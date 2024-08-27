package com.interface21.webmvc.servlet.mvc.tobe.support;

import org.junit.jupiter.api.Test;
import samples.TestUser;
import samples.TestUserController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParameterConverterTest {
    @Test
    void 파라미터_매핑이_성공적으로_동작() throws NoSuchMethodException {
        // given
        Class<?> clazz = TestUserController.class;
        Method method = clazz.getMethod("create_javabean", TestUser.class);
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("userId", new String[]{"test"});
        parameterMap.put("password", new String[]{"pw"});
        parameterMap.put("age", new String[]{"12"});

        // when
        Object[] result = ParameterConverter.convertToMethodArg(method, "test", parameterMap);

        assertEquals(1, result.length);
        assertTrue(result[0] instanceof TestUser);
    }

    @Test
    void 단일원시타입_파라미터_매핑이_성공적으로_동작() throws NoSuchMethodException {
        Class<?> clazz = TestUserController.class;
        Method method = clazz.getMethod("create_int_long", long.class, int.class);
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("id", new String[]{"1"});
        parameterMap.put("age", new String[]{"12"});

        // when
        Object[] result = ParameterConverter.convertToMethodArg(method, "test", parameterMap);

        assertEquals(2, result.length);
    }

    @Test
    void 경로변수_추출_성공() throws NoSuchMethodException {
        Class<?> clazz = TestUserController.class;
        Method method = clazz.getMethod("show_pathvariable", long.class);
        Map<String, String[]> parameterMap = new HashMap<>();

        // when
        Object[] result = ParameterConverter.convertToMethodArg(method, "/users/1", parameterMap);

        assertEquals(1, result.length);
        assertEquals(1L, result[0]);
    }
}