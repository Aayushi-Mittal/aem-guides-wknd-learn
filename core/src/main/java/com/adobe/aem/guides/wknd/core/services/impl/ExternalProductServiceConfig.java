package com.adobe.aem.guides.wknd.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "WKND External Product Service")
public @interface ExternalProductServiceConfig {

    @AttributeDefinition(name = "API base URL")
    String apiBaseUrl() default "https://dummyjson.com";

    @AttributeDefinition(name = "Connect timeout (ms)")
    int connectTimeout() default 3000;

    @AttributeDefinition(name = "Read timeout (ms)")
    int readTimeout() default 5000;
}
