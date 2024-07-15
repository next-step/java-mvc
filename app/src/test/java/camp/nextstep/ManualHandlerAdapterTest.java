package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.asis.ForwardController;
import org.junit.jupiter.api.Test;
import samples.TestController;

import static org.assertj.core.api.Assertions.assertThat;

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
}
