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

## 1단계 요구사항

[x] [Junit3TestRunner](study/src/test/java/reflection/Junit3TestRunner.java)
[x] [Junit4TestRunner](study/src/test/java/reflection/Junit4TestRunner.java)
[x] [ReflectionTest](study/src/test/java/reflection/ReflectionTest.java)
[x] [ReflectionsTest](study/src/test/java/reflection/ReflectionsTest.java)
[x] @MVC Framework 테스트 통과하기
  - [x] @Controller를 가져와서 Mapping을 생성한다.
   - 기능: @Controller로 표기된 클래스들을 스캔해서 (uri, method) 를 key로 method를 value로 가진 해쉬맵을 생성한다.
   - 조건: Request Method가 없을때는, 모든 RequestMethod를 key값으로 생성한다. (생성자가 public 이어야한다.) 
   - 결과: Uri 를 가진 Request를 대상으로 Mapping을 가지고 알맞은 method를 실행한다.

[x] JspView 클래스 구현하기
   - [x] service에서 뷰처리 부분 파악하기
   - [x] JspView 클래스로 옮기기

## 2단계 요구사항
[] 피드백 사항
- [x] 클래스명과 변수명 맞추기
- [x] 유틸성 메서드 createHandlerKeys 빼두기
- [x] RequestMethod에 값지정 안되있을때, 디폴트 값을 지정함으로써 분기 피하기
- [x] 기본 생성자가 없을때, 메세지 정확하게 하기


[x] 프론트 컨트롤러 패턴을 구현해서 AnnotationHandlerMapping과 ManualHandlerMapping을 통합합니다.
- [x] HandlerMapping 인터페이스 구현
  - [x] DispatcherServlet 초기화할때, AnnotationHandlerMapping과 ManualHandlerMapping 초기화
  - [x] HandlerMapping을 List로 관리한다
- [x] HandlerAdapter 인터페이스 구현
- 
  [x] 피드백 사항
- [x] 메서드 명 handler 변경
- [x] 레거시 코드와 @Controller 에노테이션 공존 확인
- [x] 테스트 추가 작성해보기
- [x] DispatcherServlet 멤버 변수 다른곳으로 이전하기

## 3단계 요구사항

[x] JsonView 클래스를 구현한다.
    - [x] Jackson 사용해서 json으로 만들어서 Response 객체와 ContentLength를 지정해준다.
[x] Legacy MVC 제거한다.
    - [x] @RequestMapping 과 ModelAndView를 반환하게끔 리팩토링한다.
    - [x] /api/user 컨트롤러를 추가하고 정상 동작 테스트한다.
    - [x] model 데이터 값에 다른 반환형태를 달리한다.
    - [x] ContentType은 Application_JSON_UTF8_VALUE 로 변경한다.
