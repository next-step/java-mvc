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

아래와 같은 형태의 컨트롤러를 지원하는 프레임워크를 구현한다.

```
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

- ###  1. @MVC Framework 테스트 통과하기
    - [x] AnnotationHandlerMappingTest 통과

- ### 2. JspView 클래스를 구현한다.
    - [ ] JspView 클래스 구현한다
    - [ ] service 메서드에서 뷰에 대한 처리를 하는 부분을 JspView 로 옮긴다.
    - [ ] AnnotationHandlerMappingTest 클래스의 실패하는 테스트를 통과하도록 구현한다.