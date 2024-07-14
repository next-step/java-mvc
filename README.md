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
