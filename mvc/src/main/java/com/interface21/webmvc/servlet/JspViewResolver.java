package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JspView;

public class JspViewResolver implements ViewResolver {
	@Override
	public View resolveViewName(final String viewName) {
		if (viewName == null) {
			return new JspView("redirect:/404.jsp");
		}

		if (viewName.endsWith(".jsp")) {
			return new JspView(viewName);
		}
		return new JspView("redirect:/404.jsp");
	}
}
