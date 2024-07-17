package reflection;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Junit3TestRunner {

    @DisplayName("Junit3Test 클래스의 메서드 중 test로 시작하는 메서드를 찾아 실행한다.")
    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        Junit3Test test = clazz.getDeclaredConstructor().newInstance();
        Stream.of(clazz.getDeclaredMethods())
            .filter(method -> method.getName().startsWith("test"))
            .forEach(method -> {
                try {
                    method.invoke(test);
                } catch (Exception e) {
                   e.printStackTrace();
                }
            });
    }
}
