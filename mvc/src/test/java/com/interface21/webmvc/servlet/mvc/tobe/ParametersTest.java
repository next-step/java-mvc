package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {
    @Test
    void 파라미터를_추출해서_저장() throws NoSuchMethodException {
        Class<?> classObj = TestUserController.class;
        Method method = classObj.getDeclaredMethod("test", List.class);

        Parameter[] parameters = method.getParameters();

        List<String> test = List.of("a");
        System.out.println(parameters[0].getParameterizedType().equals(test.getClass()));
        System.out.println();
    }

    @Test
    void 파라미터를_추출해서_저장2() throws NoSuchMethodException {
        Class<?> classObj = TestUserController.class;
        Method method = classObj.getDeclaredMethod("create_string", String.class, String.class);

        Parameter[] parameters = method.getParameters();

        List<String> test = List.of("a");
        System.out.println();
    }
}