package com.interface21.webmvc.servlet.view;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public interface ModelWriter {

    boolean support(Map<String, ?> model);

    void write(Map<String, ?> model, HttpServletResponse response) throws IOException;

}
