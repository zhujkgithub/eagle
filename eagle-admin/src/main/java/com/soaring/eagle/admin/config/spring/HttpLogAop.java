package com.soaring.eagle.admin.config.spring;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.soaring.eagle.common.util.FastJsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Song Yinghui
 * @date 2019/3/29
 * @Version 1.0
 * @Desc 统一记录请求日志
 */

@Aspect
@Component
public class HttpLogAop {

    private final Logger logger = LoggerFactory.getLogger("acclog");

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private static final Joiner commaJoiner = Joiner.on(",");

    @Around("(@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)) " +
            "&& !@annotation(com.soaring.eagle.admin.config.spring.NOLOG)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable{
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        final Object args[] = point.getArgs();
        final long startAt = System.currentTimeMillis();

        Object ret = null;
        try {
            ret = point.proceed();
            return ret;
        } finally {
            this.log(method, args, requestURI, ret, System.currentTimeMillis() - startAt);
        }
    }

    private void log(final Method method, final Object args[], String uri, final Object ret, final long cost) {
        String requestUrl = createRequestUrl(method, args, uri);
        log0(requestUrl, cost, toJson(ret));
    }

    private String createRequestUrl(final Method method, final Object[] args, final String url) {
        final String paramStr = this.createRequestParam(method, args);
        return Strings.isNullOrEmpty(paramStr) ? url : (url + "?" + paramStr);
    }

    private String createRequestParam(final Method method, final Object[] args) {
        final String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null && parameterNames.length != args.length) {
            logger.error("ParameterNames length not equal args length, methodName={}, args={}",
                    method.getName(), this.toJson(args));
        }
        return this.buildRequestParam(parameterNames, args);
    }

    private String buildRequestParam(final String[] parameterNames, final Object[] args) {
        final Map<String, String> paramsMap = Maps.newHashMap();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        final Map<String, String[]> requestParameterMap = request.getParameterMap();
        for (final Map.Entry<String, String[]> param : requestParameterMap.entrySet()) {
            paramsMap.put(param.getKey(), commaJoiner.join(param.getValue()));
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                if (args[i] instanceof BindingResult
                        || args[i] instanceof ModelAndView
                        || args[i] instanceof ServletResponse
                        || args[i] instanceof Model
                        || args[i] instanceof ModelMap
                        || args[i] instanceof HttpServletRequest) {
                    continue;
                }
                String key;
                String value;
                if (parameterNames == null || parameterNames.length != args.length) {
                    key = "arg" + i;
                    value = this.deleteQuote(this.toJson(args[i]));
                } else {
                    key = parameterNames[i];
                    value = this.deleteQuote(this.toJson(args[i]));
                }
                if (!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(value)) {
                    paramsMap.put(key, value);
                }
            }
        }
        return formatLog(paramsMap);
    }

    private String formatLog(final Map<String, String> paramsMap) {
        StringBuilder logBuilder = new StringBuilder(200);
        if (!paramsMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                logBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            logBuilder.setLength(logBuilder.length() - 1);
        }
        return logBuilder.toString();
    }

    private String deleteQuote(final String json) {
        if (!Strings.isNullOrEmpty(json)) {
            if (json.startsWith("\"") && json.endsWith("\"")) {
                return json.substring(1, json.length() - 1);
            }
        }
        return json;
    }

    private String toJson(final Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Number || obj instanceof Character || obj instanceof Boolean) {
            return obj.toString();
        } else {
            return FastJsonUtils.toJSONString(obj);
        }
    }

    private void log0(String url, final long cost, String result) {
        logger.info(this.join(cost, url, result));
    }

    private String join(final long cost, final String url, final String result) {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(cost).append("#").append(url).append("#").append(result);
        return sb.toString();
    }
}
