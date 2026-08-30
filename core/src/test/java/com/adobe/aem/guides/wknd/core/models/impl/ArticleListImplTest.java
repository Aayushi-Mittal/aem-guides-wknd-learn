package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.ArticleList;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class ArticleListImplTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(ArticleListImpl.class, ArticleImpl.class);
        ctx.load().json("/com/adobe/aem/guides/wknd/core/models/impl/ArticleListImplTest.json", "/content");
    }

    @Test
    void readsAllItemsInOrder() {
        ctx.currentResource("/content/list");
        ArticleList model = ctx.currentResource().adaptTo(ArticleList.class);

        assertEquals(2, model.getArticles().size());
        assertEquals("First", model.getArticles().get(0).getTitle());
        assertEquals("Second", model.getArticles().get(1).getTitle());
    }

    @Test
    void emptyWhenNoMultifield() {
        ctx.currentResource("/content/empty");
        ArticleList model = ctx.currentResource().adaptTo(ArticleList.class);
        assertEquals(0, model.getArticles().size());
    }
}
