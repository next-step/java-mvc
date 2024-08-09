package com.interface21.webmvc.servlet.mvc;

import com.interface21.webmvc.servlet.mvc.view.JsonView;

public class JsonResolver {
	public static JsonView resolveJsonView() {
		return new JsonView();
	}
}
