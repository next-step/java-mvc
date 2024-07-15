package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ReflectionTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void givenObject_whenGetsClassName_thenCorrect() {
        final Class<Question> clazz = Question.class;

        assertSoftly(softly -> {
            softly.assertThat(clazz.getSimpleName()).isEqualTo("Question");
            softly.assertThat(clazz.getName()).isEqualTo("reflection.Question");
            softly.assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question");
        });
    }

    @Test
    void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
        final Class<?> clazz = Class.forName("reflection.Question");

        assertSoftly(softly -> {
            softly.assertThat(clazz.getSimpleName()).isEqualTo("Question");
            softly.assertThat(clazz.getName()).isEqualTo("reflection.Question");
            softly.assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question");
        });
    }

    @Test
    void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        final Object student = new Student();
        final Field[] fields = student.getClass().getDeclaredFields();
        final List<String> actualFieldNames = Arrays.stream(fields).map(Field::getName).toList();

        assertThat(actualFieldNames).contains("name", "age");
    }

    @Test
    void givenClass_whenGetsMethods_thenCorrect() {
        final Class<Student> animalClass = Student.class;
        final Method[] methods = animalClass.getDeclaredMethods();
        final List<String> actualMethods = Arrays.stream(methods).map(Method::getName).toList();

        assertThat(actualMethods)
                .hasSize(3)
                .contains("getAge", "toString", "getName");
    }

    @Test
    void givenClass_whenGetsAllConstructors_thenCorrect() {
        final Class<Question> questionClass = Question.class;
        final Constructor<?>[] constructors = questionClass.getConstructors();

        assertThat(constructors).hasSize(2);
    }

    @Test
    void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws Exception {
        final Class<Question> questionClass = Question.class;

        final Constructor<Question> firstConstructor = questionClass.getDeclaredConstructor(String.class, String.class, String.class);
        final Constructor<Question> secondConstructor = questionClass.getDeclaredConstructor(long.class, String.class, String.class, String.class, Date.class, int.class);

        final Question firstQuestion = firstConstructor.newInstance("gugu", "제목1", "내용1");
        final Question secondQuestion = secondConstructor.newInstance(0,"gugu", "제목2", "내용2", new Date(), 0);

        assertSoftly(softly -> {
            softly.assertThat(firstQuestion.getWriter()).isEqualTo("gugu");
            softly.assertThat(firstQuestion.getTitle()).isEqualTo("제목1");
            softly.assertThat(firstQuestion.getContents()).isEqualTo("내용1");
            softly.assertThat(secondQuestion.getWriter()).isEqualTo("gugu");
            softly.assertThat(secondQuestion.getTitle()).isEqualTo("제목2");
            softly.assertThat(secondQuestion.getContents()).isEqualTo("내용2");
        });
    }

    @Test
    void givenClass_whenGetsPublicFields_thenCorrect() {
        final Class<Question> questionClass = Question.class;
        final Field[] fields = questionClass.getFields();

        assertThat(fields).hasSize(0);
    }

    @Test
    void givenClass_whenGetsDeclaredFields_thenCorrect() {
        final Class<Question> questionClass = Question.class;
        final Field[] fields = questionClass.getDeclaredFields();

        assertSoftly(softly -> {
            softly.assertThat(fields).hasSize(6);
            softly.assertThat(fields[0].getName()).isEqualTo("questionId");
        });
    }

    @Test
    void givenClass_whenGetsFieldsByName_thenCorrect() throws Exception {
        final Class<Question> questionClass = Question.class;
        final Field field = questionClass.getDeclaredField("questionId");

        assertThat(field.getName()).isEqualTo("questionId");
    }

    @Test
    void givenClassField_whenGetsType_thenCorrect() throws Exception {
        final Field field = Question.class.getDeclaredField("questionId");
        final Class<?> fieldClass = field.getType();

        assertThat(fieldClass.getSimpleName()).isEqualTo("long");
    }

    @Test
    void givenClassField_whenSetsAndGetsValue_thenCorrect() throws Exception {
        final Class<Student> studentClass = Student.class;
        final Student student = studentClass.getDeclaredConstructor().newInstance();
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
