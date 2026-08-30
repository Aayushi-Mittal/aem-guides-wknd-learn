package com.adobe.aem.guides.wknd.core.models;

import com.adobe.cq.wcm.core.components.models.Image;

/**
 * Extension of the core Image component that adds an author-set trackingId,
 * WITHOUT duplicating the core Image logic.
 */
public interface ImageTracking {

    /** The delegated core Image model (all standard image behaviour). */
    Image getImage();

    /** The extra, author-configured tracking id. */
    String getTrackingId();
}
