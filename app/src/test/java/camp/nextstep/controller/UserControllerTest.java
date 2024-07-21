package camp.nextstep.controller;

import camp.nextstep.domain.User;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("사용자가 존재할 경우, JSON 형태로 사용자 정보를 반환한다.")
    void showUserExistTest() {
        final UserController userController = new UserController();
        when(request.getParameter("account")).thenReturn("gugu");

        final ModelAndView actual = userController.show(request, response);

        assertSoftly(softly -> {
            softly.assertThat(actual.getView()).isInstanceOf(JsonView.class);
            softly.assertThat(actual.getModel().get("user")).isInstanceOf(User.class);
            softly.assertThat(actual.getModel().get("user")).extracting("account").isEqualTo("gugu");
        });
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 경우, 404 페이지로 redirect 한다")
    void showUserNotExistTest() {
        final UserController userController = new UserController();
        when(request.getParameter("account")).thenReturn("none");

        final ModelAndView actual = userController.show(request, response);

        assertThat(actual).isEqualTo(ModelAndView.ofJspView("redirect:/404.jsp"));
    }
}
