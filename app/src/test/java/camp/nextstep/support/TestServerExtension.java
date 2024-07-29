package camp.nextstep.support;

import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;

public class TestServerExtension implements BeforeAllCallback {

    private final Tomcat tomcat = TestTomcat.getInstance();

    @Override
    public void beforeAll(final ExtensionContext extensionContext) throws Exception {
//        TomcatServerTest annotation = getTomcatServerTest(extensionContext);
//
//        Arrays.stream(annotation.servletMappings()).forEach(servletMapping -> {
//            try {
//                final Class[] parametersClazz = servletMapping.parameters();
//                final Object[] parameters = Arrays.stream(parametersClazz).map(clazz -> {
//                    try {
//                        return clazz.getDeclaredConstructor().newInstance();
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }).toArray();
//                tomcat.addServlet(servletMapping.path(), servletMapping.servlet().getDeclaredConstructor(parametersClazz).newInstance(parameters));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });

        tomcat.start();
    }

    private TomcatServerTest getTomcatServerTest(final ExtensionContext extensionContext) {
        final Class<?> requiredTestClass = extensionContext.getRequiredTestClass();
        TomcatServerTest annotation = requiredTestClass.getAnnotation(TomcatServerTest.class);

        if (annotation == null) {
            Class<?> superclass = requiredTestClass.getSuperclass();
            while (annotation == null && superclass != null) {
                annotation = superclass.getAnnotation(TomcatServerTest.class);
                superclass = superclass.getSuperclass();
            }
        }
        return annotation;
    }

}
