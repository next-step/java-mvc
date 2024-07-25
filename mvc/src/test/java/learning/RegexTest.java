package learning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegexTest {
    @DisplayName("Matcher.matches()는 전체가 일치해야 true이다.")
    @Test
    void matchesTrue() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        assertThat(matcher.matches()).isTrue();
    }

    @DisplayName("Matcher.matches()는 전체가 일치하지 않으면 false이다.")
    @Test
    void matchesFalse() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        assertThat(matcher.matches()).isFalse();
    }

    @DisplayName("일치하는 시퀀스를 찾기 전에는 start,end,group 메서드를 사용할 수 없다.")
    @Test
    void canNotUseStartEndGroup() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        assertAll(
                () -> assertThatThrownBy(matcher::start).isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(matcher::end).isInstanceOf(IllegalStateException.class),
                () -> assertThatThrownBy(matcher::group).isInstanceOf(IllegalStateException.class)
        );
    }

    @DisplayName("Matcher.find()는 정규식과 일치하는 시퀀스를 찾고, start,end,group을 사용할 수 있다.")
    @Test
    void find() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        matcher.find();
        assertAll(
                () -> assertThat(matcher.start()).isEqualTo(12),
                () -> assertThat(matcher.end()).isEqualTo(31),
                () -> assertThat(matcher.group()).isEqualTo("example@example.com")
        );
    }

    @DisplayName("Matcher.matches()는 모든 문자열이 일치하는 시퀀스를 찾고, start,end,group을 사용할 수 있다.")
    @Test
    void matches() {
        String regex = "My email is (\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        matcher.matches();
        assertAll(
                () -> assertThat(matcher.start()).isEqualTo(0),
                () -> assertThat(matcher.end()).isEqualTo(31),
                () -> assertThat(matcher.group()).isEqualTo("My email is example@example.com"),
                () -> assertThat(matcher.group(1)).isEqualTo("example"),
                () -> assertThat(matcher.group(2)).isEqualTo("example.com")
        );
    }

    @DisplayName("Matcher.group()은 일치한 문자열 자체를 나타낸다.")
    @Test
    void matcherGroup() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        matcher.find();
        assertThat(matcher.group()).isEqualTo("example@example.com");
    }

    @DisplayName("Matcher.group(N)은 정규식 패턴과 일치한 부분의 문자열을 반환한다. 그룹은 () 기준으로 나뉜다.")
    @Test
    void matcherGroup1() {
        String regex = "(\\w+)@(\\w+\\.\\w+)";
        String text = "My email is example@example.com";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        matcher.find();
        assertAll(
                () -> assertThat(matcher.group(1)).isEqualTo("example"),
                () -> assertThat(matcher.group(2)).isEqualTo("example.com")
        );
    }

    @DisplayName("find()를 통해 일치하는 문자열에 순서대로 접근할 수 있다.")
    @Test
    void findInOrder() {
        String template = "/{var1}/{var2}";

        Pattern pattern = Pattern.compile("\\{([^/]+)}");
        Matcher matcher = pattern.matcher(template);

        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        assertThat(result).containsExactly("var1", "var2");
    }

    @DisplayName("매치된 문자열을 모두 대체한다.")
    @Test
    void replaceAll() {
        String template = "/{var1}/{var2}";

        Pattern pattern = Pattern.compile("\\{([^/]+)}");
        Matcher matcher = pattern.matcher(template);

        String actual = matcher.replaceAll("하하");
        String expected = "/하하/하하";
        assertThat(actual).isEqualTo(expected);
    }
}
