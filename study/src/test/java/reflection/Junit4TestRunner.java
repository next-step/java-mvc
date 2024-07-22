package reflection;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        final PrintStream printStream = System.out;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Class<Junit4Test> clazz = Junit4Test.class;
        final Junit4Test instance = clazz.getDeclaredConstructor().newInstance();

        System.setOut(printStream);

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(this::isMyTestMethod)
                .forEach(method -> handleMethod(method, instance));
    }

    private boolean isMyTestMethod(final Method method) {
        return method.isAnnotationPresent(MyTest.class);
    }

    private void handleMethod(final Method method, final Junit4Test instance) {
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
