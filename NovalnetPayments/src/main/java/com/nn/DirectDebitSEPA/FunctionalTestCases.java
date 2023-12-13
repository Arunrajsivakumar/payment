package com.nn.DirectDebitSEPA;

import static io.restassured.RestAssured.given;
import java.lang.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.nn.environment.CustomerCredentials;
import com.nn.environment.MerchantCredentials;
import com.nn.environment.PaymentCredentials;
import com.nn.json.Capture;
import com.nn.json.Payment;
import com.nn.json.Refund;
import com.nn.payments.Common;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class FunctionalTestCases {

//	public static RequestSpecification Header = given().log().all().header("Content-Type", "application/json")
//			.header("Charset", "utf-8").header("Accept", "application/json")
//			.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=");

	// TC_ID:1 - To verify the API is up and running.
	@Test
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

	// TC_ID:24 - Intiate a normal txn and execute full refund
	public void prosessFullRefund(String amount, String tid) {

		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Refund.intiateRefund(amount, tid)).when().post(MerchantCredentials.refund_endpoint).then().log()
				.all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS")
				&& js.getString("transaction.status").equals("DEACTIVATED")) {
			System.out.println("Refund has been executed successfully ");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}

	}

	// TC_ID:25 -Intiate a normal txn and execute partial with full refund
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
				System.out.println("*****************" + i);
				prosessFullRefund(amount, tid);
			}
		} else {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

	// TC_ID:26 - Intiate a authorized txn and execute capture
	// TC_ID:27 - Intiate a authorized txn and execute cancel
	@Test
	public void transactionCaptureCancel() {
		// SessionFilter session = new SessionFilter();
		for (int i = 0; i < 2; i++) {
			if (i == 0) {

				RestAssured.baseURI = MerchantCredentials.base_Url;
				String request = given().log().all().header("Content-Type", "application/json")
						.header("Charset", "utf-8").header("Accept", "application/json")
						.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
						.body(Payment.transactionIntaition()).when().post(MerchantCredentials.authorize_endpoint).then()
						.assertThat().statusCode(200).extract().response().asString();
				JsonPath jsonresp = Common.rawToJson(request);
				String tid = jsonresp.getString("transaction.tid");
				if (jsonresp.getString("result.status").equals("SUCCESS")
						&& jsonresp.getString("transaction.status").equals("ON_HOLD")) {
					System.out.println("######### Transaction has been authorized #########");
					RestAssured.baseURI = MerchantCredentials.base_Url;
					String capturerequest = given().log().all().header("Content-Type", "application/json")
							.header("Charset", "utf-8").header("Accept", "application/json")
							.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
							.body(Capture.intiateCaptureCancel(tid)).when().post(MerchantCredentials.capture_endpoint)
							.then().assertThat().statusCode(200).extract().response().asString();
					JsonPath jsonresp1 = Common.rawToJson(capturerequest);
					if (jsonresp1.getString("result.status").equals("SUCCESS")
							&& jsonresp1.getString("transaction.status").equals("CONFIRMED")) {
						System.out.println("######### Transaction has been confirmed	#########");
					}
				} else if (jsonresp.getString("result.status").equals("FAILURE")
						&& jsonresp.getString("transaction.status").equals("ON_HOLD")) {
					System.out.println("Transaction has been not been authorized");
				}

			} else if (i == 1) {

				RestAssured.baseURI = MerchantCredentials.base_Url;
				String request = given().log().all().header("Content-Type", "application/json")
						.header("Charset", "utf-8").header("Accept", "application/json")
						.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
						.body(Payment.transactionIntaition()).when().post(MerchantCredentials.authorize_endpoint).then()
						.assertThat().statusCode(200).extract().response().asString();
				JsonPath jsonresp = Common.rawToJson(request);
				String tid = jsonresp.getString("transaction.tid");
				if (jsonresp.getString("result.status").equals("SUCCESS")
						&& jsonresp.getString("transaction.status").equals("ON_HOLD")) {
					System.out.println("######### Transaction has been authorized #########");
					RestAssured.baseURI = MerchantCredentials.base_Url;
					String capturerequest = given().log().all().header("Content-Type", "application/json")
							.header("Charset", "utf-8").header("Accept", "application/json")
							.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
							.body(Capture.intiateCaptureCancel(tid)).when().post(MerchantCredentials.cancel_endpoint)
							.then().assertThat().statusCode(200).extract().response().asString();
					JsonPath jsonresp1 = Common.rawToJson(capturerequest);
					if (jsonresp1.getString("result.status").equals("SUCCESS")
							&& jsonresp1.getString("transaction.status").equals("DEACTIVATED")) {
						System.out.println("######### Transaction has been canceled #########");
					}
				} else if (jsonresp.getString("result.status").equals("FAILURE")) {
					System.out.println("######### Transaction has been not been authorized #########");
				}

			}

		}
	}

}
