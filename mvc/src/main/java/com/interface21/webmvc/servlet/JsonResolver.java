package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JsonView;

public class JsonResolver {
	public static JsonView resolveJsonView() {
		return new JsonView();
	}
}
