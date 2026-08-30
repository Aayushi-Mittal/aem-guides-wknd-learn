package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.services.GreetingService;

/**
 * Thin demo model: injects the GreetingService and asks it for a greeting.
 * Kept in the exported 'models' package so HTL (data-sly-use by FQN) can
 * resolve it, following the WKND HelloWorldModel convention.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GreetingModel {

    @OSGiService                       // inject the OSGi service by type
    private GreetingService greetingService;

    @ValueMapValue
    private String name;

    public String getMessage() {
        return greetingService.getGreeting(name == null ? "there" : name);
    }
}
