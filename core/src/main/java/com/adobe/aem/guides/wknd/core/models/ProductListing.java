package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

/** Capstone: a titled product listing filtered by an authored category. */
public interface ProductListing {
    String getTitle();
    List<Product> getProducts();
}
