package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;

public class WebConfig {

    private static final String DEFAULT_SUFFIX = ".jsp";
    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private final ViewResolvers viewResolvers = new ViewResolvers(DEFAULT_SUFFIX);
    private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    public void init(String basePackage) {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(basePackage);
        annotationHandlerMapping.initialize();
        handlerMappingRegistry.addHandlerMapping(annotationHandlerMapping);
        handlerAdapterRegistry.addHandlerAdapter(new AnnotationHandlerAdapter(ArgumentResolvers.getInstance()));
        viewResolvers.addViewResolver(new JspViewResolver());
    }

    public HandlerAdapterRegistry handlerAdapterRegistry() {
        return handlerAdapterRegistry;
    }

    public ViewResolvers viewResolvers() {
        return viewResolvers;
    }

    public HandlerMappingRegistry handlerMappingRegistry() {
        return handlerMappingRegistry;
    }
}
