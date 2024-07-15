package camp.nextstep;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.asis.ForwardController;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import samples.TestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ManualHandlerAdapterTest {

    private final ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();

    @Test
    void Controller_handler를_지원가능으로_판단한다() {
        boolean actual = manualHandlerAdapter.accept(new ForwardController("/test"));
        assertThat(actual).isTrue();
    }

    @Test
    void Controller가_아닌_handler는_지원불가능으로_판단한다() {
        boolean actual = manualHandlerAdapter.accept(new TestController());
        assertThat(actual).isFalse();
    }

    @Test
    void handling된_ModelAndView를_반환한다() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView actual = manualHandlerAdapter.handle(new ForwardController("/test"), request, response);
        assertThat(actual.getView()).isEqualTo(new JspView("/test"));
    }
}
