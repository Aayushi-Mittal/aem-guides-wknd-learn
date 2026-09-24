package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.Faq;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class FaqImplTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(FaqImpl.class, FaqItemImpl.class);
        ctx.build().resource("/content/faq", "sling:resourceType", "wknd/components/faq")
                .resource("faqItems").siblingsMode()
                .resource("item0", "question", "Q1", "answer", "A1")
                .resource("item1", "question", "Q2", "answer", "A2");
    }

    @Test
    void readsItems() {
        Faq faq = ctx.resourceResolver().getResource("/content/faq").adaptTo(Faq.class);
        assertEquals(2, faq.getItems().size());
        assertEquals("Q1", faq.getItems().get(0).getQuestion());
        assertEquals("A2", faq.getItems().get(1).getAnswer());
    }
}
