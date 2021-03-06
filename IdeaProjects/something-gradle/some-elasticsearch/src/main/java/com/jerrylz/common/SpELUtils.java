package com.jerrylz.common;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author jerrylz
 * @date 2020/9/3
 */
@Component
public class SpELUtils implements EmbeddedValueResolverAware {
    private StringValueResolver resolver;
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
    }


}
