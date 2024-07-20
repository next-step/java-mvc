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
}
