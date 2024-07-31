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

## Mission 1

- [X] @MVC 구현하기
    - [X] AnnotationHandlerMapping 구현
        - [X] initialize 메서드 구현
        - [X] getHandler 메서드 구현
    - [X] AnnotationHandlerMappingTest 통과
    - [X] HandlerExecution 구현

## Mission 2

- [X] Legacy MVC와 @MVC 통합하기
    - [x] ControllerScanner 추가
        - [x] Relfections 객체로 @Controller가 설정된 모든 클래스를 찾는 기능 구현
        - [x] 각 클래스의 인스턴스를 생성하는 기능 구현
    - [x] Map<HandlerKey, HandlerExecution> handlerExecutions을 생성하는 기능 구현
        - [x] @RequestMapping이 붙어있는 메서드 정보를 찾아서 HandlerKey와 HandlerExecution 객체를 생성
        - [x] Map<HandlerKey, HandlerExecution> handlerExecutions을 생성
    - [X] AnnotationHandlerMapping과 ManualHandlerMapping를 통합
        - [X] HandlerMapping 인터페이스 추가
        - [X] DispatcherServlet의 초기화 과정에서 ManualHandlerMapping, AnnotationHandlerMapping을 모두 초기화
        - [X] DispatcherServlet의 getHandler 메서드에서 ManualHandlerMapping, AnnotationHandlerMapping을 모두 조회
        - [X] HandlerAdapter 인터페이스 추가

## Mission 3

- [ ] JsonView 클래스를 구현하기
    - [ ] HTML 이외에 JSON으로 응답할 수 있도록 JsonView 클래스를 구현
- [ ] Legacy MVC 제거하기
    - [ ] app 모듈에 있는 모든 컨트롤러를 어노테이션 기반 MVC로 변경
    - [ ] asis 패키지에 있는 레거시 코드를 삭제해도 서비스가 정상 동작하도록 리팩터링
    - [ ] DispatcherServlet도 app 패키지가 아닌 mvc 패키지로 이동
