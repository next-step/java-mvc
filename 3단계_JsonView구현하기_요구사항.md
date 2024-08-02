# 🚀 3단계 - JSON View 구현하기

## 기능 요구사항
### 1. JsonView 클래스를 구현한다
webmvc.org.springframework.web.servlet.view 패키지에서 JsonView 클래스를 찾을 수 있다.
HTML 이외에 JSON으로 응답할 수 있도록 JsonView 클래스를 구현해보자.

### 2. Legacy MVC 제거하기
app 모듈에 있는 모든 컨트롤러를 어노테이션 기반 MVC로 변경한다.
그리고 asis 패키지에 있는 레거시 코드를 삭제해도 서비스가 정상 동작하도록 리팩터링하자.
Legacy MVC를 제거하고 나서 DispatcherServlet도 app 패키지가 아닌 mvc 패키지로 옮겨보자.

### 힌트
- 아래 컨트롤러를 추가해서 정상 동작하는지 테스트한다.
```java
@Controller
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @RequestMapping(value = "/api/user", method = RequestMethod.GET)
  public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
    final String account = request.getParameter("account");
    log.debug("user id : {}", account);

    final ModelAndView modelAndView = new ModelAndView(new JsonView());
    final User user = InMemoryUserRepository.findByAccount(account)
        .orElseThrow();

    modelAndView.addObject("user", user);
    return modelAndView;
  }
}
```

- JSON을 자바 객체로 변환할 때 Jackson 라이브러리를 사용한다.
- Jackson 라이브러리 공식 문서를 읽어보고 사용법을 익힌다.
- JSON으로 응답할 때 ContentType은 MediaType.APPLICATION_JSON_UTF8_VALUE으로 반환해야 한다.
- model에 데이터가 1개면 값을 그대로 반환하고 2개 이상이면 Map 형태 그대로 JSON으로 변환해서 반환한다.

---
## 요구사항 정리
[x] JsonView 클래스를 구현한다
    [x] objectMapper 를 사용한다
    [x] JSON으로 응답할 때 ContentType은 MediaType.APPLICATION_JSON_UTF8_VALUE으로 반환해야 한다.

## 피드백 적용
[x] 새롭게 만든 각 객체에 대해서 테스트 코드를 작성


