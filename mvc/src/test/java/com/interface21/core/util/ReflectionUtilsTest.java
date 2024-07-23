package com.interface21.core.util;

import com.interface21.web.bind.annotation.RequestMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class ReflectionUtilsTest {


    @DisplayName("RequestMapping 어노테이션이 선언된 메소드를 반환한다.")
    @Test
    public void getMethodsWithAnnotationTypeTest() throws NoSuchMethodException {

        var basePackages = new Object[] {"samples"};
        var results = ReflectionUtils.getMethodsWithAnnotationType(basePackages, RequestMapping.class);

        assertThat(results).isNotNull();
        Method actualMethod = Arrays.stream(TestController.class.getDeclaredMethods())
                                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                                    .findFirst()
                                    .orElseThrow(NoSuchMethodException::new);

        assertThat(results).contains(actualMethod);
    }

}