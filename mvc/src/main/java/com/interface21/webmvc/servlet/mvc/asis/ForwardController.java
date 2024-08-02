package com.interface21.webmvc.servlet.mvc.asis;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

@Controller
public class ForwardController {

    private static final String PATH = "/index.jsp";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final HttpServletRequest request, final HttpServletResponse response) {
        return PATH;
    }
}
