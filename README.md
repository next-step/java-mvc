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



## 🚀 4단계 - Controller 메서드 인자 매핑

### HttpServletRequest, HttpServletResponse 외의 파라미터들도 입력 받을 수 있게 하자

[ ] Controller 메서드의 인자 타입에 따라 HttpServletRequest에서 값을 꺼내와 자동으로 형 변환을 한 후 매핑하는 등의 작업을 자동 처리하도록 만들자.
- [x] ArgumentResolver 인터페이스 및 구현체 개발
  (HttpServletRequestResolver, HttpServletResponseResolver, PathVariableResolver, RequestParamResolver)
- [x] MethodArgumentResolvers 클래스 구현
  (여러 ArgumentResolver를 관리하고 적절한 리졸버 선택)
- [x] HandlerExecution 클래스 개선
  (Method 객체를 사용하여 파라미터 정보 처리)