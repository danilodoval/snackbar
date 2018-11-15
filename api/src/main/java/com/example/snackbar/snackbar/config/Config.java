package com.example.snackbar.snackbar.config;

import com.github.mongobee.Mongobee;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class Config extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongo}")
    private String host;

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host);
    }

    @Override
    protected String getDatabaseName() {
        return "snack_bar";
    }

    @Bean
    public Mongobee mongobee() throws Exception {
        Mongobee runner = new Mongobee(uri);
        runner.setDbName(getDatabaseName());
        runner.setChangeLogsScanPackage("com.example.snackbar.snackbar.mongo");
        runner.setMongoTemplate(mongoTemplate());
        return runner;
    }
}
