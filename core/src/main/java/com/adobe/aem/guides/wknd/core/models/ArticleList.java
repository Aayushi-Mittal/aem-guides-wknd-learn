package com.adobe.aem.guides.wknd.core.models;

import java.util.List;

/** Parent model: exposes the authored list of articles. */
public interface ArticleList {
    List<Article> getArticles();
}
