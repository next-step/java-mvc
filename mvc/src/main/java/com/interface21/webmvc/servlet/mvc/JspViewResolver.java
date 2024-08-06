package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.mvc.view.JspView;

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
