package com.mszq.uas.uasserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                     //设置允许跨域的路径
                .allowedOrigins("*")                                //设置允许跨域请求的域名
                .allowCredentials(true)                             //是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")     //设置允许的方法
                .maxAge(3600);                                      //跨域允许时间
    }

//    @Bean
//    public AppSecretInterceptor getAppSecretInterceptor() {
//        return new AppSecretInterceptor();
//    }
//
//    @Bean
//    public IpBlackListInterceptor getIpBlackListInterceptor() {
//        return new IpBlackListInterceptor();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        //IP黑名单验证
//        InterceptorRegistration registration = registry.addInterceptor(getIpBlackListInterceptor());
//        registration.addPathPatterns("/**");
//        //registration.excludePathPatterns("/","/login","/error","/static/**","/logout");       //添加不拦截路径
//
//        //APP Secret 验证
//        InterceptorRegistration registration1 = registry.addInterceptor(getAppSecretInterceptor());
//        registration1.addPathPatterns("/sso/**","/datasync/**","/permission/**");                    //所有路径都被拦截
////        registration1.excludePathPatterns("/auth","/signout","/swagger-ui**","/static/**");       //添加不拦截路径
    }
}
