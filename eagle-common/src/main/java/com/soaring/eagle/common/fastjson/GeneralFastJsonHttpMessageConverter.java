package com.soaring.eagle.common.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Email: hautxxxyzjk@163.com
 * Date: 2019-03-26
 * Time: 23:21
 * Description:
 */
public class GeneralFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    public GeneralFastJsonHttpMessageConverter() {
        setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
        FastJsonConfig fastJsonConfig = getFastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                //避免循环引用
//                SerializerFeature.DisableCircularReferenceDetect,
                //是否输出Map中,值为null的字段
                SerializerFeature.WriteMapNullValue,
                //List字段如果为null,输出为[],而非null
//                SerializerFeature.WriteNullListAsEmpty,
                //数值字段如果为null,输出为0,而非null
//                SerializerFeature.WriteNullNumberAsZero,
                //字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                //Boolean字段如果为null,输出为false,而非null
//                SerializerFeature.WriteNullBooleanAsFalse,
                //FastJson代替JackSon后，由于springBoot返回json的日期自动转为long，所以必须要让FastJson格式化日期
                SerializerFeature.WriteDateUseDateFormat);

        /*fastJsonConfig.setSerializeFilters((ValueFilter) (o, s, o1) -> {
            if (o1 == null) {
                return "";
            }
            if(o1 instanceof BigDecimal || o1 instanceof Double || o1 instanceof Float){
                BigDecimal bigDecimal = new BigDecimal(o1.toString());
                return bigDecimal;
            }
            return o1;
        });*/

        // JS 中能精准表示的最大整数是 Math.pow(2, 53)，十进制即 9007199254740992，
        // 雪花算法生成的ID是18位的 Long类型，要想让JS识别，JSON只能以字符串形式返回，
        // 但是和树形结构展示冲突，因为 parentId 必须是 null
        // 目前是把雪花算法 只取后面15位
        /*SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, (jsonSerializer, o, o1, type, i) -> {
            SerializeWriter out = jsonSerializer.getWriter();
            out.writeString(Objects.toString(o, null));
        });
        fastJsonConfig.setSerializeConfig(serializeConfig);*/
    }
}
