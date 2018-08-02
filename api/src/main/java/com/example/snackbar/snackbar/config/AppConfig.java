/*
 * CONFIDENTIAL – This document contains confidential information.
 * Access is restricted and ownership or possession by Logicalis
 * or any of its subsidiaries or partner companies, and it is
 * protected against publication by applicable legislation.
 * Undue possession, visualization, publication, distribution or
 * unauthorized use of this document is strictly prohibited.
 * <p>
 * Copyright 2018, Logicalis.
 * All rights reserved.
 */

package com.example.snackbar.snackbar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("com.javasampleapproach.corsjavaconfig")
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("${snack.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowedHeaders("Content-Type")
                .exposedHeaders("header-1", "header-2")
                .allowCredentials(false)
                .maxAge(6000);
    }
}