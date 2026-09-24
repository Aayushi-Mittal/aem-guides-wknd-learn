package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.adobe.aem.guides.wknd.core.models.Faq;
import com.adobe.aem.guides.wknd.core.models.FaqItem;

@Model(adaptables = Resource.class, adapters = Faq.class,
       resourceType = FaqImpl.RESOURCE_TYPE,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqImpl implements Faq {

    protected static final String RESOURCE_TYPE = "wknd/components/faq";

    @ChildResource                    // children of the "faqItems" multifield node
    private List<FaqItem> faqItems;

    @Override
    public List<FaqItem> getItems() {
        return faqItems == null ? Collections.emptyList() : faqItems;
    }
}
