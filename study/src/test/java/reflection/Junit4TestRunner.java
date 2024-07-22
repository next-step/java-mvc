package reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> {
                    try {
                        Assertions.assertThatNoException()
                            .isThrownBy(() -> method.invoke(clazz.getDeclaredConstructor().newInstance()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
