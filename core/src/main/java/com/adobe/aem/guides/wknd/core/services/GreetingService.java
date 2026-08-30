package com.adobe.aem.guides.wknd.core.services;

/** Returns a greeting for a name. Prefix is OSGi-configurable. */
public interface GreetingService {
    String getGreeting(String name);
}
