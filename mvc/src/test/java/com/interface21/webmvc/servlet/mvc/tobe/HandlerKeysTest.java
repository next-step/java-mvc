package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;

class HandlerKeysTest {
    @DisplayName("uriPrefix와 RequestMapping 정보를 기반으로 HandlerKey를 생성한다.")
    @Test
    void test() {
        String uriPrefix = "/prefix";
        List<RequestMapping> annotations = ReflectionUtils.getAllMethods(ControllerForThisTest1.class, ReflectionUtils.withAnnotation(RequestMapping.class))
                .stream()
                .map(method -> method.getAnnotation(RequestMapping.class))
                .toList();

        RequestMapping requestMapping = annotations.getFirst();
        HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, requestMapping);

        assertThat(handlerKeys).hasSize(1);
        assertThat(handlerKeys).contains(new HandlerKey(uriPrefix + requestMapping.value(), RequestMethod.GET));
    }

    @Controller
    private static class ControllerForThisTest1 {
        @RequestMapping(value = "/test", method = RequestMethod.GET)
        public ModelAndView method(final HttpServletRequest request, final HttpServletResponse response) {
            return new ModelAndView(new JspView(""));
        }
    }

    @DisplayName("RequestMethod가 명시되지 않은 @RequestMapping 애노테이션은 모든 method에 대해 HandlerKey를 생성한다.")
    @Test
    void test2() {
        List<RequestMapping> annotations = ReflectionUtils.getAllMethods(ControllerForThisTest2.class, ReflectionUtils.withAnnotation(RequestMapping.class))
                .stream()
                .map(method -> method.getAnnotation(RequestMapping.class))
                .toList();

        RequestMapping requestMapping = annotations.getFirst();
        HandlerKeys handlerKeys = HandlerKeys.of("", requestMapping);

        assertThat(handlerKeys).hasSize(RequestMethod.values().length);
    }

    @Controller
    private static class ControllerForThisTest2 {
        @RequestMapping(value = "/test")
        public ModelAndView method(final HttpServletRequest request, final HttpServletResponse response) {
            return new ModelAndView(new JspView(""));
        }
    }
}