package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.view.JsonView;

@Controller
public class TestController {
    public record TestUserDto(String name, int age) {
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ModelAndView test(@RequestBody TestUserDto testUserDto) {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("test", testUserDto);
        return modelAndView;
    }
}
