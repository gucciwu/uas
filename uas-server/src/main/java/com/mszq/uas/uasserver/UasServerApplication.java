package com.mszq.uas.uasserver;

import com.mszq.uas.uasserver.filter.SimpleCORSFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.mszq.uas.uasserver.dao.mapper")
public class UasServerApplication {
    @PostConstruct
    void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    @Bean
    public FilterRegistrationBean filterRegistry(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new SimpleCORSFilter());
        bean.addUrlPatterns("/*");
        bean.setName("SimpleCORSFilter");
        bean.setOrder(1);
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(UasServerApplication.class, args);
    }
}
