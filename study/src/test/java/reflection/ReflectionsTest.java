package reflection;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.annotation.Controller;
import reflection.annotation.Repository;
import reflection.annotation.Service;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    void showAnnotationClass() {
        Reflections reflections = new Reflections("reflection.examples");

        reflections.getTypesAnnotatedWith(Controller.class).forEach(it -> log.info("@Controller " + it.getName()));
        reflections.getTypesAnnotatedWith(Service.class).forEach(it -> log.info("@Service " + it.getName()));
        reflections.getTypesAnnotatedWith(Repository.class).forEach(it -> log.info("@Repository " + it.getName()));
    }
}
