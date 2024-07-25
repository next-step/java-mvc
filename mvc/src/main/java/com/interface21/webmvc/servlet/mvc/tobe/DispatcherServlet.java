package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.HandlingExecutionException;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NoMappingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            modelAndView.render(request, response);
        } catch (Exception e) {
            throw new ServletException("응답을 그리던 중에 예외가 발생했습니다.", e);
        }
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                              .map(handlerMapping -> handlerMapping.getHandler(request))
                              .filter(Objects::nonNull)
                              .findFirst()
                              .orElse(null);
    }

    private ModelAndView getHandlerAdapter(Object handler,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws ServletException {
        try {
            if (handler instanceof HandlerExecution handlerExecution) {
                return handlerExecution.handle(request, response);
            }
        } catch (Exception e) {
            throw new HandlingExecutionException(e);
        }

        throw new NoMappingException(request);
    }
}
