package com.interface21.webmvc.servlet.mvc.tobe.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValueConfig {
    private final String FILE_NAME = "/application.properties";
    private final String MULTI_VALUE_SEPARATOR_STR = ",";
    private final Pattern KEY_VALUE_SEPARATOR = Pattern.compile("=");
    private final Pattern MULTI_VALUE_SEPARATOR = Pattern.compile(MULTI_VALUE_SEPARATOR_STR);
    private final int KEY_INDEX = 0;
    private final int VALUE_INDEX = 1;

    private Map<String, Object> valueMap;

    public void init() {
        this.valueMap = new HashMap<>();

        InputStream inputStream = getInputStream(FILE_NAME);
        parseAndPutValue(inputStream);
    }

    public Map<String, Object> getValueMap() {
        if (valueMap == null) {
            init();
        }
        return valueMap;
    }

    private InputStream getInputStream(
            String path
    ) {
        return this.getClass().getResourceAsStream(path);
    }

    private void parseAndPutValue(InputStream inputStream) {
        try (
                final BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream)
                )
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] keyAndValue = KEY_VALUE_SEPARATOR.split(line);
                String key = keyAndValue[KEY_INDEX];
                String value = keyAndValue[VALUE_INDEX];
                if (value.contains(MULTI_VALUE_SEPARATOR_STR)) {
                    valueMap.put(key, MULTI_VALUE_SEPARATOR.split(keyAndValue[VALUE_INDEX]));
                } else {
                    valueMap.put(key, value);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
