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


### 🚀 1단계 - @MVC 구현하기
- [x] : 1. @MVC Framework 테스트 통과하기
- [x] : 2. JspView 클래스를 구현한다.

### 🚀 2단계 - 점진적인 리팩터링
#### 기능 요구 사항
- Legacy MVC와 @MVC 통합하기
- 컨트롤러 인터페이스 기반 MVC 프레임워크와 @MVC 프레임워크가 공존하도록 만들자.

- [x] : 1. ControllerScanner 클래스를 통해 컨트롤러를 찾는 기능 추가
- [x] : 2. 컨트롤러의 메서드 정보로 HandlerExecution을 생성한다.
- [x] : 3. HandlerMapping 인터페이스 추가
- [x] : 4. HandlerAdapter 인터페이스 추가

### 🚀 3단계 - JSON View 구현하기
#### 기능 요구 사항
- [x] : 1. JsonView 클래스를 구현한다.
- [x] : 2. Legacy MVC 제거하기

### 🚀 4단계 - Controller 메서드 인자 매핑
#### 기능 요구 사항
- Controller 메서드의 인자 타입에 따라 HttpServletRequest에서 값을 꺼내와 자동으로 형 변환을 한 후 매핑하는 등의 작업을 자동 처리

- [x] : 1. HandlerMethodArgumentResolver 인터페이스 추가
