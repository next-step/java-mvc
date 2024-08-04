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



## 🚀 2단계 - 점진적인 리팩터링

### Legacy MVC와 @MVC 통합하기AnnotationHandlerMapping은
- Legacy MVC 프레임워크와 @MVC 프레임워크가 공존하도록 만들자
- 회원가입 컨트롤러를 아래와 같이 변경해도 정상 동작해야 한다

```java
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest req, HttpServletResponse res) {
        // ...
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse res) {
        // ...
    }
}
```
### AnnotationHandlerMapping
- [x] Controller Scanner 추가
- [x] 컨트롤러 메서드 정보로 HandlerExecution 생성하기

### DispatcherServlet
- [x] HandlerMapping 인터페이스 추가
- [x] DispatcherServlet의 초기화 과정에서 ManualHandlerMapping, AnnotationHandlerMapping 초기화
- [x] HandlerAdapter 인터페이스 추가
    - [x] AnnotationHandlerMapping은 HandlerExecution을 반환한다
    - [x] ManualHandlerMapping은 Controller를 반환한다
