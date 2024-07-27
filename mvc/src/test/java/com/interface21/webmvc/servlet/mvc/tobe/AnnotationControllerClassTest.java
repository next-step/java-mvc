package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationControllerClassTest {

    @Test
    @DisplayName("클래스의 인스턴스를 가져온다")
    void testGetNewInstance() {
        AnnotationControllerClass controller = new AnnotationControllerClass(TestController.class);
        Optional<Object> instance = controller.getNewInstance();

        assertThat(instance).isPresent();
        assertThat(instance.get()).isInstanceOf(TestController.class);
    }

    @Test
    @DisplayName("@RequestMapping 어노테이션이 붙어있는 메서드 리스트를 조회한다")
    void testGetRequestMappingMethod() {
        AnnotationControllerClass controller = new AnnotationControllerClass(TestController.class);
        Method[] methods = controller.getRequestMappingMethod();

        assertThat(methods).hasSize(1);
        assertThat(methods[0].getName()).isEqualTo("annotatedMethod");
    }

    @Controller
    static class TestController {
        @RequestMapping
        public void annotatedMethod() {}

        public void nonAnnotatedMethod() {}
    }
}