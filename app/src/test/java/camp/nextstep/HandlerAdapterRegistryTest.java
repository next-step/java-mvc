package camp.nextstep;

import camp.nextstep.manual.ManualHandlerAdapter;
import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.asis.ForwardController;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerAdapterRegistryTest {

    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(new ManualHandlerAdapter());

    @Test
    void 지원가능한_adapter가_없으면_예외가_발생한다() {
        HandlerExecution handlerExecution = new HandlerExecution(new HandlerExecutionController(), "handle");
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        assertThatThrownBy(() -> handlerAdapterRegistry.handle(handlerExecution, request, response))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원가능한 adapter가 없습니다.");
    }

    @Test
    void handler에_맞는_adapter로_실행한다() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView actual = handlerAdapterRegistry.handle(new ForwardController("/test"), request, response);
        assertThat(actual.getView()).isEqualTo(new JspView("/test"));
    }

    @Controller
    private static class HandlerExecutionController {

        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView(new JspView(""))
                    .addObject("name", "jinyoung");
        }
    }
}
