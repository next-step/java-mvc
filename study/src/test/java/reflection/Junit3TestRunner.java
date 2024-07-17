package reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Junit3TestRunner {

    @Test
    void run() throws InvocationTargetException, IllegalAccessException {
        Class<Junit3Test> clazz = Junit3Test.class;
        for (Method method : clazz.getMethods()) {
            if (method.getName().startsWith("test")) {
                method.invoke(new Junit3Test());
            }
        }
    }
}
