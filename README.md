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

--- 

# TODO

### 1단계
기능 요구 사항
1. - [X] @MVC Framework 테스트 통과하기
      - [X] AnnotationHandlerMapping#initialize 가 호출되면 @RequestMapping 정보를 참고해 맵핑 정보를 초기화한다
      - [X] AnnotationHandlerMapping#handler 를 호출하면 @RequestMapping 에 맵핑된 handleExecution 을 반환한다

2. - [X] JspView 클래스를 구현한다.
  
- DispatcherServlet 클래스의 service 메서드에서 어떤 부분이 뷰에 대한 처리를 하고 있는지 파악해서 JspView 클래스로 옮겨보자.


### 2단계
기능 요구 사항
```markdown
컨트롤러 인터페이스 기반 MVC 프레임워크와 @MVC 프레임워크가 공존하도록 만들자.
```
1. AnnotationHandlerMappingTest
   - [X] `handlerExecutions`은 `request path`와 `http method`에 실행할 `HandlerExecution` 인스턴스를 맵핑한 자료구조다
1. `ControllerScanner`
   - [X] `@Controller` 가 선언된 클래스를 스캔한다
   - [X] `Relfections` 객체로 스캔한 모든 클래스의 인스턴스를 생성한다
2. `MethodScanner`
   - [X] 스캔한 모든 컨트롤러의 메서드를 스캔하여 `@RequestMapping` 어노테이션 정보를 추출한다
2. `HandlerExecution`
    - [X] `HandlerExecution`을 생성한다
    - [X] `HandlerExecution`는 실행할 메서드의 인스턴스와 실행할 메서드를 인스턴스 변수로 갖는다.
    - [X] `Map<HandlerKey, HandlerExecution> handlerExecutions`을 생성한다.  
    - [ ] `HandlerKey`에 매핑된 인스턴스의 메서드를 실행한다
3. `HandlerMapping`
   - [X] URL에 맵핑된 인스턴스를 조회하는 기능을 명세한 인터페이스다
   - [X] DispatcherServlet에서 HandlerMapping 구현체를 생성한다
4. `HandlerAdapter`  
   - [X] `HandlerMapping` 를 통해 찾은 인스턴스의 메서드를 호출하고 결과를 반환하는 기능이 명세된 인터페이스다 