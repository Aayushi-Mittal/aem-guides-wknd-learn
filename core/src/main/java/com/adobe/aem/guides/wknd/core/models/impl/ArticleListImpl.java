package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.adobe.aem.guides.wknd.core.models.Article;
import com.adobe.aem.guides.wknd.core.models.ArticleList;

@Model(adaptables = Resource.class,
       adapters = ArticleList.class,
       resourceType = ArticleListImpl.RESOURCE_TYPE,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleListImpl implements ArticleList {

    protected static final String RESOURCE_TYPE = "wknd/components/article-list";

    // @ChildResource on a List injects the CHILDREN of the "articles" node
    // (item0, item1, ...), each adapted to Article. The field name must match
    // the multifield's node name ("./articles" in the dialog).
    @ChildResource
    private List<Article> articles;

    @Override
    public List<Article> getArticles() {
        return articles == null ? Collections.emptyList() : articles;
    }
}
