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

## 1단계 미션 요구사항
- [x] 1.@MVC Framework 테스트 통과하기
- [x] 2.JspView 클래스를 구현

## 2단계 미션 요구사항
- [x] 1.AnnotationHandlerMapping 클래스 구현
- [x] 2.DispatcherServlet 클래스 구현

## 4단계 미션 요구사항
### 요구사항 - Controller 메서드의 인자 타입에 따라 자동으로 형 변환을 한 후 매핑하는 등의 작업을 자동 처리
- Controller 의 메서드의 파라미터의 타입에 따라 자동으로 형변환 후 맵핑처리
   - String, 이나 primitiveType, wrapperType 과 같은 기본적인 타입 맵핑 처리
   - 객체타입은 객체를 Reflection 으로 생성해 맵핑해준다.
      - 기본 class 는 기본생성자 이용
      - record class 는 canonical 생성자 이용
   - PathVariable 은 `@RequestMapping.value` 의 url 중 특정 pattern 을 토대로 맵핑해 넣어준다