# 만들면서 배우는 스프링
[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## @MVC 구현하기

### 학습목표
- @MVC를 구현하면서 MVC 구조와 MVC의 각 역할을 이해한다.
- 새로운 기술을 점진적으로 적용하는 방법을 학습한다.

### 시작 가이드
1. 미션을 시작하기 전에 학습 테스트를 먼저 진행합니다.
    - [Junit3TestRunner](study/src/test/java/reflection/Junit3TestRunner.java)
    - [Junit4TestRunner](study/src/test/java/reflection/Junit4TestRunner.java)
    - [ReflectionTest](study/src/test/java/reflection/ReflectionTest.java)
    - [ReflectionsTest](study/src/test/java/reflection/ReflectionsTest.java)
    - 나머지 학습 테스트는 강의 시간에 풀어봅시다.
2. 학습 테스트를 완료하면 LMS의 1단계 미션부터 진행합니다.

## 학습 테스트
1. [Reflection API](study/src/test/java/reflection)
2. [Servlet](study/src/test/java/servlet)


### [STEP2]  Legacy MVC와 @MVC 통합하기
#### 목표: HandlerMapping과 HandlerAdapter를 만드는 과정
- HandlerExecution는 request를 handle하여 modelAndView 응답을 만들고,
- HandlerMapping에서는 handler 목록을 만들어주고,
- Handlers에서는 handler 목록을 관리하고,
- HandlerRegistry에서는 mapping을 통해 handlers를 만들어주고,
- HandlerAdapter에선 전달받은 handler를 handle시키고 response를 상황에 맞게 처리(render 등)한다.
#### TODO
- [x] AnnotationHandlerMapping initialize 리팩토링
  - [X] ControllerScanner 에게 컨트롤러를 찾아서 인스턴스 생성하는 역할을 맡긴다.
  - [X] 스캔한 컨트롤러 정보를 바탕으로 Map<HandlerKey, HandlerExecution> handlerExecutions을 생성한다.
- [X] DispatcherServlet 에서  ManualHandlerMapping, AnnotationHandlerMapping을 모두 초기화
  - [X] HandlerMapping 인터페이스 추가
  - [X] HandlerAdapter 인터페이스 추가


### [STEP3] JSON View 구현하기
#### TODO
- [X] JsonView render 구현 후 UserController가 정상동작하는지 테스트

### [STEP4] 4단계 - Controller 메서드 인자 매핑
#### TODO
- [ ] method의 parameter types를 통해 parsing할 수 있는 parameter parser
  - [X] query param parser -> 메소드 파라미터의 type, name 보고 request.getParameter 에서 추출
  - [X] path variable parser -> @PathVariable이 있을경우 메소드 파라미터의 type, name 보고 /{}/{} path에 특정한 양식 ({})으로 추출 
  - [ ] parameter typed parser -> 특정 타입을 deserialization. request.getParameter에서 추출
- [ ] handlerExecution에서 invoke 시점에 parser로 parsing
- [ ] testusercontroller로 확인
