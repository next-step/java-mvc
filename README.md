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

## 1단계 - @MVC 구현하기
- basePackage를 받아 하위에 있는 클래스들을 트래킹한다.
  - `@Controller` 어노테이션이 달린 클래스들만 필터링한다.
  - 해당 클래스의 메소드 중 `@RequestMapping`이 걸린 메소드를 필터링하고 value와 method를 받아온다.
  - HttpRequest를 가지고 HandlerKey를 만들어 해당하는 HandlerExecution을 반환한다.
- AnnotationHandlerMapping
  - 지원하지 않는 request값으로 handler를 요청하면 예외가 발생한다.

- HandlerExecution
  - handling할 수 있는 객체와 실행할 메소드(Method)를 들고 있는다.
  - handling할 수 있는 객체와 실행할 메소드 name만을 받아서 생성한다.
    - 없는 메소드를 요청한 경우 예외가 발생한다. (HttpServletRequest와 HttpServletResponse)를 가진다.
  - 실행하여 반환한다.
- HandlerKey
  - HttpServletRequest를 가지고 값을 파싱하여 생성할 수 있다.

## 2단계 - 점진적인 리팩터링
- ControllerScanner
  - 패키지 내에 controller어노테이션을 가진 클래스를 찾아 인스턴스화된 map을 반환한다
  - 예외는 기존 AnnotationHandlerMapping과 동일하다

- HandlerMapping
  - ManualHandlerMapping, AnnotationHandlerMapping를 HandlerMapping 인터페이스로 각각 추상화하여 저장할 수 있도록 한다.
  - 저장과 동시에 초기화한다.
- HandlerMappingRegistry
  - mapping list를 가지며 생성 시 초기화한다.
  - request에 해당하는 handler를 반환한다.
    - request에 해당하는 handler가 없는 경우 예외가 발생햔다.
    - HandlerMapping에서 Object(nullable)를 반환하고 못찾으면 Registry에서 예외를 반환하는 것으로 변경한다.
- JspView
  - redirect가 포함된 viewName인 경우 rending할 때 response로 해당 위치를 redirect시킨다.
  - render를 실행하면 model을 포함하여 requestDispatcher로 forward한다.
- HandleAdapter
  - Object Handler가 지원가능한 handler인지 확인한다.
  - 요청된 handler로 ModelAndView를 만들어 반환한다.
- HandlerAdapterRegistry
  - HandlerAdapter list를 가진다.
  - 지원가능한 handler가 없는 경우 예외가 발생한다.
  - 지원가능한 handler로 ModelAndView를 만들어 반환한다.

## 3단계 - JSON View 구현하기
- JsonView
  - response에 contentType을 application/json으로 설정한다.
  - objectMapper를 통해 model을 Serialization한다.
    - 그 값의 길이를 contentLength로 설정한다
    - response에 직렬화한 body를 저장한다
- legacy mvc를 제거한다
  - 기존 app에 있던 servlet 클래스를 모두 mvc로 이동하여도 동작하도록 수정한다
- UserController
  - 현재 로그인중인 유저가 없으면 401로 리다이랙한다
  - 현재 로그인중인 유저의 정보를 json으로 반환한다

## 4단계 - Controller 메서드 인자 매핑
- HandlerExecution은 method가 가지고 있는 파라미터의 정보를 함께 가진다
  - 파라미터의 순서에 맞게 List로 저장한다.
- MethodParameter
  - 타입을 가지고 있는다
  - HttpServletRequest에서 해당하는 타입과 이름에 맞는 값을 가져와 사용한다.
- MethodParameters
  - method.invoke를 하기위해 각 파라미터의 파싱된 값을 배열로 반환한다
