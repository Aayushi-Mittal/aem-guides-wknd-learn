package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.Product;

@Model(adaptables = Resource.class, adapters = Product.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductImpl implements Product {

    @ValueMapValue private String name;
    @ValueMapValue private String price;
    @ValueMapValue private String category;

    @Override public String getName() { return name; }
    @Override public String getPrice() { return price; }
    @Override public String getCategory() { return category; }
}
