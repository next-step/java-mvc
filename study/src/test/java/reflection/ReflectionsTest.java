package reflection;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.annotation.Controller;
import reflection.annotation.Repository;
import reflection.annotation.Service;

import java.util.Set;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    void showAnnotationClass() {
        final Reflections reflections = new Reflections("reflection.examples");

        // @Controller 애노테이션이 설정된 모든 클래스 찾아 로그로 출력
        final Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        log.info("@Controller annotated classes: {}", controllerClasses);

        // @Service 애노테이션이 설정된 모든 클래스 찾아 로그로 출력
        final Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
        log.info("@Service annotated classes: {}", serviceClasses);

        // @Repository 애노테이션이 설정된 모든 클래스 찾아 로그로 출력
        final Set<Class<?>> repositoryClasses = reflections.getTypesAnnotatedWith(Repository.class);
        log.info("@Repository annotated classes: {}", repositoryClasses);
    }
}
