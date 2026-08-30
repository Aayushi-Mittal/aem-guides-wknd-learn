package com.adobe.aem.guides.wknd.core.services.impl;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The configuration schema. Each method = one configurable field that shows up
 * in the OSGi Configuration Manager (/system/console/configMgr).
 */
@ObjectClassDefinition(name = "WKND Greeting Service")
public @interface GreetingServiceConfig {

    @AttributeDefinition(
            name = "Prefix",
            description = "Text placed before the name, e.g. 'Hello'.")
    String prefix() default "Hello";
}
