package reflection;

import java.lang.reflect.Modifier;
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
import reflection.ReflectionTest.O1.I;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    /**
     * - simpleName : 선언된 클래스 이름 그 자체. 패키지 이름이 포함되지 않아 고유성이 보장되지 않음.
     * - name : ClassLoader를 사용하여 Class.forName 을 호출해 클래스를 동적으로 로드하는 데 사용되는 이름
     *   - 내부 클래스, 내부 정적 클래스 등은 $를 이용해 표시됨
     * - canonicalName : import 구문에 사용되는 이름 (패키지 이름 + 클래스 이름)
     *   - 내부 클래스, 내부 정적 클래스를 구분하지 않고 . 으로 연결됨
     */
    @Test
    void givenObject_whenGetsClassName_thenCorrect() {
        final Class<Question> clazz = Question.class;

        assertThat(clazz.getSimpleName()).isEqualTo("Question");
        assertThat(clazz.getName()).isEqualTo("reflection.Question");
        assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question");
    }

    static class O1 { class I {} }

    @DisplayName("getName()과 getCanonicalName()은 내부 클래스 표기방법이 다르다.")
    @Test
    void test() {
        String name = I.class.getName();
        String canonicalName = I.class.getCanonicalName();

        assertThat(name).isEqualTo("reflection.ReflectionTest$O1$I");
        assertThat(canonicalName).isEqualTo("reflection.ReflectionTest.O1.I");
    }

    @Test
    void givenClassName_whenCreatesObject_thenCorrect() throws ClassNotFoundException {
        final Class<?> clazz = Class.forName("reflection.Question");

        assertThat(clazz.getSimpleName()).isEqualTo("Question");
        assertThat(clazz.getName()).isEqualTo("reflection.Question");
        assertThat(clazz.getCanonicalName()).isEqualTo("reflection.Question");
    }

    /**
     * - getDeclaredFields()는 public, protected, default(package) access 및 private 필드가 포함되지만 상속된 필드는 제외하고 가져온다
     * - getFields()는 public을 가져온다
     */
    @Test
    void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() {
        final Object student = new Student();
        final Field[] fields = student.getClass().getDeclaredFields();
        final List<String> actualFieldNames = Arrays.stream(fields)
                .map(Field::getName)
                .toList();

        assertThat(actualFieldNames).contains("name", "age");
    }

    @DisplayName("상속한 경우 테스트 - getFields()는 상속받은 public 필드를 배열에 담는다.")
    @Test
    void test2() {
        class Student2 extends Student {
            public int publicField;
            private int privateField;
        }
        Class<Student2> student2Class = Student2.class;

        List<String> student2Fields = Arrays.stream(student2Class.getFields())
                .map(Field::getName)
                .toList();

        List<String> student2DeclaredFields = Arrays.stream(student2Class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        assertThat(student2Fields).containsOnly("publicField", "publicSuperClassField");

        // this$0 은 외부에 대한 참조다.(Student2는 내부클래스에 해당하여 외부에 대한 참조를 유지한다)
        assertThat(student2DeclaredFields).containsOnly("publicField", "privateField", "this$0");
    }

    /**
     * - getMethods()는 상속 받은 모든 public 메서드를 반환한다
     * - getDeclaredMethods()는 상속 받은 모든 메서드는 제외하고, 클래스 자신의 메서드를 접근 제어자와 관계 없이 반환한다
     */
    @Test
    void givenClass_whenGetsMethods_thenCorrect() {
        final Class<?> animalClass = Student.class;
        final Method[] methods = animalClass.getDeclaredMethods();
        final List<String> actualMethods = Arrays.stream(methods)
                .map(Method::getName)
                .toList();

        assertThat(actualMethods)
                .hasSize(3)
                .contains("getAge", "toString", "getName");
    }

    @Test
    void givenClass_whenGetsAllConstructors_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Constructor<?>[] constructors = questionClass.getDeclaredConstructors();

        assertThat(constructors).hasSize(2);
    }

    @Test
    void givenClass_whenInstantiatesObjectsAtRuntime_thenCorrect() throws Exception {
        final Class<?> questionClass = Question.class;

        final Constructor<?> firstConstructor = questionClass.getDeclaredConstructors()[0];
        final Constructor<?> secondConstructor = questionClass.getDeclaredConstructors()[1];

        final Question firstQuestion = (Question) firstConstructor.newInstance("gugu", "제목1", "내용1");
        final Question secondQuestion = (Question) secondConstructor.newInstance(1L, "gugu", "제목2", "내용2", new Date(), 10);

        assertThat(firstQuestion.getWriter()).isEqualTo("gugu");
        assertThat(firstQuestion.getTitle()).isEqualTo("제목1");
        assertThat(firstQuestion.getContents()).isEqualTo("내용1");
        assertThat(secondQuestion.getWriter()).isEqualTo("gugu");
        assertThat(secondQuestion.getTitle()).isEqualTo("제목2");
        assertThat(secondQuestion.getContents()).isEqualTo("내용2");
    }

    @Test
    void givenClass_whenGetsPublicFields_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Field[] fields = questionClass.getFields();

        assertThat(fields).hasSize(0);
    }

    @Test
    void givenClass_whenGetsDeclaredFields_thenCorrect() {
        final Class<?> questionClass = Question.class;
        final Field[] fields = questionClass.getDeclaredFields();

        assertThat(fields).hasSize(6);
        assertThat(fields[0].getName()).isEqualTo("questionId");
    }

    @Test
    void givenClass_whenGetsFieldsByName_thenCorrect() throws Exception {
        final Class<?> questionClass = Question.class;
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
        final Class<?> studentClass = Student.class;
        final Student student = (Student) studentClass.getConstructors()[0].newInstance();
        final Field ageField = studentClass.getDeclaredField("age");

        ageField.setAccessible(true);
        assertThat(ageField.getInt(student)).isZero();
        assertThat(student.getAge()).isZero();

        ageField.set(student, 99);

        assertThat(ageField.getInt(student)).isEqualTo(99);
        assertThat(student.getAge()).isEqualTo(99);
    }
}
