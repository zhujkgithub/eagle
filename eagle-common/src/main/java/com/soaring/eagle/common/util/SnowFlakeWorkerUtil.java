package com.soaring.eagle.common.util;

import org.apache.commons.lang3.RandomUtils;

/**
 * 一般情况，实现全局唯一ID，有三种方案，分别是通过中间件方式、UUID、雪花算法。
 *
 * 方案一，通过中间件方式，可以是把数据库或者redis缓存作为媒介，从中间件获取ID。
 * 这种呢，优点是可以体现全局的递增趋势（优点只能想到这个），缺点呢，倒是一大堆，
 * 比如，依赖中间件，假如中间件挂了，就不能提供服务了；依赖中间件的写入和事务，会影响效率；
 * 数据量大了的话，你还得考虑部署集群，考虑走代理。这样的话，感觉问题复杂化了
 *
 * 方案二，通过UUID的方式，java.util.UUID就提供了获取UUID的方法，
 * 使用UUID来实现全局唯一ID，优点是操作简单，也能实现全局唯一的效果，
 * 缺点呢，就是不能体现全局视野的递增趋势；太长了，UUID是32位，有点浪费；
 * 最重要的，是插入的效率低，因为呢，我们使用mysql的话，一般都是B+tree的结构来存储索引，
 * 假如是数据库自带的那种主键自增，节点满了，会裂变出新的节点，新节点满了，再去裂变新的节点，
 * 这样利用率和效率都很高。而UUID是无序的，会造成中间节点的分裂，也会造成不饱和的节点，
 * 插入的效率自然就比较低下了。
 *
 * 方案三，雪花算法SnowFlake，是推特公司使用的一款通过划分命名空间并行生成的算法，
 * 来解决全局唯一ID的需求，类似的还有MongoDB的object_id。雪花算法，是64位二进制，转换十进制，
 * 不超过20位。第一位是符号位，一般是不变的0，第二阶梯的是41位的毫秒，第三阶梯是10位的机器ID，
 * 第四阶梯是12位的序列号，雪花算法能保证一毫秒内，支持1024*4096个并发，400多W了，
 * 对付绝大多数场景，都适用了。
 * 优点就是方案一和方案二的不足的反例---不依赖中间件、可以体现趋势递增，
 * 且通过第三阶梯的机器ID可以知道是哪一台机器生成的ID，
 * 缺点呢，就是因为雪花算法是依赖毫秒，而毫秒又是通过本机来获取的，
 * 假如本机的时钟回拨了，那就乱套了，可能会造成ID冲突或者ID乱序。
 *
 * @Description: 全局唯一Id生成器
 */

public class SnowFlakeWorkerUtil {

    /** 开始时间截 (2019-04-01) */
    private final long twepoch = 1554048000000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /**
     * 工作机器ID(0~31)
     */
    private final static long WORKERID = 0;
    /**
     * 数据中心ID(0~31)
     */
    private final static long DATACENTERID = 0;

    private static SnowFlakeWorkerUtil instance = new SnowFlakeWorkerUtil(WORKERID, DATACENTERID);

    public static SnowFlakeWorkerUtil getInstance(){
        return instance;
    }

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    private SnowFlakeWorkerUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = generateRandom();
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }
    //生成的ID，例如message-id/ order-id/ tiezi-id，在数据量大时往往需要分库分表，
    // 这些ID经常作为取模分库分表的依据，为了分库分表后数据均匀，
    // ID生成往往有“取模随机性”的需求，所以我们通常把每秒内的序列号放在ID的最末位，
    // 保证生成的ID是随机的。

    //又如果，我们在跨毫秒时，序列号总是归0，会使得序列号为0的ID比较多，导致生成的ID取模后不均匀。
    // 解决方法是，序列号不是每次都归0，而是归一个0到9的随机数，这个地方。
    protected long generateRandom() {
        return RandomUtils.nextLong(0,10);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) {
        SnowFlakeWorkerUtil idWorker = new SnowFlakeWorkerUtil(0, 0);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id)+ "==="+Long.toBinaryString(id).length());
//            System.out.println(id+"======"+String.valueOf(id).length());

        }
    }
}

