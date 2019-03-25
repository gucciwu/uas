package com.mszq.uas.syncdata;

import com.mszq.uas.syncdata.job.PasswordChangeCatcher;
import com.mszq.uas.syncdata.job.SyncHrDataJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobDetailFactoryBean passwordChangeCatcherJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PasswordChangeCatcher.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean syncHrDataJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(SyncHrDataJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean passwordChangeCatcherJobTrigger(@Qualifier("passwordChangeCatcherJobDetail") JobDetail jobDetail,
                                                     @Value("${passwordChangeCatcher.cronExpression}") String cronExpression) {
        logger.info("Catch password changed job.");
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression(cronExpression);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean syncHrDataJobTrigger(@Qualifier("syncHrDataJobDetail") JobDetail jobDetail,
                                                     @Value("${syncHrDataJob.cronExpression}") String cronExpression) {
        logger.info("Synchronize organization job.");
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression(cronExpression);
        return factoryBean;
    }

//    @Bean
//    public SimpleTriggerFactoryBean syncHrDataJobTrigger(@Qualifier("syncHrDataJobDetail") JobDetail jobDetail,
//                                                         @Value("${syncHrDataJob.cronExpression}") String cronExpression) {
//        logger.info("Synchronize organization job.");
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(jobDetail);
//        factoryBean.setStartDelay(0L);
//        factoryBean.setRepeatCount(0);
//        factoryBean.setRepeatInterval(5000);
//        return factoryBean;
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger passwordChangeCatcherJobTrigger)
            throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(passwordChangeCatcherJobTrigger);
        logger.info("Start job scheduler");
        return factory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean1(JobFactory jobFactory, Trigger syncHrDataJobTrigger)
            throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(syncHrDataJobTrigger);
        logger.info("Start job scheduler");
        return factory;
    }
}
