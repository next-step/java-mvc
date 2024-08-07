package samples;

import static org.junit.jupiter.api.Assertions.*;

import com.interface21.core.util.ReflectionUtils;
import com.interface21.webmvc.servlet.mvc.AnnotationControllerClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestUserControllerTest {

  @Test
  void tsets() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    Class<TestUserController> clazz = TestUserController.class;
    Method[] methods = clazz.getDeclaredMethods();

    Method method2 = null;
    for(Method method : methods) {
      if(method.getName().equals("show_pathvariable")) {
        method2 = method;
      }
    }

    Object[] args = new Object[1];
    args[0] = 1L;
    Object instance = new AnnotationControllerClass(clazz).getNewInstance();
    Object invoke = method2.invoke(instance, args);

    System.out.println(invoke);
  }

}