package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScanner {

  private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

  private final Reflections reflections;

  private ControllerScanner(Reflections reflections) {
    this.reflections = reflections;
  }

  public static ControllerScanner of(Object... basePackage) {
    return new ControllerScanner(new Reflections(basePackage));
  }


  /*@Controller 어노테이션 클래스를 찾아서 맵으로 반환*/
  public Map<Class<?>, Object> scan() {
    Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
    return controllerClasses.stream()
        .collect(
            Collectors.toUnmodifiableMap(controllerClass -> controllerClass, controllerClass -> createControllerInstance(controllerClass)));
  }

  private Object createControllerInstance(Class<?> controllerClass) {
    try {
      Constructor<?> controllerConstructor = controllerClass.getDeclaredConstructor();
      return controllerConstructor.newInstance();
    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

}
