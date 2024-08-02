package com.interface21.webmvc.servlet.mvc.fixtures;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;

public class ControllerFixtures {

  @Controller
  class TestController {
    @RequestMapping
    public void annotatedMethod() {}

    public void nonAnnotatedMethod() {}
  }

  @Controller
  class TestController1 {}

  @Controller
  class TestController2 {}

  @Controller
  class TestController3 {}

}
