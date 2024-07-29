package camp.nextstep.support;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class FormUtils {
    public static String getFormString(final Map<String, Object> params) {
        return params.entrySet().stream()
                .map(FormUtils::toKeyValuePair)
                .collect(Collectors.joining("&"));
    }

    private static String toKeyValuePair(final Map.Entry<String, Object> entry) {
        return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8);
    }
}
