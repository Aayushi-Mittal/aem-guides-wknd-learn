package com.adobe.aem.guides.wknd.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.services.GreetingService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class GreetingServiceImplTest {

    private final AemContext ctx = new AemContext();

    @Test
    void usesConfiguredPrefix() {
        // registerInjectActivateService constructs the component and calls
        // @Activate with the given config values.
        GreetingService svc = ctx.registerInjectActivateService(
                new GreetingServiceImpl(), Map.of("prefix", "Namaste"));

        assertEquals("Namaste Aayushi", svc.getGreeting("Aayushi"));
    }

    @Test
    void fallsBackToDefaultPrefix() {
        GreetingService svc = ctx.registerInjectActivateService(new GreetingServiceImpl());
        assertEquals("Hello Aayushi", svc.getGreeting("Aayushi"));
    }
}
