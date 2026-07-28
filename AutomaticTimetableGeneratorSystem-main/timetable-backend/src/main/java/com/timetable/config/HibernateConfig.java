package com.timetable.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(
            DataSource dataSource) {

        LocalSessionFactoryBean factory =
                new LocalSessionFactoryBean();

        factory.setDataSource(dataSource);

        factory.setPackagesToScan(
                "com.timetable.entity");

        Properties properties = new Properties();

        properties.put(
                "hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");

        properties.put(
                "hibernate.show_sql",
                "true");

        properties.put(
                "hibernate.format_sql",
                "true");

        properties.put(
                "hibernate.hbm2ddl.auto",
                "update");

        properties.put(
                "hibernate.current_session_context_class",
                "org.springframework.orm.hibernate5.SpringSessionContext");

        factory.setHibernateProperties(properties);

        return factory;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        return new HibernateTransactionManager(sessionFactory);
    }

}