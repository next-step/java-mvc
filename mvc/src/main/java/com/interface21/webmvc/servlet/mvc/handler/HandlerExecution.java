package com.interface21.webmvc.servlet.mvc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestParam;
import com.interface21.web.http.MediaType;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import com.interface21.webmvc.servlet.mvc.support.QueryStringParser;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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

                Class<?> type = parameter.getType();
                arguments.add(type.cast(uriValue));

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

                Class<?> type = parameter.getType();
                arguments.add(type.cast(value));
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
                // ModelAttribute는 key=value&key2=value 와 같은 스트링을 "객체"로 변환할 때 사용한다

                // 쿼리 스트링인 경우
                String queryString = request.getQueryString();

                // x-www-form-urlencoded 인 경우 바디에서 파싱 -> ContentType 헤더가 x-www-form-urlencoded일 때만 쓴다
                String body = parseRequestBody(request);

                QueryStringParser queryStringParser = new QueryStringParser();
                Map<String, String> parsedQueryString = queryStringParser.parse(queryString);  //todo : or body

                // todo : parsedQueryString을 이용해서 key에 해당하는 필드에 value를 넣어주어야 한다.
                Class<?> type = parameter.getType();
                Constructor<?> declaredConstructor = type.getDeclaredConstructor();
                Object instance = declaredConstructor.newInstance();

                // todo : field에 값 주입하는거 학습 테스트 작성
                Field field = type.getDeclaredField("key");
                field.set(instance, parsedQueryString.get("key"));

                Arrays.stream(type.getDeclaredFields())
                        .forEach(f -> {
                            f.setAccessible(true);
                            String name = f.getName();
                            try {
                                f.set(instance, parsedQueryString.get(name));
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        });  // 또는 parsedQueryString.keySet()을 순회하면서 getDeclaredField(key)를 사용

            } else {
                Class<?> type = parameter.getType();
                if (type == ServletRequest.class) {
                    arguments.add(request);
                }

                if (type == ServletResponse.class) {
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

    public <T extends Annotation> T extractAnnotation(Class<T> annotationType) {
        return method.getAnnotation(annotationType);
    }
}
