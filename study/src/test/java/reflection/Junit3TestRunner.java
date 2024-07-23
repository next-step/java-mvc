package reflection;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class Junit3TestRunner {

    @Test
    void run() throws Exception {
        final PrintStream printStream = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Class<Junit3Test> clazz = Junit3Test.class;
        final Junit3Test instance = clazz.getDeclaredConstructor().newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(this::isTestMethod)
                .forEach(method -> handleMethod(method, instance));

        System.setOut(printStream);

        final String actual = out.toString();
        assertThat(actual).contains("Running Test1", "Running Test2");
    }

    private boolean isTestMethod(final Method method) {
        return method.getName().startsWith("test");
    }

    private void handleMethod(final Method method, final Junit3Test instance) {
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
