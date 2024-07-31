package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.ServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ServletModelAttributeMethodProcessor implements HandlerMethodArgumentResolver {

    private final ParameterBinders parameterBinders = new ParameterBinders();

    @Override
    public boolean supportsParameter(final Parameter parameter) {
        return Object.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public Object resolveArgument(final Parameter parameter, final ServletWebRequest webRequest) throws Exception {
        final Class<?> clazz = parameter.getType();
        final ServletRequest request = webRequest.getRequest();

        final Constructor<?> constructor = getConstructor(clazz);

        final Object[] parameters = Arrays.stream(constructor.getParameters())
                .map(constructorParameter -> bindParameter(constructorParameter, request))
                .toArray();

        try {
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } finally {
            constructor.setAccessible(false);
        }
    }

    private Constructor<?> getConstructor(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .findFirst()
                .orElseThrow(() -> new ArgumentNotResolvedException("Constructor not found - [" + clazz.getSimpleName() + "]"));
    }

    private Object bindParameter(final Parameter constructorParameter, final ServletRequest request) {
        final ParameterBinder binder = parameterBinders.getBinder(constructorParameter);
        final String arg = request.getParameter(constructorParameter.getName());

        return binder.bind(arg, constructorParameter);
    }
}
