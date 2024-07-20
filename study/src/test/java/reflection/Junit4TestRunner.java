package reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@SuppressWarnings("NewClassNamingConvention")
class Junit4TestRunner {

    @Test
    @DisplayName("Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행")
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Junit4Test junit4Test = clazz.getConstructor().newInstance();

        Arrays.stream(clazz.getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(MyTest.class))
                .forEach(it -> {
                    try {
                        it.invoke(junit4Test);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
