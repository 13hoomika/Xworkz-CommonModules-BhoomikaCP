package com.xworkz.userapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@ComponentScan("com.xworkz.userapp")
@EnableWebMvc
public class SpringConfiguration {
    public SpringConfiguration() {

//        System.out.println("SpringConfiguration obj created");
        log.info("SpringConfiguration obj created");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(getDataSource());
        bean.setPackagesToScan("com.xworkz.userapp.entity");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());//imp
        bean.setJpaProperties(getJpaProperties());
        return bean;

    }

    //@Bean //not required its object is not manages by spring container
    public Properties getJpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        properties.setProperty("hibernate.SQL","DEBUG");
        return properties;
    }

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/commonmodule_db");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("qwerty");
        return driverManagerDataSource;
    }

//    @Bean
//    public CommonsMultipartResolver getCommonsMultipartResolver(){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(20971520);// 20MB
//        multipartResolver.setMaxInMemorySize(104857);// 102KB
//        return multipartResolver;
//    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024); // 10MB
        return multipartResolver;
    }

    @Bean
    public ViewResolver internalViewResolver(){
        return new InternalResourceViewResolver("/",".jsp");
    }



}
