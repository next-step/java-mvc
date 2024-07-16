package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

public class HandlerAdapterRegistry {

    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(HandlerAdapter... handlerAdapters) {
        this.handlerAdapters = Arrays.stream(handlerAdapters)
                .toList();
    }

    public ModelAndView handle(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        return handlerAdapter.handle(handler, request, response);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.accept(handler))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("지원가능한 adapter가 없습니다."));
    }
}
