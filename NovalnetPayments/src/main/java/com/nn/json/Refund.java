package com.nn.json;

import static io.restassured.RestAssured.given;

import com.nn.environment.MerchantCredentials;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Refund {
	
	public static String intiateRefund(String amount, String tid) {
		String request = "{\n"
				+ "   \"transaction\":{\n"
				+ "      \"tid\":\""+tid+"\",\n"
				+ "      \"amount\":\""+amount+"\",\n"
				+ "      \"reason\":\"Product not satisfied\"\n"
				+ "   },\n"
				+ "   \"custom\":{\n"
				+ "      \"lang\":\"EN\"\n"
				+ "   }\n"
				+ "}";
		return request;
	}
	
	
}
