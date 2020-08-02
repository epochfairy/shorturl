package com.example.demo.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;

@Configuration
public class SQLInit {

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory factory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(factory);
        //ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"),new ClassPathResource("data.sql"));
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));

        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
