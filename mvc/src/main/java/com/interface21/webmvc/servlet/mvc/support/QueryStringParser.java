package com.interface21.webmvc.servlet.mvc.support;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryStringParser {

	private static final String QUERY_SEPARATOR = "&";
	private static final String KEY_VALUE_SEPARATOR = "=";

	private QueryStringParser() {
	}

	public static Map<String, String> parse(String queryString) {
		return Arrays.stream(splitQueryString(queryString))
				.map(QueryStringParser::splitKeyAndValue)
				.collect(Collectors.toUnmodifiableMap(QueryStringParser::getKey, QueryStringParser::getValue));
	}

	private static String[] splitQueryString(String queryString) {
		return queryString.split(QUERY_SEPARATOR);
	}

	private static String[] splitKeyAndValue(String query) {
		return query.split(KEY_VALUE_SEPARATOR);
	}

	private static String getKey(String[] keyValue) {
		return keyValue[0];
	}

	private static String getValue(String[] keyValue) {
		if (keyValue.length == 1) {
			return "";
		}
		return keyValue[1];
	}
}
