//package com.interface21.webmvc.servlet.mvc.tobe;
//
//import com.interface21.webmvc.servlet.ModelAndView;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.lang.reflect.Method;
//
//public class ParameterizedHandlerExecution {
//    private Method method;
//    private Object[] objects;
//
//    public ParameterizedHandlerExecution(Method method, Object... objects) {
//        this.method = method;
//        this.objects = objects;
//    }
//
//    public ModelAndView handle(HttpServletRequest httpServletRequest) throws Exception {
//        return (ModelAndView) method.invoke(object, request, response);
//    }
//}
