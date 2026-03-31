package com.fourman.common.webapp.config.inbound;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InboundRequest {

    boolean logRequestBody() default true;

    boolean logResponseBody() default true;

    int maxPayloadLength() default 2000;
}
