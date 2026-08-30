package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.Article;

/** Adapts a single multifield item node (item0, item1, ...) into an Article. */
@Model(adaptables = Resource.class,
       adapters = Article.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleImpl implements Article {

    @ValueMapValue private String title;
    @ValueMapValue private String description;
    @ValueMapValue private String category;
    @ValueMapValue private String url;

    @Override public String getTitle() { return title; }
    @Override public String getDescription() { return description; }
    @Override public String getCategory() { return category; }
    @Override public String getUrl() { return url; }
}
