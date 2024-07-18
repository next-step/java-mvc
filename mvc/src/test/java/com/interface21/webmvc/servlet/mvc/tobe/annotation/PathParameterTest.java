package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class PathParameterTest {

    @Test
    void PathVariable이_없다면_빈_PathParameter를_생성한다() throws NoSuchMethodException {
        Parameter parameter = TestUserController.class
                .getMethod("createString", String.class, int.class)
                .getParameters()[0];
        PathParameter actual = PathParameter.of("/", parameter);
        assertThat(actual).isEqualTo(new PathParameter("/", false, ""));
    }

    @Test
    void PathVariable이_있다면_PathVariable값을_파싱하여_생성한다() throws NoSuchMethodException {
        Parameter parameter = TestUserController.class
                .getMethod("createString", String.class, int.class)
                .getParameters()[1];
        PathParameter actual = PathParameter.of("/", parameter);
        assertThat(actual).isEqualTo(new PathParameter("/", true, "age"));
    }

    private static class TestUserController {

        public ModelAndView createString(String userId, @PathVariable(value = "age") int userAge) {
            return null;
        }
    }
}
