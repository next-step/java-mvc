package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectTypeArgumentResolverTest {

    @Test
    @DisplayName("ObjectTypeArgumentResolver 는 fallback 로 사용되어 아무것도 support 하지 않는다")
    void notSupportForAnything() {
        final ObjectTypeArgumentResolver resolver = new ObjectTypeArgumentResolver();
        final MethodParameter parameter = new FakeMethodParameter(Person.class, "person");

        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    @DisplayName("맞는 타입으로 객체를 생성해서 반환한다.")
    void resolveArgumentForObjectTest() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "jongmin");
        request.setParameter("age", "4943");

        final ObjectTypeArgumentResolver resolver = new ObjectTypeArgumentResolver();
        final MethodParameter parameter = new FakeMethodParameter(Person.class, "person");

        final Object result = resolver.resolveArgument(parameter, request, null);

        assertThat(result).isEqualTo(new Person("jongmin", 4943));
    }

    @Test
    @DisplayName("record 도 반환할 수 있다.")
    void resolveArgumentForRecordTest() {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name", "jongmin");
        request.setParameter("age", "4943");

        final ObjectTypeArgumentResolver resolver = new ObjectTypeArgumentResolver();
        final MethodParameter parameter = new FakeMethodParameter(PersonRecord.class, "person");

        final Object result = resolver.resolveArgument(parameter, request, null);

        assertThat(result).isEqualTo(new PersonRecord("jongmin", 4943));
    }

    static class Person {
        private String name;
        private int age;

        public Person() {
        }

        public Person(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }

    record PersonRecord(String name, int age) {
        public PersonRecord {
        }

        public PersonRecord(final String name) {
            this(name, 0);
        }

        public PersonRecord(final int age) {
            this("name", age);
        }
    }

}
