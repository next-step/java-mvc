package com.interface21.webmvc.servlet.mvc.tobe.exception;

public class RequestMethodNotDefinedException extends RuntimeException {

  public RequestMethodNotDefinedException() {
    super("RequestMethod가 정의되지 않았습니다. ");
  }
}
