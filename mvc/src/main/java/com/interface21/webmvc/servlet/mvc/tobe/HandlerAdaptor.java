package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NoExactHandlerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class HandlerAdaptor {
    private List<HandlerMapping> handlerMappings;

    public HandlerAdaptor(
            List<HandlerMapping> handlerMappings
    ) {
        this.handlerMappings = handlerMappings;
        handlerMappings.stream()
                .forEach(HandlerMapping::initialize);
    }

    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object handler = getHandler(request);
        if (handler instanceof HandlerExecution) {
            return ((HandlerExecution)handler).handle(request, response);
        } else {
            throw new NoExactHandlerException("적절한 컨트롤러가 없습니다.");
        }
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(handler -> handler != null)
                .findFirst()
                .orElseThrow(() -> new NoExactHandlerException("적절한 컨트롤러가 없습니다."));
    }
}
