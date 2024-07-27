package reflection;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Junit3TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // TODO Junit3Test에서 test로 시작하는 메소드 실행
        Junit3Test junit3Test = clazz.getConstructor().newInstance();

        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        for (Method method : methods) {
            method.invoke(junit3Test);
            assertThat(outputStream.toString()).startsWith("Running Test");
        }
    }
}
