package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.exception.InternalServerError;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotFoundException;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ViewMissingException;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JsonViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JspViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.RedirectViewResolver;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

        processDispatchResult(modelAndView, handlerExecution, request, response);
    }

    private HandlerExecution getHandler(HttpServletRequest request) {
        return (HandlerExecution) handlerMappings.stream()
                                                 .map(handlerMapping -> handlerMapping.getHandler(request))
                                                 .filter(Objects::nonNull)
                                                 .findFirst()
                                                 .orElseThrow(() -> new NotFoundException(request));
    }

    @Nullable
    private ModelAndView process(HandlerExecution handlerExecution,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            return handlerExecution.handle(request, response);
        } catch (Exception e) {
            throw new InternalServerError(e);
        }
    }

    private void processDispatchResult(@Nullable ModelAndView modelAndView,
                                       HandlerExecution handlerExecution,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            if (modelAndView == null) {
                renderNotFound(handlerExecution, request, response);
                throw new NotFoundException(request);
            }

            View view = getView(modelAndView, handlerExecution);
            Map<String, Object> model = modelAndView.getModel();

            render(view, model, request, response);
        } catch (Exception e) {
            throw new InternalServerError(e);
        }
    }

    private View getView(ModelAndView modelAndView, HandlerExecution handlerExecution) {
        View view = modelAndView.getView();
        if (view != null) {
            return view;
        }

        String viewName = modelAndView.getViewName();
        if (viewName != null) {
            return resolveViewName(handlerExecution, viewName);
        }

        throw new ViewMissingException();
    }

    private void render(View view,
                        Map<String, Object> model,
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        view.render(model, request, response);
    }

    private void renderNotFound(HandlerExecution handlerExecution, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        View view = resolveViewName(handlerExecution, NOT_FOUND_VIEW_NAME);
        view.render(Collections.emptyMap(), request, response);
    }

    private View resolveViewName(HandlerExecution handlerExecution, String viewName) {
        return viewResolvers.stream()
                            .map(viewResolver -> viewResolver.resolveView(viewName, handlerExecution))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElseThrow(ViewMissingException::new);
    }
}
