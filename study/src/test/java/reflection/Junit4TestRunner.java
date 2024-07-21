package reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class Junit4TestRunner {

    @Test
    void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        // TODO Junit4Test에서 @MyTest 애노테이션이 있는 메소드 실행
        // (1) for 문 사용
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(MyTest.class)) {
                method.invoke(instance);
            }
        }

        // (2) stream 사용
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.isAnnotationPresent(MyTest.class))
            .forEach(method -> {
                try {
                    method.invoke(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
