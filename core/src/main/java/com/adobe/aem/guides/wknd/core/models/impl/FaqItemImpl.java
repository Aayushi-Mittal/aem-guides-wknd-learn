package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.FaqItem;

@Model(adaptables = Resource.class, adapters = FaqItem.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqItemImpl implements FaqItem {

    @ValueMapValue private String question;
    @ValueMapValue private String answer;

    @Override public String getQuestion() { return question; }
    @Override public String getAnswer() { return answer; }
}
