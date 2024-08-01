package com.interface21.webmvc.servlet.mvc.tobe;

public class WebConfig {

    private static final String DEFAULT_SUFFIX = ".jsp";

    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private final ViewResolvers viewResolvers = new ViewResolvers(DEFAULT_SUFFIX);
    private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    public void init(String basePackage) {
        addHandlerAdapter(new AnnotationHandlerAdapter());
        addViewResolver(new JspViewResolver());
        addHandlerMapping(new AnnotationHandlerMapping(basePackage));
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addViewResolver(ViewResolver viewResolver) {
        viewResolvers.addViewResolver(viewResolver);
    }

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
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
