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

### 🚀 4단계 - Controller 메서드 인자 매핑


#### 기능 요구사항
모든 Controller 메서드의 인자가 HttpServletRequest request, HttpServletResponse response라서 사용자가 전달하는 값을 매번 HttpServletRequest request에서 가져와 형 변환을 해야하는 불편함이 있다.

Controller 메서드의 인자 타입에 따라 HttpServletRequest에서 값을 꺼내와 자동으로 형 변환을 한 후 매핑하는 등의 작업을 자동 처리하도록 만들자.
또한 URL을 통해서도 동적으로 값을 전달하는 방법이 있으면 좋겠다. 예를 들어 다음과 같이 개발하는 것이 가능하면 좋겠다.

```java
public class TestUserController {
    private static final Logger logger = LoggerFactory.getLogger(TestUsersController.class);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        logger.debug("userId: {}, password: {}", userId, password);
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_int_long(long id, int age) {
        logger.debug("id: {}, age: {}", id, age);
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUser testUser) {
        logger.debug("testUser: {}", testUser);
        return null;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        logger.debug("userId: {}", id);
        return null;
    }
}
```
