package com.evo.common;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.evo.common.storage.client.StorageClient;

@Configuration
@EnableFeignClients(clients = {StorageClient.class})
public class FeignClientAutoConfiguration {}
