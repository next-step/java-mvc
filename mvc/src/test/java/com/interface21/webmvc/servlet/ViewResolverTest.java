package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JsonView;
import com.interface21.webmvc.servlet.view.JspView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class ViewResolverTest {

    @DisplayName("JspView를 반환한다.")
    @Test
    public void resolveJspView() {
        //given
        String viewName = "index.jsp";
        //when
        View view = ViewResolver.resolveViewName(viewName);
        //then
        assertThat(view).isInstanceOf(JspView.class);
    }

    @DisplayName("JsonView를 반환한다.")
    @Test
    public void resolveJsonView() {
        //when
        View view = ViewResolver.resolveJsonView();
        //then
        assertThat(view).isInstanceOf(JsonView.class);
    }

    @DisplayName("viewName이 null일 때 JspView를 반환한다.")
    @Test
    public void resolveJspViewWhenViewNameIsNull() {
        //given
        String viewName = null;
        //when
        View view = ViewResolver.resolveViewName(viewName);
        //then
        assertThat(view).isInstanceOf(JspView.class);
    }
}