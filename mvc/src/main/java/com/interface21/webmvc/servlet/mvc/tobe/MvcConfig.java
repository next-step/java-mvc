package com.interface21.webmvc.servlet.mvc.tobe;

public class MvcConfig {

    private static final String DEFAULT_BASE_PACKAGE = "camp.nextstep";

    private final String basePackage;
    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;

    private MvcConfig() {
        handlerMappingRegistry = initHandlerMappingRegistry();
        handlerAdapterRegistry = initHandlerAdapterRegistry();
        basePackage = DEFAULT_BASE_PACKAGE;
    }

    public MvcConfig(String basePackage) {
        handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping(basePackage));
        handlerAdapterRegistry = new HandlerAdapterRegistry(new HandlerExecutionAdapter());
        this.basePackage = basePackage;
    }

    public static MvcConfig getInstance() {
        return SingletonHelper.instance;
    }

    private HandlerMappingRegistry initHandlerMappingRegistry() {
        return new HandlerMappingRegistry(new AnnotationHandlerMapping(basePackage));
    }

    private HandlerAdapterRegistry initHandlerAdapterRegistry() {
        return new HandlerAdapterRegistry(new HandlerExecutionAdapter());
    }

    public HandlerMappingRegistry handlerMappingRegistry() {
        return handlerMappingRegistry;
    }

    public HandlerAdapterRegistry handlerAdapterRegistry() {
        return handlerAdapterRegistry;
    }

    private static class SingletonHelper {
        static final MvcConfig instance = new MvcConfig();
    }
}
