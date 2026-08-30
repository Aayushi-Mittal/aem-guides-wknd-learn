package com.adobe.aem.guides.wknd.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.aem.guides.wknd.core.services.GreetingService;

@Component(service = GreetingService.class)   // register as an OSGi service
@Designate(ocd = GreetingServiceConfig.class) // bind the config schema to this component
public class GreetingServiceImpl implements GreetingService {

    private String prefix;

    /*
     * @Activate runs on start; @Modified runs when config changes at runtime.
     * Binding both means the service re-reads config with NO restart/redeploy.
     */
    @Activate
    @Modified
    protected void activate(GreetingServiceConfig config) {
        this.prefix = config.prefix();
    }

    @Override
    public String getGreeting(String name) {
        return prefix + " " + name;
    }
}
