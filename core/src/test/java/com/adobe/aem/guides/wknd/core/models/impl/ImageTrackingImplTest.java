package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.ImageTracking;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ImageTrackingImplTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ImageTrackingImpl.class);
        ctx.build().resource("/content/img",
                "sling:resourceType", "wknd/components/image-tracking",
                "trackingId", "campaign-42");
        ctx.currentResource("/content/img");
    }

    @Test
    void readsOwnTrackingId() {
        ImageTracking model = ctx.request().adaptTo(ImageTracking.class);
        assertEquals("campaign-42", model.getTrackingId());
    }
}
