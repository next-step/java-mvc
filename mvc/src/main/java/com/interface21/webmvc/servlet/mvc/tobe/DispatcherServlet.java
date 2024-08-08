package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.InternalServerError;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DispatcherServlet extends HttpServlet {

    public static final String NOT_FOUND_VIEW_NAME = "404.jsp";
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private final List<HandlerMapping> handlerMappings;

    private ViewResolvers viewResolvers;

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
        viewResolvers = new ViewResolvers();
    }

    @Override
    public void init() {
        initHandlerMappings();

        viewResolvers.initialize();
    }

    private void initHandlerMappings() {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep.controller");
        annotationHandlerMapping.initialize();
        handlerMappings.add(annotationHandlerMapping);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            try {
                HandlerExecution handlerExecution = getHandler(request);

                ModelAndView modelAndView = handlerExecution.handle(request, response);

                ViewResolver viewResolver = viewResolvers.findResolver(modelAndView, handlerExecution);

                viewResolver.render(modelAndView, request, response);
            } catch (NotFoundException e) {
                renderNotFound(request, response);
            }

        } catch (Exception e) {
            throw new InternalServerError(e);
        }
    }

    private HandlerExecution getHandler(HttpServletRequest request) {
        return (HandlerExecution) handlerMappings.stream()
                                                 .map(handlerMapping -> handlerMapping.getHandler(request))
                                                 .filter(Objects::nonNull)
                                                 .findFirst()
                                                 .orElseThrow(() -> new NotFoundException(request));
    }

    private void renderNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        ModelAndView modelAndView = new ModelAndView(NOT_FOUND_VIEW_NAME);
        viewResolvers.findResolver(modelAndView, null)
                     .render(modelAndView, request, response);
    }
}
