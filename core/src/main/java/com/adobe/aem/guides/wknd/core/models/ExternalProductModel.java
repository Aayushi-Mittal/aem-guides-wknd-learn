package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.services.ExternalProductService;

/** Demo model: reads authored productId, asks the external service for it. */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExternalProductModel {

    @OSGiService
    private ExternalProductService externalProductService;

    @ValueMapValue
    private String productId;

    public Product getProduct() {
        return externalProductService.getProduct(productId == null ? "1" : productId);
    }
}
