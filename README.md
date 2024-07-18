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

## 프로그래밍 요구사항

- 모든 로직에 단위테스트 구현
- 자바 코드 컨벤션 지키면서 프로그래밍
- 한 메서드에 한 단계의 들여쓰기만 한다.

## 기능목록 및 commit 로그 요구사항

- feat (feature)
- fix (bug fix)
- docs (documentation)
- style (formatting, missing semi colons, …)
- refactor
- test (when adding missing tests)
- chore (maintain)

## 기능 요구 사항

### 🚀 1단계 - @MVC 구현하기

#### 기능 요구 사항

1. @MVC Framework 테스트 통과하기
    - 효과적인 실습을 위해 새로운 MVC 프레임워크의 뼈대가 되는 코드(mvc 모듈의 webmvc.servlet.mvc.tobe 패키지)와 테스트 코드를 제공하고 있다.
      AnnotationHandlerMappingTest 클래스의 테스트가 성공하면 1단계 미션을 완료한 것으로 생각하면 된다.
    - Tomcat 구현하기 미션에서 적용한 Controller 인터페이스는 2단계 미션에서 통합할 예정이다. Controller 인터페이스는 그대로 두고 미션을 진행한다.
2. JspView 클래스를 구현한다.
   `webmvc.org.springframework.web.servlet.view` 패키지에서 JspView 클래스를 찾을 수 있다.
   DispatcherServlet 클래스의 service 메서드에서 어떤 부분이 뷰에 대한 처리를 하고 있는지 파악해서 JspView 클래스로 옮겨보자.

#### 참고사항

프레임워크 영역과 서비스 영역을 분리하기 위해 멀티모듈을 적용했다.
mvc 모듈은 프레임워크, app 모듈은 프로덕션 영역이다.

#### 힌트

AnnotationHandlerMappingTest 클래스의 실패하는 테스트를 통과하도록 구현해보자.
