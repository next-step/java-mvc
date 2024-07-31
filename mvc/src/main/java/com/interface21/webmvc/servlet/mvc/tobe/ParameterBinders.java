package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Parameter;
import java.util.List;

public class ParameterBinders {
    private final List<ParameterBinder> parameterBinders = List.of(new StringParameterBinder());

    public ParameterBinder getBinder(final Parameter parameter) {
        return parameterBinders.stream().filter(binder -> binder.supports(parameter))
                .findFirst()
                .orElseThrow(() -> new ParameterNotBindException("No binder was found to support parameter type - [" + parameter.getType() + "]"));
    }
}
