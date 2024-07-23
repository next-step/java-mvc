package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        for (Object object : basePackage) {
            String packageStr = (String) object;
            Set<Class<?>> set = new Reflections(packageStr)
                    .getTypesAnnotatedWith(Controller.class);
            putRequestMappingMethodFromClassSet(set);
        }
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void putRequestMappingMethodFromClassSet(Set<Class<?>> set) {
        for (Class<?> classObj : set) {
            putRequestMappingMethodFromClass(classObj);
        }
    }

    private void putRequestMappingMethodFromClass(Class<?> classObj) {
        for (final Method method : classObj.getDeclaredMethods()) {
            putRequestMappingMethod(method, classObj);
        }
    }

    private void putRequestMappingMethod(Method method, Class<?> classObj) {
        if (method.isAnnotationPresent(RequestMapping.class)) {
            method.setAccessible(true);
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            for (RequestMethod requestMethod : annotation.method()) {
                HandlerKey handlerKey = new HandlerKey(annotation.value(), requestMethod);
                Constructor<?> ctor = null;
                HandlerExecution handlerExecution = null;
                try {
                    ctor = classObj.getConstructor();
                    handlerExecution = new HandlerExecution(
                            method,
                            ctor.newInstance()
                    );
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                handlerExecutions.put(handlerKey, handlerExecution);
            }
        }
    }


    public Object getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(
                request.getRequestURI(),
                RequestMethod.valueOf(request.getMethod())
        );

        return handlerExecutions.get(handlerKey);
    }
}
