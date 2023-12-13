package com.nn.payments;

import io.restassured.path.json.JsonPath;

public class Common {
	public static JsonPath rawToJson(String response)
	{
		JsonPath js =new JsonPath(response);
		return js;
	}
}
