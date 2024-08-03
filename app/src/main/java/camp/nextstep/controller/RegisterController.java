package camp.nextstep.controller;

import camp.nextstep.dao.InMemoryUserDao;
import camp.nextstep.domain.User;
import camp.nextstep.dto.UserDto;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(UserDto userDto) throws Exception {
        final var user = new User(2,
            userDto.getAccount(),
            userDto.getPassword(),
            userDto.getEmail());
        InMemoryUserDao.save(user);

        return "redirect:/index";
    }
}
