package camp.nextstep;

import java.io.IOException;
import java.util.stream.Stream;

public class Application {

    private static final int DEFAULT_PORT = 8080;

    public static void main(final String[] args) throws Exception {
        final int port = defaultPortIfNull(args);
        final var tomcat = new TomcatStarter(port);
        tomcat.start();
        await();
        tomcat.stop();
    }

    private static int defaultPortIfNull(final String[] args) {
        return Stream.of(args)
                .findFirst()
                .map(Integer::parseInt)
                .orElse(DEFAULT_PORT);
    }

    private static void await() throws IOException {
        // make the application wait until we press any key.
        System.in.read();
    }
}
