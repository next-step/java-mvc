# 🚀 1단계 - @MVC 구현하기

## 기능 요구사항

### 1. @MVC Framework 테스트 통과하기
효과적인 실습을 위해 새로운 MVC 프레임워크의 뼈대가 되는 코드(mvc 모듈의 webmvc.servlet.mvc.tobe 패키지)와 테스트 코드를 제공하고 있다. AnnotationHandlerMappingTest 클래스의 테스트가 성공하면 1단계 미션을 완료한 것으로 생각하면 된다.
Tomcat 구현하기 미션에서 적용한 Controller 인터페이스는 2단계 미션에서 통합할 예정이다. Controller 인터페이스는 그대로 두고 미션을 진행한다.

[x] reflection 을 이용해서 @Controller 어노테이션이 붙은 클래스들을 조회한다.
[x] 조회한 클래스들중 @RequestMapping 어노테이션이 붙어있는 메서드를 찾고 url 과 method type 을 조회한다.
[x] AnnotationHandlerMapping 의 getHandler() 메서드 본문을 구현한다.
[x] AnnotationHandlerMappingTest 의 테스트 코드를 성공시킨다.

### 2. JspView 클래스를 구현한다.
webmvc.org.springframework.web.servlet.view 패키지에서 JspView 클래스를 찾을 수 있다.
DispatcherServlet 클래스의 service 메서드에서 어떤 부분이 뷰에 대한 처리를 하고 있는지 파악해서 JspView 클래스로 옮겨보자.

[x] com.interface21.webmvc.servlet.view JspView 클래스에서 render 메서드 본문을 구현한다

[forward 와 sendRedirect 의 차이]

RequestDispatcher 와 forward 란 클라이언트로부터 요청을 받고 이를 다른 리소스(서블릿, html, jsp) 로 넘겨주는 역할을 하는 인터페이스
- forward()
   - 사용자 요청에 의해 컨테이너에서 생성된 request, response 를 다른 리소스로 넘겨주는 역할을 한다.
   - response 하지 않고 다른 리소스로 전달하는 역할 
  
```java
request.getRequestDispatcher(viewName).forward(request, response);
```

- sendRedirect()
   - 클라이언트와 서버간의 통신이 끊김

```java
if (viewName.startsWith(JspView.REDIRECT_PREFIX)) { // prefix 가 redirect 로 시작하면 sendRedirect 메서드 호출
            response.sendRedirect(viewName.substring(JspView.REDIRECT_PREFIX.length()));
            return;
}
```

- 참고: https://sgcomputer.tistory.com/235 


### 참고사항
프레임워크 영역과 서비스 영역을 분리하기 위해 멀티모듈을 적용했다.
mvc 모듈은 프레임워크, app 모듈은 프로덕션 영역이다.

### 힌트
AnnotationHandlerMappingTest 클래스의 실패하는 테스트를 통과하도록 구현해보자.