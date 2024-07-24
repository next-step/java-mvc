package reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class Junit3TestRunner {

    public static final String PREFIX_TEST = "test";

    @Test
    void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        // TODO Junit3Test 에서 test 로 시작하는 메소드 실행

        // (1) for 문 사용
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
            if(method.getName().startsWith(PREFIX_TEST)) {
                method.invoke(instance);
            }
        }

        /**
         * output:
         * Running Test1
         * Running Test2
         */

        // (2) stream 사용
        Arrays.stream(clazz.getMethods())
            .filter(method -> method.getName().startsWith(PREFIX_TEST))
            .forEach(method -> {
                try {
                    method.invoke(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
