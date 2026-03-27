package com.fourman.common;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.fourman.common.storage.client.StorageClient;

@Configuration
@EnableFeignClients(clients = {StorageClient.class})
public class FeignClientAutoConfiguration {}
