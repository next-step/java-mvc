package camp.nextstep.controller;

import camp.nextstep.domain.User;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private final UserController userController = new UserController();

    @Test
    void 현재_로그인된_유저의_정보를_반환한다() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        User givenUser = new User(1L, "jinyoung", "password", "jinyoungchoi95@gmail.com");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(givenUser);

        // when
        ModelAndView actual = userController.show(request, response);

        // then
        assertAll(
                () -> assertThat(actual.getView()).isInstanceOf(JsonView.class),
                () -> assertThat(actual.getModel()).containsExactly(Map.entry("user", givenUser))
        );
    }

    @Test
    void 현재_로그인되지_않았다면_401을_리다이랙한다() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // when
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        // then
        ModelAndView actual = userController.show(request, response);
        assertThat(actual.getView()).isEqualTo(new JspView("redirect:/401.jsp"));
    }
}
