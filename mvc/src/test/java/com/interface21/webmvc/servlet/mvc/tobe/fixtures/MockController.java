package com.interface21.webmvc.servlet.mvc.tobe.fixtures;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

@Controller
public class MockController {

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public void mockGet() {
        System.out.println("Mock GET method");
    }
}
