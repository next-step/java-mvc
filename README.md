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

### 🚀 2단계 - 점진적인 리팩터링

#### 미션 설명

새로운 MVC 프레임워크를 추가하면 기존에 구현한 컨트롤러 인터페이스 기반 MVC 프레임워크로 만든 컨트롤러도 변경 해야 할까?
실습 코드는 컨트롤러 클래스의 갯수가 적고 시스템 영향도 파악이 어렵지 않고 금방 바꿀 수 있다.
하지만 실제 서비스 되는 프로덕션 코드는 복잡하고 영향 범위가 훨씬 크다.
수백 개에서 수천 개의 클래스를 변경해야 될 수도 있다.
변경이 쉽지 않기 때문에 기존 코드를 유지하면서 신규 기능을 추가해야 한다.

#### 기능 요구 사항

Legacy MVC와 @MVC 통합하기

컨트롤러 인터페이스 기반 MVC 프레임워크와 @MVC 프레임워크가 공존하도록 만들자.
예를 들면, 회원가입 컨트롤러를 아래처럼 어노테이션 기반 컨트롤러로 변경해도 정상 동작해야 한다.

```java
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest req, HttpServletResponse res) {
        ...
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse res) {
        ...
    }
}```
