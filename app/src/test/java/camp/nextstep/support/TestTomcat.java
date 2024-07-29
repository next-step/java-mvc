package camp.nextstep.support;

import camp.nextstep.UncheckedServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestTomcat {

    private static final Logger log = LoggerFactory.getLogger(TestTomcat.class);

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";

    private static Tomcat tomcat;

    public static synchronized Tomcat getInstance() {
        if (tomcat == null) {
            tomcat = new Tomcat();
            tomcat.setConnector(createConnector(8080));

            final var docBase = new File(WEBAPP_DIR_LOCATION).getAbsolutePath();
            final var context = (StandardContext) tomcat.addWebapp("", docBase);
            skipTldScan(context);
            skipClearReferences(context);
        }

        return tomcat;
    }

    public void start() {
        try {
            tomcat.start();
            log.info("web server start.");
            log.info("configuring app with basedir: {}", WEBAPP_DIR_LOCATION);
        } catch (LifecycleException e) {
            throw new UncheckedServletException(e);
        }
    }

    public void stop() {
        try {
            tomcat.stop();
            tomcat.destroy();
            log.info("web server stop.");
        } catch (LifecycleException e) {
            throw new UncheckedServletException(e);
        }
    }

    private static Connector createConnector(final int port) {
        final var connector = new Connector();
        connector.setPort(port);
        return connector;
    }

    private static void skipTldScan(final Context context) {
        final var jarScanner = (StandardJarScanner) context.getJarScanner();
        final var jarScanFilter = new StandardJarScanFilter();
        jarScanFilter.setDefaultTldScan(false);
        jarScanner.setJarScanFilter(jarScanFilter);
    }

    private static void skipClearReferences(final StandardContext context) {
        /**
         * https://tomcat.apache.org/tomcat-10.1-doc/config/context.html
         *
         * setClearReferencesObjectStreamClassCaches 번역
         * true인 경우 웹 응용 프로그램이 중지되면 Tomcat은 직렬화에 사용되는
         * ObjectStreamClass 클래스에서 웹 응용 프로그램에 의해 로드된
         * 클래스에 대한 SoftReference를 찾고 찾은 모든 SoftReference를 지웁니다.
         * 이 기능은 리플렉션을 사용하여 SoftReference를 식별하므로 Java 9 이상에서
         * 실행할 때 명령줄 옵션 -XaddExports:java.base/java.io=ALL-UNNAMED를 설정해야 합니다.
         * 지정하지 않으면 기본값인 true가 사용됩니다.
         *
         * ObjectStreamClass와 관련된 메모리 누수는 Java 19 이상, Java 17.0.4 이상 및
         * Java 11.0.16 이상에서 수정되었습니다.
         * 수정 사항이 포함된 Java 버전에서 실행할 때 확인이 비활성화됩니다.
         *
         * Amazon Corretto-17.0.6은 경고 메시지가 나옴.
         * 학습과 관련 없는 메시지가 나오지 않도록 관련 설정을 끈다.
         */
        context.setClearReferencesObjectStreamClassCaches(false);
        context.setClearReferencesRmiTargets(false);
        context.setClearReferencesThreadLocals(false);
    }
}
