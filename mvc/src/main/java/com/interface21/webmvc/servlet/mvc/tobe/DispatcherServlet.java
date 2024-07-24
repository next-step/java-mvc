package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
    }

    @Override
    public void init() {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep.controller");
        annotationHandlerMapping.initialize();
        handlerMappings.add(annotationHandlerMapping);
    }

    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) throws ServletException {
        final String requestURI = request.getRequestURI();
        log.debug("Method : {}, Request URI : {}", request.getMethod(), requestURI);

        Object handler = getHandler(request);

        ModelAndView modelAndView = getHandlerAdapter(handler, request, response);

        try {
            render(modelAndView, request, response);
        } catch (Exception e) {
            throw new ServletException("응답을 그리던 중에 예외가 발생했습니다.", e);
        }
    }

    private Object getHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(request);
            if (handler != null) return handler;
        }
        return null;
    }

    private ModelAndView getHandlerAdapter(Object handler, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (handler instanceof HandlerExecution handlerExecution) {
                return handlerExecution.handle(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException("핸들러 동작 중에 예외가 발생했습니다.", e);
        }

        throw new RuntimeException("매핑이 없어요: " + request.getRequestURI());
    }

    private void render(ModelAndView mav, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = mav.getModel();
        mav.getView().render(model, request, response);
    }
}
