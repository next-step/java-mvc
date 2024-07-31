package com.interface21.webmvc.servlet.mvc.tobe;

public class ArgumentNotResolvedException extends RuntimeException {
    public ArgumentNotResolvedException(final String message) {
        super(message);
    }

    public ArgumentNotResolvedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
