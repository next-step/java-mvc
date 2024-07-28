package camp.nextstep.controller.support;

import camp.nextstep.TomcatStarter;

import java.net.http.HttpResponse;

public class RequestUtils {
    private static final int PORT = 8081;
    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final TomcatStarter tomcat = new TomcatStarter(WEBAPP_DIR_LOCATION, PORT);

    static {
        tomcat.start();
    }

    public static HttpResponse<String> send(final String path) {
        return HttpUtils.sendGet(PORT, path);
    }

    public static HttpResponse<String> sendPost(final String path) {
        return HttpUtils.sendPost(PORT, path);
    }
}
