package org.example.config;

import org.example.factory.HibernateSessionFactory;
import org.example.factory.impl.H2Factory;
import org.example.factory.impl.PostgresFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("org.example")
public class SpringConfig {
    @Bean
    @Profile("test")
    public HibernateSessionFactory getH2SessionFactory(){

        return new H2Factory();
    }

    @Bean
    @Profile("dev")
    public HibernateSessionFactory getPostgresSessionFactory(){

        return new PostgresFactory();
    }
}
