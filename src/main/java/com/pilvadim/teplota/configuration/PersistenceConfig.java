package com.pilvadim.teplota.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableCaching
@MapperScan("com.pilvadim.teplota")
public class PersistenceConfig {}
