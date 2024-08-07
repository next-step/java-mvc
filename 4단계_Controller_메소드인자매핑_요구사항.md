# **🚀 4단계 - Controller 메서드 인자 매핑**

## 기능 요구사항

모든 Controller 메서드의 인자가 **`HttpServletRequest request, HttpServletResponse response`**라서 사용자가 전달하는 값을 매번 **`HttpServletRequest request`**에서 가져와 형 변환을 해야하는 불편함이 있다.

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

## 힌트

### **HttpServletRequest에 값 전달하기**

- spring-test에서 제공하는 MockHttpServletRequest을 활용할 수 있다.

```java
MockHttpServletRequest request = new MockHttpServletRequest();
request.addParameter("userId", "javajigi");
request.addParameter("password", "password");
```

### **메서드의 인자 이름 구하기**

- java reflection의 Parameter에서 인자 이름을 구하면 arg0, arg1과 같이 나와 이름을 활용할 수 없다.
- JDK8부터 컴파일 인수로 -parameters를 추가하면 메서드 파라미터명을 가져올 수 있다. build.gradle에 컴파일 옵션으로 추가해두었으니 참고하자.

```java
compileJava {
    options.compilerArgs << '-parameters'
}

```

compileJava 작업을 포함한 모든 JavaCompile 작업에 적용하려면 아래와 같이 옵션을 적용한다.

```java
tasks.withType(JavaCompile).configureEach {
    options.compilerArgs << '-parameters'
}

```

### **primitive type을 비교할 때**

- 메서드 인자의 type이 primitive type 인 경우 Integer.class, Long.class와 비교할 수 없다.
- primitive type을 비교할 경우 int.class, long.class로 비교할 수 있다.

```java
if (parameterType.equals(int.class)) {
    values[i] = Integer.parseInt(value);
}
if (parameterType.equals(long.class)) {
    values[i] = Long.parseLong(value);
}

```

### **PathVariable 구현을 위해 URL 매칭과 값 추출**

- 아래 제공하는 PathPatternUtil 클래스와 PathPatternParser 클래스를 사용하면 URL 매칭과 경로에서 변수를 추출할 수 있다.
- 간단한 사용법은 PathPatternUtilTest 클래스를 참고하자.

```java
public class PathPatternUtil {

    public static String getUriValue(String pattern, String path, String key) {
        final Map<String, String> uriVariables = getUriVariables(pattern, path);
        return uriVariables.get(key);
    }

    public static Map<String, String> getUriVariables(String pattern, String path) {
        if (!isUrlMatch(pattern, path)) {
            return Map.of();
        }
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.extractUriVariables(path);
    }

    public static boolean isUrlMatch(String pattern, String path) {
        final PathPatternParser parser = new PathPatternParser(pattern);
        return parser.matches(path);
    }
}

```

```java
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPatternParser {

    private final Pattern pattern;
    private final List<String> variableNames = new ArrayList<>();

    public PathPatternParser(String pattern) {
        String regex = buildRegex(pattern);
        this.pattern = Pattern.compile(regex);
    }

    private String buildRegex(String pattern) {
        Matcher matcher = Pattern.compile("\\{([^}]+)}").matcher(pattern);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            variableNames.add(matcher.group(1));
            matcher.appendReplacement(buffer, "([^/]+)");
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public boolean matches(String path) {
        return pattern.matcher(path).matches();
    }

    public Map<String, String> extractUriVariables(String path) {
        Matcher matcher = pattern.matcher(path);
        if (!matcher.matches()) {
            return Collections.emptyMap();
        }
        Map<String, String> variables = new HashMap<>();
        for (int i = 0; i < variableNames.size(); i++) {
            variables.put(variableNames.get(i), matcher.group(i + 1));
        }
        return variables;
    }
}

```

## **Spring 프레임워크 힌트**

- 이 기능은 Spring 프레임워크의 HandlerMethodArgumentResolver의 동작 원리와 같다.
- 구현에 힌트를 얻기 힘들다면 Spring 프레임워크의 HandlerMethodArgumentResolver 사용법을 익힌 후 도전해 볼 것을 추천한다.

---
## 요구사항 정리
- [ ]  Controller 메서드의 인자 타입에 따라 HttpServletRequest 에서 값을 꺼내와 자동으로 형 변환을 한 후 매핑하는 등의 작업을 자동 처리
  - [x] HandlerMethod, MethodParameter 역할을 하는 객체를 생성한다
  - [ ] InvocableHandlerMethod 클래스를 생성해서 형변환한 메서드 인자들을 리턴하는 메서드를 구현한다
  - [ ] HandlerMethodArgumentResolver 인터페이스를 구현한다  
  - [x] request path 과 urlPattern 이 일치하는 handler 를 조회한다 (pathVariable 이 쓰일 경우)
  - [ ] AbstractNamedValueMethodArgumentResolver 를 구현한다 
  - [ ] PathVariableMethodArgumentResolver 를 구현한다
  - [ ] RequestParamMethodArgumentResolver 를 구현한다
  - [ ] TestUser 용 custom 어노테이션을 만들고 Resolver 를 구현한다