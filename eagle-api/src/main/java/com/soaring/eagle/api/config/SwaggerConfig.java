package com.soaring.eagle.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: 朱建柯
 * Date: 2019/3/28
 * Time: 13:00
 * description: 文档地址：http://localhost:9001/swagger-ui.html
 * <p>
 * Swagger2 基本使用：
 * @Api 描述类/接口的主要用途
 * @ApiOperation 描述方法用途
 * @ApiImplicitParam 描述方法的参数
 * @ApiImplicitParams 描述方法的参数(Multi - Params)
 * @ApiIgnore 忽略某类/方法/参数的文档
 */
@Configuration
public class SwaggerConfig {

    /**
     * 是否开启swagger，正式环境一般是需要关闭的，
     * 可根据springboot的多环境配置进行设置
     */
    @Value(value = "${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.soaring.eagle.api"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("eagle-api")
                .description("翱翔的雄鹰接口文档")
                .termsOfServiceUrl("https://gitee.com/doublelifeke/eagle")
                .version("1.0")
                .build();
    }
}
