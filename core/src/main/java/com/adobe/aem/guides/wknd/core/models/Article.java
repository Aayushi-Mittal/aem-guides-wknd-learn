package com.adobe.aem.guides.wknd.core.models;

/** One row of the Article List multifield. */
public interface Article {
    String getTitle();
    String getDescription();
    String getCategory();
    String getUrl();
}
