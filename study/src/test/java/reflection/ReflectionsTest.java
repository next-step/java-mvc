package reflection;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.annotation.Controller;
import reflection.annotation.Repository;
import reflection.annotation.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionsTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    void showAnnotationClass() throws Exception {
        final PrintStream printStream = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        Reflections reflections = new Reflections("reflection.examples");

        final Set<Class<?>> controllerAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);
        final Set<Class<?>> serviceAnnotatedWith = reflections.getTypesAnnotatedWith(Service.class);
        final Set<Class<?>> repositoryAnnotatedWith = reflections.getTypesAnnotatedWith(Repository.class);
        
        printClass(controllerAnnotatedWith);
        printClass(serviceAnnotatedWith);
        printClass(repositoryAnnotatedWith);
        
        System.setOut(printStream);
        final String actual = out.toString();
        assertThat(actual).contains(
                "class reflection.examples.QnaController",
                "class reflection.examples.MyQnaService",
                "class reflection.examples.JdbcQuestionRepository",
                "class reflection.examples.JdbcUserRepository");
    }

    private void printClass(final Set<Class<?>> classes) {
        classes.forEach(System.out::println);
    }
}
