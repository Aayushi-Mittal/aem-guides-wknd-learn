package com.adobe.aem.guides.wknd.core.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.adobe.aem.guides.wknd.core.models.Product;

/** Finds products with QueryBuilder, optionally filtered by category. */
public interface ProductSearchService {
    List<Product> search(ResourceResolver resolver, String category);
}
