package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.aem.guides.wknd.core.models.ImageTracking;
import com.adobe.cq.wcm.core.components.models.Image;

@Model(adaptables = SlingHttpServletRequest.class,
       adapters = ImageTracking.class,
       resourceType = ImageTrackingImpl.RESOURCE_TYPE,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageTrackingImpl implements ImageTracking {

    protected static final String RESOURCE_TYPE = "wknd/components/image-tracking";

    /*
     * DELEGATION: @Self adapts the current request to the core Image model.
     * @Via(ResourceSuperType) tells Sling to present the resource *as its
     * sling:resourceSuperType* (core image) when choosing the model, so we get
     * the fully-featured core Image implementation for free — zero duplication.
     */
    @Self
    @Via(type = ResourceSuperType.class)
    private Image image;

    /** Our own extra property, read from this component's own node. */
    @ValueMapValue
    private String trackingId;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getTrackingId() {
        return trackingId;
    }
}
