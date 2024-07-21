package reflection;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("주어진 클래스의 올바른 클래스 명을 출력한다")
    void givenObject_whenGetsClassName_thenCorrect() {
        final Class<Question> clazz = Question.class;

        assertThat(clazz.getSimpleName()).isEqualTo("Question");
        assertThat(clazz.getName()).isEqualTo("reflection.Question"); // 클래스 표준 명칭
        assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question"); // 클래스 표준 명칭
    }

    @Test
    @DisplayName("클래스 명으로 오브젝트를 생성할 때 클래스 명을 출력한다")
    void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
        final Class<?> clazz = Class.forName("reflection.Question"); // "Question" 으로 생성하면 ClassNotFoundException 발생

        assertThat(clazz.getSimpleName()).isEqualTo("Question");
        assertThat(clazz.getName()).isEqualTo("reflection.Question");
        assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question");
    }

    @Test
    @DisplayName("런타임에 클래스의 필드를 가져온다")
    void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        final Object student = new Student();
        final Field[] fields = student.getClass().getDeclaredFields();
        final List<String> actualFieldNames = Arrays.stream(fields).map(Field::getName).toList();

        assertThat(actualFieldNames).contains("name", "age");
    }

    @Test
    @DisplayName("선언된 클래스의 메서드를 가져온다")
    void givenClass_whenGetsMethods_thenCorrect() {
        final Class<?> animalClass = Student.class;
        final Method[] methods = animalClass.getDeclaredMethods();
        final List<String> actualMethods = Arrays.stream(methods).map(Method::getName).toList();

        assertThat(actualMethods)
                .hasSize(3)
                .contains("getAge", "toString", "getName");
    }

    @Test
    @DisplayName("클래스의 생성자 리스트를 가져온다")
    void givenClass_whenGetsAllConstructors_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Constructor<?>[] constructors = questionClass.getConstructors();

        assertThat(constructors).hasSize(2);
    }

    @Test
    @DisplayName("리플렉션으로 클래스 생성자를 조회하고 조회한 생성자로 새 인스턴드를 생성한다")
    void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws Exception {
        final String TEST_WRITER = "gugu";
        final String TEST_TITLE_1 = "제목1";
        final String TEST_CONTENT_1 = "내용1";
        final String TEST_TITLE_2 = "제목2";
        final String TEST_CONTENT_2 = "내용2";

        final Class<?> questionClass = Question.class;

        final Constructor<?> firstConstructor = questionClass.getConstructors()[0];
        final Constructor<?> secondConstructor = questionClass.getConstructors()[1];

        final Question firstQuestion = (Question) firstConstructor.newInstance(TEST_WRITER, TEST_TITLE_1,TEST_CONTENT_1);
        final Question secondQuestion = (Question) secondConstructor.newInstance(
            2L, TEST_WRITER, TEST_TITLE_2,TEST_CONTENT_2, Date.from(Instant.now()), 3);

        assertThat(firstQuestion.getWriter()).isEqualTo(TEST_WRITER);
        assertThat(firstQuestion.getTitle()).isEqualTo("제목1");
        assertThat(firstQuestion.getContents()).isEqualTo("내용1");
        assertThat(secondQuestion.getWriter()).isEqualTo("gugu");
        assertThat(secondQuestion.getTitle()).isEqualTo("제목2");
        assertThat(secondQuestion.getContents()).isEqualTo("내용2");
    }

    @Test
    @DisplayName("접근제한자가 Public 인 필드를 조회한다")
    void givenClass_whenGetsPublicFields_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Field[] fields = questionClass.getFields();

        assertThat(fields).hasSize(0);
    }

    @Test
    @DisplayName("클래스 내부에 선언된 모든 필드를 조회한다")
    void givenClass_whenGetsDeclaredFields_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Field[] fields = questionClass.getDeclaredFields();

        assertThat(fields).hasSize(6);
        assertThat(fields[0].getName()).isEqualTo("questionId");
    }

    @Test
    @DisplayName("클래스 내부 필드의 이름으로 필드를 조회한다")
    void givenClass_whenGetsFieldsByName_thenCorrect() throws Exception {
        final Class<?> questionClass = Question.class;
        final Field field = questionClass.getDeclaredField("questionId");

        assertThat(field.getName()).isEqualTo("questionId");
    }

    @Test
    @DisplayName("클래스 필드의 타입을 조회한다")
    void givenClassField_whenGetsType_thenCorrect() throws Exception {
        final Field field = Question.class.getDeclaredField("questionId");
        final Class<?> fieldClass = field.getType();

        assertThat(fieldClass.getSimpleName()).isEqualTo("long");
    }

    @Test
    void givenClassField_whenSetsAndGetsValue_thenCorrect() throws Exception {
        final Class<?> studentClass = Student.class;
        final Student student = (Student) studentClass.getConstructor().newInstance();
        final Field field = student.getClass().getDeclaredField("age");

        // todo field에 접근 할 수 있도록 만든다.
        field.setAccessible(true);

        assertThat(field.getInt(student)).isZero();
        assertThat(student.getAge()).isZero();

        field.set(student, 99);

        assertThat(field.getInt(student)).isEqualTo(99);
        assertThat(student.getAge()).isEqualTo(99);
    }
}
