package learning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParameterTest {

    private static class Car {
        public void method1(@TestAnnotation String name, Integer age) {
            System.out.println("name = " + name);
            System.out.println("name = " + name);
        }
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAnnotation {}

    @DisplayName("Parameter의 Type 정보를 알 수 있다.")
    @Test
    void getParameterType() {
        Method[] methods = Car.class.getMethods();
        Method method1 = methods[0];

        Parameter[] parameters = method1.getParameters();

        assertAll(
                () -> assertThat(parameters[0].getType()).isEqualTo(String.class),
                () -> assertThat(parameters[1].getType()).isEqualTo(Integer.class)
        );
    }

    @DisplayName("Parameter에 Annotation이 달렸는지 확인할 수 있다")
    @Test
    void isAnnotationPresent() {
        Method[] methods = Car.class.getMethods();
        Method method1 = methods[0];

        Parameter[] parameters = method1.getParameters();

        assertAll(
                () -> assertThat(parameters[0].isAnnotationPresent(TestAnnotation.class)).isTrue(),
                () -> assertThat(parameters[1].isAnnotationPresent(TestAnnotation.class)).isFalse()
        );
    }

    @DisplayName("Parameter의 이름을 확인할 수 있다 : options.compilerArgs << '-parameters' 라는 컴파일 옵션을 줘야한다.")
    @Test
    void getName() {
        Method[] methods = Car.class.getMethods();
        Method method1 = methods[0];

        Parameter[] parameters = method1.getParameters();

        assertAll(
                () -> assertThat(parameters[0].getName()).isEqualTo("name"),
                () -> assertThat(parameters[1].getName()).isEqualTo("age")
        );
    }
}
