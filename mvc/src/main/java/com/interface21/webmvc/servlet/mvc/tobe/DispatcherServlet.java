package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.exception.InternalServerError;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ViewMissingException;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JsonViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JspViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.RedirectViewResolver;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DispatcherServlet extends HttpServlet {

    public static final String NOT_FOUND_VIEW_NAME = "404.jsp";
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private final List<HandlerMapping> handlerMappings;
    private final List<ViewResolver> viewResolvers;

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
        viewResolvers = new ArrayList<>();
    }

    @Override
    public void init() {
        initHandlerMappings();

        initViewResolvers();
    }

    private void initHandlerMappings() {
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("camp.nextstep.controller");
        annotationHandlerMapping.initialize();
        handlerMappings.add(annotationHandlerMapping);
    }

    private void initViewResolvers() {
        viewResolvers.add(new RedirectViewResolver());
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
    }

    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) {
        HandlerExecution handlerExecution = getHandler(request);

        ModelAndView modelAndView = process(handlerExecution, request, response);

        render(request, response, modelAndView, handlerExecution);
    }

    private HandlerExecution getHandler(HttpServletRequest request) {
        return (HandlerExecution) handlerMappings.stream()
                                                 .map(handlerMapping -> handlerMapping.getHandler(request))
                                                 .filter(Objects::nonNull)
                                                 .findFirst()
                                                 .orElse(null);
    }

    private ModelAndView process(Object handler, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (handler instanceof HandlerExecution handlerExecution) {
                return handlerExecution.handle(request, response);
            }
            return null;
        } catch (Exception e) {
            throw new InternalServerError(e);
        }
    }

    private void render(HttpServletRequest request,
                        HttpServletResponse response,
                        ModelAndView modelAndView,
                        HandlerExecution handlerExecution) {
        try {
            if (modelAndView == null) {
                renderNotFound(request, response, handlerExecution);
                throw new NotFoundException(request);
            }

            View view = resolveView(modelAndView, handlerExecution);
            view.render(modelAndView.getModel(), request, response);
        } catch (Exception e) {
            throw new InternalServerError(e);
        }
    }

    private View resolveView(ModelAndView modelAndView, HandlerExecution handlerExecution) {
        if (modelAndView.getView() != null) {
            return modelAndView.getView();
        }

        String viewName = modelAndView.getViewName();
        if (viewName != null) {
            return resolveView(viewName, handlerExecution);
        }

        throw new ViewMissingException();
    }

    private View resolveView(String viewName, HandlerExecution handlerExecution) {
        return viewResolvers.stream()
                            .map(viewResolver -> viewResolver.resolveView(viewName, handlerExecution))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);
    }

    private void renderNotFound(HttpServletRequest request,
                                HttpServletResponse response,
                                HandlerExecution handlerExecution) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        View view = resolveView(NOT_FOUND_VIEW_NAME, handlerExecution);
        view.render(Collections.emptyMap(), request, response);
    }
}
