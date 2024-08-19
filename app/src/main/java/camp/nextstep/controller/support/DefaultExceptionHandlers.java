package camp.nextstep.controller.support;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.webmvc.servlet.exception.HttpRequestMethodNotSupportedException;
import com.interface21.webmvc.servlet.exception.NoHandlerFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultExceptionHandlers {

    @ExceptionHandler(IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(400);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void handle(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(404);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void handle(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(405);
    }

}
