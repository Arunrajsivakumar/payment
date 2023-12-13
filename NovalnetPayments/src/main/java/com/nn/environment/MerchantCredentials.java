package com.nn.environment;

import static io.restassured.RestAssured.given;

import java.util.Base64;

import io.restassured.specification.RequestSpecification;

public class MerchantCredentials {

	public static String base_Url = "https://payport.novalnet.de/v2";
	public static String signature = "7ibc7ob5|tuJEH3gNbeWJfIHah||nbobljbnmdli0poys|doU3HJVoym7MQ44qf7cpn7pc";
	public static int tariff_id = 10004;
	public static byte test_mode = 1;
	public static String payment_access_key = "a87ff679a2f3e71d9181a67b7542122c";
	public static String payment_endpoint = "payment";
	public static String authorize_endpoint = "authorize";
	public static String capture_endpoint = "transaction/capture";
	public static String cancel_endpoint = "transaction/cancel";
	public static String refund_endpoint = "transaction/refund";
	public static String update_endpoint = "transaction/update";

	public static RequestSpecification Header = given().log().all().header("Content-Type", "application/json")
			.header("Charset", "utf-8").header("Accept", "application/json")
			.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=");
	
//	public String encode() {
//		String originalkey = MerchantCredentials.payment_access_key;
//		String encodedKey = Base64.getEncoder().encodeToString(originalkey.getBytes());
//		// System.out.println("encode value " + encodedKey);
//		return encodedKey;
//
//	}
//
//	public static void main(String[] args) {
//
//		MerchantCredentials a = new MerchantCredentials();
//		String check = a.encode();
//		System.out.println(check);
//	}

}
