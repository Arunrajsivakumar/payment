package com.nn.DirectDebitSEPA;

import static io.restassured.RestAssured.given;
import java.lang.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nn.environment.CustomerCredentials;
import com.nn.environment.MerchantCredentials;
import com.nn.environment.PaymentCredentials;
import com.nn.json.Payment;
import com.nn.json.Refund;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class FunctionalTestCases {

	public void transaction() {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.transactionIntaition()).when().post("payment").then().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = new JsonPath(input);
		String amount = js.getString("transaction.amount");
		String tid = js.getString("transaction.tid");
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("Transaction has been success");
			prosessFullRefund(amount, tid);
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}

	}

	public void prosessFullRefund(String amount, String tid) {

		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Refund.intiateRefund(amount, tid)).when().post("transaction/refund").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS")
				&& js.getString("transaction.status").equals("DEACTIVATED")) {
			System.out.println("Refund has been executed successfully ");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}

	}

	@Test
	public void prosessPartialFullRefund() {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.transactionIntaition()).when().post("payment").then().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = new JsonPath(input);
		String transactionAmount = js.getString("transaction.amount");
		int intAmount = Integer.parseInt(transactionAmount) / 2;
		String amount = String.valueOf(intAmount);
		String tid = js.getString("transaction.tid");
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("Transaction has been success");
			for (int i = 0; i < 2; i++) {
				System.out.println(i);
				prosessFullRefund(amount, tid);
			}
		} else {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

}
