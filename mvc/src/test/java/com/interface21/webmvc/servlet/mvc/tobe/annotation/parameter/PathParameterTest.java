package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.webmvc.servlet.ModelAndView;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PathParameterTest {

    private final Parameter[] parameters = TestUserController.class
            .getMethod("createString", String.class, int.class, int.class, int.class)
            .getParameters();

    PathParameterTest() throws NoSuchMethodException {
    }

    @Test
    void PathVariable이_없다면_빈_PathParameter를_생성한다() {
        PathParameter actual = PathParameter.of("/", parameters[0]);
        assertAll(
                () -> assertThat(actual.getUrlPattern()).isEqualTo("/"),
                () -> assertThat(actual.isPathVariable()).isFalse(),
                () -> assertThat(actual.getPathVariableValue()).isEqualTo("")
        );
    }

    @Test
    void PathVariable이_있다면_PathVariable값을_파싱하여_생성한다() {
        PathParameter actual = PathParameter.of("/", parameters[1]);
        assertAll(
                () -> assertThat(actual.getUrlPattern()).isEqualTo("/"),
                () -> assertThat(actual.isPathVariable()).isTrue(),
                () -> assertThat(actual.getPathVariableValue()).isEqualTo("age")
        );
    }

    @Test
    void PathVariable값이_없는데_파싱하려는_경우_예외가_발생한다() {
        PathParameter pathParameter = PathParameter.of("/", parameters[2]);
        assertThatThrownBy(() -> pathParameter.parsePathVariable("/1", "userAge1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("PathVariable이 없는 경우 호출할 수 없다");
    }

    @Test
    void PathVariable이_빈값이면_파라미터이름으로_파싱한다() {
        PathParameter pathParameter = PathParameter.of("/{userAge1}", parameters[3]);
        String actual = pathParameter.parsePathVariable("/1", "userAge1");
        assertThat(actual).isEqualTo("1");
    }

    @Test
    void PathVariable값이_있으면_해당값으로_파싱한다() {
        PathParameter pathParameter = PathParameter.of("/{age}", parameters[1]);
        String actual = pathParameter.parsePathVariable("/1", "userAge");
        assertThat(actual).isEqualTo("1");
    }

    private static class TestUserController {

        public ModelAndView createString(
                String userId,
                @PathVariable(value = "age") int userAge,
                int userAge1,
                @PathVariable int userAge2
        ) {
            return null;
        }
    }
}
