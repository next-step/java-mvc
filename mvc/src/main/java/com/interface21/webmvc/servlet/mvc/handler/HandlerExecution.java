package com.interface21.webmvc.servlet.mvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import com.interface21.webmvc.servlet.mvc.support.QueryStringParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandlerExecution {
    private final Object handler;
    private final Method method;  // @Controller 내의 @RequestMapping 애노테이션이 달린 메서드의 정보

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Parameter[] parameters = method.getParameters();
        List<Object> arguments = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String uriPattern = requestMapping.value();
                String requestURI = request.getRequestURI();

                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                String name;
                if (pathVariable.name().isBlank()) {
                    name = parameter.getName();
                } else {
                    name = pathVariable.name();
                }
                String uriValue = PathPatternUtil.getUriValue(uriPattern, requestURI, name);
                arguments.add(convertStringToParameterType(uriValue, parameter.getType()));

            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                String queryString = request.getQueryString();
                QueryStringParser queryStringParser = new QueryStringParser();
                Map<String, String> parsedQueryString = queryStringParser.parse(queryString);

                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String name;
                if (requestParam.name().isBlank()) {
                    name = parameter.getName();
                } else {
                    name = requestParam.name();
                }
                String value = parsedQueryString.get(name);

                if (requestParam.required() && value == null) {
                    throw new IllegalArgumentException("필수 값 누락");
                }

                arguments.add(convertStringToParameterType(value, parameter.getType()));
            } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                String contentType = request.getHeader("Content-Type");
                if (!contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
                    throw new IllegalArgumentException("Content-Type 헤더가 JSON이 아님");
                }
                String requestBody = parseRequestBody(request);
                ObjectMapper objectMapper = new ObjectMapper();
                Class<?> type = parameter.getType();
                Object body = objectMapper.readValue(requestBody, type);
                arguments.add(type.cast(body));

            } else if (parameter.isAnnotationPresent(ModelAttribute.class)) {
                String value;
                if (request.getMethod().equals(RequestMethod.POST.name())) {
                    value = parseRequestBody(request);
                } else {
                    value = request.getQueryString();
                }
                QueryStringParser queryStringParser = new QueryStringParser();
                Map<String, String> parsedQueryString = queryStringParser.parse(value);

                Class<?> type = parameter.getType();
                Constructor<?>[] declaredConstructors = type.getDeclaredConstructors();
                Constructor<?> declaredConstructor = declaredConstructors[0];
                Parameter[] constructorParameters = declaredConstructor.getParameters();
                Object[] objects = Arrays.stream(constructorParameters)
                        .map(param -> {
                            Class<?> type1 = param.getType();
                            if (type1.isPrimitive() && type1.equals(boolean.class)) {
                                return false;
                            }
                            if (type1.isPrimitive()) {
                                return 0;
                            }
                            return null;
                        })
                        .toArray();
                Object instance = declaredConstructor.newInstance(objects);
                parsedQueryString.keySet()
                        .forEach(key -> {
                            try {
                                Field field = type.getDeclaredField(key);
                                field.setAccessible(true);
                                field.set(instance, convertStringToParameterType(parsedQueryString.get(key), field.getType()));
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException("%s에 해당하는 객체 필드명이 없습니다.".formatted(key), e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        });

                arguments.add(instance);
            } else {
                Class<?> type = parameter.getType();
                if (type == HttpServletRequest.class) {
                    arguments.add(request);
                }

                if (type == HttpServletResponse.class) {
                    arguments.add(response);
                }
                // 나머지는 지원하지 않는다. 또는 기본적으로 지원하는걸 지원한다?
            }
        }

        // invoke(객체, 파라미터[])
        return (ModelAndView) method.invoke(handler, arguments.toArray());
    }

    private String parseRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private <T> T convertStringToParameterType(String value, Class<?> parameterType) {
        if (parameterType == Integer.class || parameterType == int.class) {
            return (T) Integer.valueOf(value);
        } else if (parameterType == Double.class || parameterType == double.class) {
            return (T) Double.valueOf(value);
        } else if (parameterType == Boolean.class || parameterType == boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (parameterType == Long.class || parameterType == long.class) {
            return (T) Long.valueOf(value);
        } else if (parameterType == Float.class || parameterType == float.class) {
            return (T) Float.valueOf(value);
        } else if (parameterType == Short.class || parameterType == short.class) {
            return (T) Short.valueOf(value);
        } else if (parameterType == Byte.class || parameterType == byte.class) {
            return (T) Byte.valueOf(value);
        } else if (parameterType == String.class) {
            return (T) value;
        } else {
            throw new IllegalArgumentException("Unsupported target type: " + parameterType.getName());
        }
    }

    public <T extends Annotation> T extractAnnotation(Class<T> annotationType) {
        return method.getAnnotation(annotationType);
    }
}
