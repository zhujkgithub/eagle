Spring Boot 1.5.10 配置RedisConfig
====

RedisConfig
----

    import com.fasterxml.jackson.annotation.JsonAutoDetect;
    import com.fasterxml.jackson.annotation.PropertyAccessor;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.cache.CacheManager;
    import org.springframework.cache.annotation.CachingConfigurerSupport;
    import org.springframework.cache.annotation.EnableCaching;
    import org.springframework.cache.interceptor.KeyGenerator;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.data.redis.cache.RedisCacheManager;
    import org.springframework.data.redis.connection.RedisConnectionFactory;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.data.redis.core.StringRedisTemplate;
    import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
    
    /**
     *  必须继承CachingConfigurerSupport，不然此类中生成的Bean不会生效（没有替换掉默认生成的，只是一个普通的bean）
     *  springboot默认生成的缓存管理器和redisTemplate支持的类型很有限，根本不满足我们的需求，会抛出如下异常：
     *      org.springframework.cache.interceptor.SimpleKey cannot be cast to java.lang.String
     */
    @Configuration
    @EnableCaching
    public class RedisCacheConfig extends CachingConfigurerSupport {
    
        @Value("${cache.expire-time:180}")
        private int expireTime;
    
        // 配置key生成器，作用于缓存管理器管理的所有缓存
        // 如果缓存注解（@Cacheable、@CacheEvict等）中指定key属性，那么会覆盖此key生成器
        @Bean
        public KeyGenerator keyGenerator() {
            return (target, method, params) -> {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            };
        }
    
        // 缓存管理器管理的缓存都需要有对应的缓存空间，否则抛异常：No cache could be resolved for 'Builder...
        @Bean
        public CacheManager cacheManager(RedisTemplate redisTemplate) {
            RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
            rcm.setDefaultExpiration(expireTime);               //设置缓存管理器管理的缓存的过期时间, 单位：秒
            return rcm;
        }
    
        @Bean
        public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
            StringRedisTemplate template = new StringRedisTemplate(factory);
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(om);
            template.setValueSerializer(jackson2JsonRedisSerializer);
            template.afterPropertiesSet();
            return template;
        }
    }

实现类
----

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.stereotype.Service;
    import org.springframework.util.StringUtils;
    
    import java.util.ArrayList;
    import java.util.List;
    
    /**
     * 若未配置@CacheConfig(cacheNames = "hello"), 则@Cacheable一定要配置value，相当于指定缓存空间
     * 否则会抛异常：No cache could be resolved for 'Builder...
     *
     * 若@CacheConfig(cacheNames = "hello") 与 @Cacheable(value = "123")都配置了， 则@Cacheable(value = "123")生效
     *
     * 当然@CacheConfig还有一些其他的配置项，Cacheable也有一些其他的配置项
     */
    @Service
    public class CacheServiceImpl implements ICacheService {
    
        @Autowired
        private RedisTemplate<String, String> redisTemplate;
    
        @Override
        @Cacheable(value = "test")                                                    // key用的自定义的KeyGenerator
        public String getName() {
            System.out.println("getName, no cache now...");
            return "brucelee";
        }
    
        @Override
        @Cacheable(value = "user", key = "methodName + '_' + #p0", unless = "#result.size() <= 0")      // key会覆盖掉KeyGenerator
        public List<User> listUser(int pageNum, int pageSize) {
            System.out.println("listUser no cache now...");
            List<User> users = new ArrayList<>();
            users.add(new User("zhengsan", 22));
            users.add(new User("lisi", 20));
            System.out.println("===========");
            return users;
        }
    
        /**
         * 缓存不是缓存管理器管理，那么不受缓存管理器的约束
         * 缓存管理器中的配置不适用与此
         * 这里相当于我们平时直接通过redis-cli操作redis
         * @return
         */
        @Override
        public String getUserName() {
    
            String userName = redisTemplate.opsForValue().get("userName");
            if (!StringUtils.isEmpty(userName)) {
                return userName;
            }
    
            System.out.println("getUserName, no cache now...");
            redisTemplate.opsForValue().set("userName", "userName");
            return "userName";
        }
    
    }
    
