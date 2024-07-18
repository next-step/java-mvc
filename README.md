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
- [] `@RequestMapping`  구현하기 
  - [] URL을 설정할 수 있다
  - [] HTTP 메서드를 설정할 수 있다
    - [] 설정되지 않은 경우 모든 HTTP 메서드를 지원한다  
- [] `AnnotationHandlerMappingTest` 클래스의 테스트 통과하기 
  - [] `AnnotationHandlerMapping`을 구현한다 
  - [] `DispatcherServlet`의 `ManualHandlerMapping`을 `AnnotationHandlerMapping`으로 교체한다 
- [] `JspView` 클래스 구현하기 
  - [] `DispatcherServlet` 클래스의 `service()` 메서드에서 뷰에 대한 처리를 하고 있는 부분을 파악한다
  - [] 파악한 부분을 `JspView`로 옮긴다 
