package camp.nextstep.controller;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestUserController {

    private static final Logger log = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/users/string", method = RequestMethod.GET)
    public ModelAndView create_string(String userId, String password) {
        log.debug("userId: {}, password: {}", userId, password);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("userId", userId);
        mav.addObject("password", password);
        return mav;
    }

    @RequestMapping(value = "/users/long", method = RequestMethod.GET)
    public ModelAndView create_int_long(long id, int age) {
        log.debug("id: {}, age: {}", id, age);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("id", id);
        mav.addObject("age", age);
        return mav;
    }

    @RequestMapping(value = "/users/test", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUser testUser) {
        log.debug("testUser: {}", testUser);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("testUser", testUser);
        return mav;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        log.debug("userId: {}", id);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("id", id);
        return mav;
    }

    public static class TestUser {
        private String userId;
        private String password;
        private int age;

        public TestUser() {
        }

        public TestUser(String userId, String password, int age) {
            this.userId = userId;
            this.password = password;
            this.age = age;
        }

        public String getUserId() {
            return userId;
        }

        public String getPassword() {
            return password;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "TestUser{" +
                    "userId='" + userId + '\'' +
                    ", password='" + password + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
