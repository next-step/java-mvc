# 🚀 1단계 - @MVC 구현하기

## 기능 요구사항

### 1. @MVC Framework 테스트 통과하기
효과적인 실습을 위해 새로운 MVC 프레임워크의 뼈대가 되는 코드(mvc 모듈의 webmvc.servlet.mvc.tobe 패키지)와 테스트 코드를 제공하고 있다. AnnotationHandlerMappingTest 클래스의 테스트가 성공하면 1단계 미션을 완료한 것으로 생각하면 된다.
Tomcat 구현하기 미션에서 적용한 Controller 인터페이스는 2단계 미션에서 통합할 예정이다. Controller 인터페이스는 그대로 두고 미션을 진행한다.

### 2. JspView 클래스를 구현한다.
webmvc.org.springframework.web.servlet.view 패키지에서 JspView 클래스를 찾을 수 있다.
DispatcherServlet 클래스의 service 메서드에서 어떤 부분이 뷰에 대한 처리를 하고 있는지 파악해서 JspView 클래스로 옮겨보자.

### 참고사항
프레임워크 영역과 서비스 영역을 분리하기 위해 멀티모듈을 적용했다.
mvc 모듈은 프레임워크, app 모듈은 프로덕션 영역이다.

### 힌트
AnnotationHandlerMappingTest 클래스의 실패하는 테스트를 통과하도록 구현해보자.