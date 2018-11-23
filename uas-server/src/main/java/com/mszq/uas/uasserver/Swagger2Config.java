package com.mszq.uas.uasserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//注解开启 swagger2 功能
@EnableSwagger2
//注解标示,这是一个配置类,@Configuation注解包含了@Component注解
//可以不用在使用@Component注解标记这是个bean了
@Configuration
@EnableWebMvc
public class Swagger2Config implements WebMvcConfigurer {
    //项目初始目录  一般就是自己springboot启动类的包名
    private String baseLocation;

    /**
     * 将Swagger2 的swagger-ui.html加入资源路径下,否则Swagger2静态页面不能访问。要想使资源能够访问，可以有两种方法
     * 一：需要配置类继承WebMvcConfigurationSupport 类，实现addResourceHandlers方法。
     *      但是实现了WebMvcConfigurationSupport以后，Spring Boot的 WebMvc自动配置就会失效，具体表现比如访问不到
     *      静态资源（js，css等）了，这是因为WebMvc的自动配置都在WebMvcAutoConfiguration类中，但是类中有这个注解
     *      @ConditionalOnMissingBean({WebMvcConfigurationSupport.class})，意思是一旦在容器中检测到
     *      WebMvcConfigurationSupport这个类，WebMvcAutoConfiguration类中的配置都不生效。
     *      所以一旦我们自己写的配置类继承了WebMvcConfigurationSupport，相当于容器中已经有了WebMvcConfigurationSupport，
     *      所有默认配置都不会生效，都得自己在配置文件中配置。
     * 二：继承WebMvcConfigurer接口，这里采用此方法 网上有人说使用该方法会导致date编译等问题，可能在配置文件中得到解决，
     *      本人没有碰到，不多做解释
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 通过 createRestApi函数来构建一个DocketBean
     * 函数名,可以随意命名,喜欢什么命名就什么命名
     * 接口文档默认访问路径http://localhost:8080/swagger-ui.html，
     *          配置文件中有配置此处为http://localhost:8080/springboot2/swagger-ui.html
     * 注解说明参考博客：https://blog.csdn.net/qq_28009065/article/details/79104103，
     */

    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("统一认证API接口文档")
                .apiInfo(apiInfo("统一认证API接口文档"))
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mszq.uas.uasserver.controller"))//指向自己的controller即可
                .paths(PathSelectors.any())
                .build();
    }
    //
//    @Bean
//    public Docket normalUserDocket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("普通用户API文档")
//                .apiInfo(apiInfo("提供普通用户接口"))
//                .protocols(Sets.newHashSet("https","http"))
//                .produces(Sets.newHashSet("html/text"))
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(baseLocation+".controller.candidate"))//设置生成的Docket对应的Controller为candidate下的所有Controller
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Bean
//    public Docket companyUserDocket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("企业用户API接口文档")
//                .apiInfo(apiInfo("提供企业用户接口"))
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage(baseLocation+".controller.company"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//设置文档信息
    private ApiInfo apiInfo(String desc) {
        return new ApiInfoBuilder()
                //页面标题
                .title(desc)
                //设置作者联系方式,可有可无
                .contact(new Contact("刘国杨", "","liugyang@126.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API描述")
                .build();

    }
}

/*
    Docket类的方法：
    Docket groupName(String var):设置栏目名

    Docket apiInfo(ApiInfo apiInfo):设置文档信息

    Docket pathMapping(String path):设置api根路径

    Docket protocols(Set<String> protocols):设置协议，Sets为com.goolge.common下的类，Sets.newHashSet("https","http")相当于new HashSet(){{add("https");add("http");}};

    ApiSelectorBuilder select():初始化并返回一个API选择构造器



    ApiSelectorBuilder类的方法：

    ApiSelectorBuilder apis(Predicate<RequestHandler> selector):添加选择条件并返回添加后的ApiSelectorBuilder对象

    ApiSelectorBuilder paths(Predicate<String> selector):设置路径筛选，该方法中含一句pathSelector = and(pathSelector, selector);表明条件为相与



    RequestHandlerSelectors类的方法：

    Predicate<RequestHandler> any()：返回包含所有满足条件的请求处理器的断言，该断言总为true

    Predicate<RequestHandler> none()：返回不满足条件的请求处理器的断言,该断言总为false

    Predicate<RequestHandler> basePackage(final String basePackage)：返回一个断言(Predicate)，该断言包含所有匹配basePackage下所有类的请求路径的请求处理器



    PathSelectors类的方法：

    Predicate<String> any():满足条件的路径，该断言总为true

    Predicate<String> none():不满足条件的路径,该断言总为false

    Predicate<String> regex(final String pathRegex):符合正则的路径



设置swagger-ui.html默认路径，servlet的配置文件添加:

    <mvc:annotation-driven></mvc:annotation-driven>
    <mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars"/>

    swagger-ui.html位于springfox-swagger-ui jar包中的META-INF/resources/目录下，项目编译后swagger-ui.html将添加到classpath的/META-INF/resources/下，所以添加mapping="/webjars/**" 可通过localhost:端口号/项目名/swagger-ui.html打开SwaggerUI

常用注解:

    Swagger所有注解并非必须，若不加就只显示类目/方法名/参数名没有注释而已，但若注解中含@ApiParam不对应@RequestParam将无效果

    @Api:注解controller，value为@RequestMapping路径

    @ApiOperation:注解方法，value为简要描述,notes为全面描述,hidden=true Swagger将不显示该方法，默认为false

    @ApiParam:注解参数,hidden=true Swagger参数列表将不显示该参数,name对应参数名，value为注释，defaultValue设置默认值,allowableValues设置范围值,required设置参数是否必须，默认为false

    @ApiModel:注解Model

    @ApiModelProperty:注解Model下的属性，当前端传过来的是一个对象时swagger中该对象的属性注解就是ApiModelProperty中的value

    @ApiIgnore:注解类、参数、方法，注解后将不在Swagger UI中显示


*/
