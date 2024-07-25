package reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.annotation.Controller;
import reflection.annotation.Repository;
import reflection.annotation.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    void showAnnotationClass() throws Exception {

        // TODO 클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.
        Reflections reflections = new Reflections("reflection.examples",  Scanners.SubTypes.filterResultsBy(s -> true));

        reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Repository.class))
                .forEach(clazz -> log.info(clazz.getName()));

    }

    @Test
    @DisplayName("Method.invoke() 학습테스트")
    public void methodInvokeTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // given
        var car = new Car();
        var method = car.getClass().getMethod("number", String.class);

        // when
        var result = method.invoke(car, "1234");

        // then
        assertThat(result).isEqualTo("Benz:1234");
    }

}
