package com.interface21.webmvc.servlet.mvc.asis;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

@Controller
public class ForwardController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView welcome(final HttpServletRequest req,
      final HttpServletResponse res) {
    return new ModelAndView(new JspView("/index.jsp"));
  }
}
