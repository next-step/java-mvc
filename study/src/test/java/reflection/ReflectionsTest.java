package reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.annotation.Controller;
import reflection.annotation.Repository;
import reflection.annotation.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    @DisplayName("클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.")
    void showAnnotationClass() {
        Reflections reflections = new Reflections("reflection.examples");

        printClassesAnnotatedWith(reflections, Controller.class);
        printClassesAnnotatedWith(reflections, Service.class);
        printClassesAnnotatedWith(reflections, Repository.class);
    }

    private void printClassesAnnotatedWith(Reflections reflections, Class<? extends Annotation> annotation) {
        reflections.getTypesAnnotatedWith(annotation)
                   .forEach(System.out::println);
    }

    @Test
    @DisplayName("Type 중에서 클래스만 골라낸다")
    void classTypeTest() {
        Reflections reflections = new Reflections("reflection.sampleclasses");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(Controller.class);

        List<String> all = filterTypes(types, this::alwaysTrue);
        assertThat(all).containsExactlyInAnyOrder(
                "reflection.sampleclasses.SampleInterface",
                "reflection.sampleclasses.SampleClass",
                "reflection.sampleclasses.SampleRecord",
                "reflection.sampleclasses.SampleEnum");

        List<String> enums = filterTypes(types, Class::isEnum);
        assertThat(enums).containsExactlyInAnyOrder("reflection.sampleclasses.SampleEnum");

        List<String> interfaces = filterTypes(types, Class::isInterface);
        assertThat(interfaces).containsExactlyInAnyOrder("reflection.sampleclasses.SampleInterface");

        List<String> records = filterTypes(types, Class::isRecord);
        assertThat(records).containsExactlyInAnyOrder("reflection.sampleclasses.SampleRecord");

        List<String> classes = filterTypes(types, this::isClass);
        assertThat(classes).containsExactlyInAnyOrder("reflection.sampleclasses.SampleClass");
    }

    private List<String> filterTypes(Set<Class<?>> typesAnnotatedWith, Predicate<Class<?>> predicate) {
        return typesAnnotatedWith
                .stream()
                .filter(predicate)
                .map(Class::getName)
                .toList();
    }

    private boolean alwaysTrue(Class<?> it) {
        return true;
    }

    private boolean isClass(Class<?> it) {
        return it.getSuperclass() == Object.class;
    }
}
