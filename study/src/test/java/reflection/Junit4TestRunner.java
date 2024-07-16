package reflection;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @DisplayName("Junit4Test 클래스의 메서드 중 @MyTest 애노테이션이 설정된 메서드를 찾아 실행한다.")
    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        Junit4Test test = clazz.getDeclaredConstructor().newInstance();

        Stream.of(clazz.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(MyTest.class))
            .forEach(method -> {
                try {
                    method.invoke(test);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