说明
----
    工程中的缓存分两种：缓存管理器管理的缓存（也就是一些列注解实现的缓存）、
    redisTemplate操作的缓存
    
    缓存管理器管理的缓存：
        会在redis中增加2条数据，一个是类型为 zset 的 缓存名~keys , 里面存放了该缓存所有的key， 
        另一个是对应的key，值为序列化后的json；缓存名~keys可以理解成缓存空间，与我们平时所说的具体的缓存是不一样的。
        另外对缓存管理器的一些设置（全局过期时间等）都会反映到缓存管理器管理的所有缓存上；
        上图中的http://localhost:8888/getName和http://localhost:8888/listUser?pageNum=1&pageSize=3对应的是缓存管理器管理的缓存。
    
    redisTemplate操作的缓存：
        会在redis中增加1条记录，key - value键值对，
        与我们通过redis-cli操作缓存一样；
        上图中的http://localhost:8888/getUserName对应的是redisTemplate操作的缓存。
  
  
      
        
Spring Boot 2.0.3 配置RedisConfig
====

RedisConfig 
----

    @EnableCaching
    @Configuration
    @AutoConfigureAfter(RedisAutoConfiguration.class)
    public class RedisConfig {
    
    
        /**
         * 配置自定义redisTemplate
         *
         * @param connectionFactory
         * @return
         */
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            template.setValueSerializer(jackson2JsonRedisSerializer());
            //使用StringRedisSerializer来序列化和反序列化redis的key值
            template.setKeySerializer(new StringRedisSerializer());
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(jackson2JsonRedisSerializer());
            template.afterPropertiesSet();
            return template;
        }
    
        /**
         * json序列化
         * @return
         */
        @Bean
        public RedisSerializer<Object> jackson2JsonRedisSerializer() {
            //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
            Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
    
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            serializer.setObjectMapper(mapper);
            return serializer;
        }
    
    
        /**
         * 配置缓存管理器
         * @param redisConnectionFactory
         * @return
         */
        @Bean
        public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
            // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
            // 设置缓存的默认过期时间，也是使用Duration设置
            config = config.entryTtl(Duration.ofMinutes(1))
                    // 设置 key为string序列化
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                    // 设置value为json序列化
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                    // 不缓存空值
                    .disableCachingNullValues();
    
            // 设置一个初始化的缓存空间set集合
            Set<String> cacheNames = new HashSet<>();
            cacheNames.add("timeGroup");
            cacheNames.add("user");
    
            // 对每个缓存空间应用不同的配置
            Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
            configMap.put("timeGroup", config);
            configMap.put("user", config.entryTtl(Duration.ofSeconds(120)));
    
            // 使用自定义的缓存配置初始化一个cacheManager
            RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                    // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                    .initialCacheNames(cacheNames)
                    .withInitialCacheConfigurations(configMap)
                    .build();
            return cacheManager;
        }
    }


实现类
----

    @Service
    public class UserServiceImpl implements UserService {
    
        @Autowired
        private UserMapper userMapper;
    
    
        @Override
        public User saveUser(User user) {
            userMapper.save(user);
            // 返回用户信息，带id
            return user;
        }
    
        /**
         * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
         *      condition 与unless相反，只有表达式为真才会执行。
         * @param id 主键id
         * @return
         */
        @CacheEvict(value = "user", key = "#root.args[0]", condition = "#result eq true")
        @Override
        public Boolean removeUser(Long id) {
            // 如果删除记录不为1  则是失败
            return userMapper.deleteById(id) == 1;
        }
    
        /**
         *  @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
         *            key 缓存在redis中的key
         *            unless 表示条件表达式成立的话不放入缓存
         * @param id 主键id
         * @return
         */
        @Cacheable(value = "user", key = "#root.args[0]", unless = "#result eq null ")
        @Override
        public User getById(Long id) {
            return userMapper.selectById(id);
        }
    
        /**
         *  @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
         * @param user 用户信息
         * @return
         */
        @CachePut(value = "user", key = "#root.args[0]", unless = "#user eq null ")
        @Override
        public User updateUser(User user) {
            userMapper.update(user);
            return user;
        }
    }
    
    
说明
----

    在springboot2中配置缓存管理是新的api也就是builder模式构建。
    然后通过@EnableCaching 开启缓存注解。然后需要注意的是
    你在redistemplate中的配置的key，value序列化方法并不会生效，
    需要在RedisCacheConfiguration中单独配置。