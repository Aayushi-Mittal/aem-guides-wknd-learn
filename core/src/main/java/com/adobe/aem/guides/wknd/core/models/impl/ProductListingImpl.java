package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.Product;
import com.adobe.aem.guides.wknd.core.models.ProductListing;
import com.adobe.aem.guides.wknd.core.services.ProductSearchService;

/**
 * Capstone model. Ties the whole stack together:
 * dialog props (title/category) -> this model -> ProductSearchService (QueryBuilder)
 * -> HTL. Each layer keeps its single responsibility.
 */
@Model(adaptables = Resource.class,
       adapters = ProductListing.class,
       resourceType = ProductListingImpl.RESOURCE_TYPE,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductListingImpl implements ProductListing {

    protected static final String RESOURCE_TYPE = "wknd/components/product-listing";

    @ValueMapValue private String title;
    @ValueMapValue private String category;      // optional filter authored in dialog

    @OSGiService private ProductSearchService searchService;   // service layer
    @SlingObject private ResourceResolver resolver;            // this resource's resolver

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<Product> getProducts() {
        return searchService.search(resolver, category);   // delegate; no logic here
    }
}
