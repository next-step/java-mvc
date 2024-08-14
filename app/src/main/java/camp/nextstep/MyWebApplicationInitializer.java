package camp.nextstep;

import com.interface21.web.WebApplicationInitializer;
import com.interface21.webmvc.servlet.mvc.tobe.DispatcherServlet;
import com.interface21.webmvc.servlet.mvc.tobe.MvcConfig;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for {@link WebApplicationInitializer}
 * implementations that register a {@link DispatcherServlet} in the servlet context.
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    private static final String DEFAULT_SERVLET_NAME = "dispatcher";
    private static final String BASE_PACKAGE = "camp.nextstep";

    @Override
    public void onStartup(final ServletContext servletContext) {
        final MvcConfig mvcConfig = new MvcConfig(BASE_PACKAGE);
        final var dispatcherServlet = new DispatcherServlet(mvcConfig);

        final var registration = servletContext.addServlet(DEFAULT_SERVLET_NAME, dispatcherServlet);
        if (registration == null) {
            throw new IllegalStateException("Failed to register servlet with name '" + DEFAULT_SERVLET_NAME + "'. " +
                    "Check if there is another servlet registered under the same name.");
        }

        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        log.info("Start AppWebApplication Initializer");
    }
}
