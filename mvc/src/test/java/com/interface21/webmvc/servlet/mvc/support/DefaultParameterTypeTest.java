package com.interface21.webmvc.servlet.mvc.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DefaultParameterTypeTest {

  @Test
  @DisplayName("boolean, Boolean 타입과 value 를 인자로 boolean, boolean 타입으로 컨버팅한다")
  public void testConvertBoolean() {
    assertThat(DefaultParameterType.convert(boolean.class, "true")).isEqualTo(true);
    assertThat(DefaultParameterType.convert(boolean.class, "false")).isEqualTo(false);
    assertThat(DefaultParameterType.convert(Boolean.class, "true")).isEqualTo(true);
    assertThat(DefaultParameterType.convert(Boolean.class, "false")).isEqualTo(false);
  }

  @Test
  @DisplayName("byte, Byte 타입과 value 를 인자로 byte, Byte 타입으로 컨버팅한다")
  public void testConvertByte() {
    assertThat(DefaultParameterType.convert(byte.class, "123")).isEqualTo((byte) 123);
    assertThat(DefaultParameterType.convert(Byte.class, "123")).isEqualTo((byte) 123);
  }

  @Test
  @DisplayName("char, Character 타입과 value 를 인자로 char, Character 타입으로 컨버팅한다")
  public void testConvertChar() {
    assertThat(DefaultParameterType.convert(char.class, "a")).isEqualTo('a');
    assertThat(DefaultParameterType.convert(Character.class, "a")).isEqualTo('a');
  }

  @Test
  @DisplayName("Double, double 타입과 value 를 인자로 Double, double 타입으로 컨버팅한다")
  public void testConvertDouble() {
    assertThat(DefaultParameterType.convert(double.class, "123.45")).isEqualTo(123.45);
    assertThat(DefaultParameterType.convert(Double.class, "123.45")).isEqualTo(123.45);
  }

  @Test
  @DisplayName("float, Float 타입과 value 를 인자로 float, Float 타입으로 컨버팅한다")
  public void testConvertFloat() {
    assertThat(DefaultParameterType.convert(float.class, "123.45")).isEqualTo(123.45f);
    assertThat(DefaultParameterType.convert(Float.class, "123.45")).isEqualTo(123.45f);
  }

  @Test
  @DisplayName("int, Integer 타입과 value 를 인자로 int, Integer 타입으로 컨버팅한다")
  public void testConvertInt() {
    assertThat(DefaultParameterType.convert(int.class, "123")).isEqualTo(123);
    assertThat(DefaultParameterType.convert(Integer.class, "123")).isEqualTo(123);
  }

  @Test
  @DisplayName("long, Long 타입과 value 를 인자로 long, Long 타입으로 컨버팅한다")
  public void testConvertLong() {
    assertThat(DefaultParameterType.convert(long.class, "123")).isEqualTo(123L);
    assertThat(DefaultParameterType.convert(Long.class, "123")).isEqualTo(123L);
  }

  @Test
  @DisplayName("short, Short 타입과 value 를 인자로 short, Short 타입으로 컨버팅한다")
  public void testConvertShort() {
    assertThat(DefaultParameterType.convert(short.class, "123")).isEqualTo((short) 123);
    assertThat(DefaultParameterType.convert(Short.class, "123")).isEqualTo((short) 123);
  }

  @Test
  @DisplayName("String 타입과 value 를 인자로 String 타입으로 컨버팅한다")
  public void testConvertString() {
    assertThat(DefaultParameterType.convert(String.class, "hello")).isEqualTo("hello");
  }

  @Test
  @DisplayName("value 가 null 이면 null 로 리턴한다")
  public void testConvertNullForWrapperTypes() {
    assertThat(DefaultParameterType.convert(Boolean.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Byte.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Character.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Double.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Float.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Integer.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Long.class, null)).isNull();
    assertThat(DefaultParameterType.convert(Short.class, null)).isNull();
    assertThat(DefaultParameterType.convert(String.class, null)).isNull();
  }
}