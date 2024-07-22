# 만들면서 배우는 스프링
[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## @MVC 구현하기

### 학습목표
- @MVC를 구현하면서 MVC 구조와 MVC의 각 역할을 이해한다.
- 새로운 기술을 점진적으로 적용하는 방법을 학습한다.

### 시작 가이드
1. 미션을 시작하기 전에 학습 테스트를 먼저 진행합니다.
    - [x] [Junit3TestRunner](study/src/test/java/reflection/Junit3TestRunner.java)
    - [x] [Junit4TestRunner](study/src/test/java/reflection/Junit4TestRunner.java)
    - [x] [ReflectionTest](study/src/test/java/reflection/ReflectionTest.java)
    - [x] [ReflectionsTest](study/src/test/java/reflection/ReflectionsTest.java)
    - 나머지 학습 테스트는 강의 시간에 풀어봅시다.
2. 학습 테스트를 완료하면 LMS의 1단계 미션부터 진행합니다.

## 학습 테스트
1. [Reflection API](study/src/test/java/reflection)
2. [Servlet](study/src/test/java/servlet)

---
# 요구사항
## 🚀 1단계 - @MVC 구현하기
### 아래의 컨트롤러를 지원하는 프레임워크를 구현해야 한다.
```java
@Controller
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/get-test", method = RequestMethod.GET)
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response) {
        log.info("test controller get method");
        final ModelAndView modelAndView = new ModelAndView(new JspView("/get-test.jsp"));
        modelAndView.addObject("id", request.getAttribute("id"));
        return modelAndView;
    }

    @RequestMapping(value = "/post-test", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        log.info("test controller post method");
        final ModelAndView modelAndView = new ModelAndView(new JspView("/post-test.jsp"));
        modelAndView.addObject("id", request.getAttribute("id"));
        return modelAndView;
    }
}
```
- [x] `AnnotationHandlerMappingTest` 클래스의 테스트 통과하기 
  - [x] `AnnotationHandlerMapping`을 구현한다
    - [x] `@Controller`를 인식해서 처리한다 
    - [x] `@RequestMapping`을 인식해서 처리한다
      - URL과 HTTP 메서드가 있다. 
        - [x] 설정되지 않은 경우 모든 HTTP 메서드를 지원한다
      - [x] 클래스에 달린 경우 URI prefix로 활용한다
      - [x] 메서드에 달린 경우 등록한다    
- [x] `JspView` 클래스 구현하기 
  - [x] `DispatcherServlet` 클래스의 `service()` 메서드에서 뷰에 대한 처리를 하고 있는 부분을 파악한다
  - [x] 파악한 부분을 `JspView`로 옮긴다

## 🚀 2단계 - 점진적인 리팩터링
- 기존 코드를 유지하면서 신규 기능을 추가해야 한다
### Legacy MVC와 @MVC 통합하기
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
- [x] Controller Scanner 추가하기 
  - [x] `@Controller`가 설정된 모든 클래스를 찾아 인스턴스를 생성해야 한다.
    - `Map<Class<?>, Object>`
- [x] 컨트롤러 메서드 정보로 HandlerExecution 생성하기 
  - `ReflectionUtils.getAllMethods()` 활용 

### DispatcherServlet
- AnnotationHandlerMapping를 구현하는 동안 ManualHandlerMapping도 유지해야 한다.
- [x] HandlerMapping 인터페이스 추가 
  - 초기화한 2개의 HandlerMapping을 List로 관리 
- [x] HandlerAdapter 인터페이스 추가 
  - HandlerMapping 구현체에서 찾은 컨트롤러를 실행해야 한다 
  - [x] AnnotationHandlerMapping은 HandlerExecution을 반환한다
  - [x] ManualHandlerMapping은 Controller를 반환한다 
  - 둘 다 메서드를 실행해야 한다 
    ```java
    Object handler = getHandler(req);
    if (handler instanceof Controller) {
        ModelAndView mav = ((Controller)handler).execute(req, resp);
    } else if (handler instanceof HandlerExecution) {
        ModelAndView mav = ((HandlerExecution)handler).handle(req, resp);
    } else {
        // throw exception
    }
    
    ```
    
## 🚀 3단계 - JSON View 구현하기
### JsonView 클래스 구현하기 
- [] HTML 이외에 JSON으로도 응답이 가능해야 한다 

### Legacy MVC 제거하기 
- [] app 모듈의 컨트롤러를 모두 애노테이션 기반으로 변경한다 
- [] asis 패키지의 레거시 코드를 제거한다 
  - [] 서비스가 정상 동작해야 한다 
- [] DispatcherServlet을 mvc 패키지로 이동한다