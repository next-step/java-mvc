package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapterRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecutionAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerMappingRegistry;

public class MvcConfig {

    private static final String BASE_PACKAGE = "camp.nextstep";

    private final HandlerMappingRegistry handlerMappingRegistry;
    private final HandlerAdapterRegistry handlerAdapterRegistry;

    private MvcConfig() {
        handlerMappingRegistry = initHandlerMappingRegistry();
        handlerAdapterRegistry = initHandlerAdapterRegistry();
    }

    public static MvcConfig getInstance() {
        return SingletonHelper.instance;
    }

    private HandlerMappingRegistry initHandlerMappingRegistry() {
        return new HandlerMappingRegistry(new AnnotationHandlerMapping(BASE_PACKAGE));
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
