package com.nn.payments;

import static io.restassured.RestAssured.given;
import java.lang.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.json.simple.parser.JSONParser;

import com.nn.environment.CustomerCredentials;
import com.nn.environment.MerchantCredentials;
import com.nn.environment.PaymentCredentials;
import com.nn.json.Payment;
import com.nn.json.Refund;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DirectDebitSEPA {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// @Test(dataProvider = "iban_bic")
	public static void ibanBic(String iban, String bic) {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.iban_bic(iban, bic)).when().post("payment").then().assertThat().statusCode(200).extract()
				.response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("transaction has been created succefully");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

	// @Test(dataProvider = "no_nc")
	public static void noNC(String email, String mobile, String no_nc) {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.no_nc(email, mobile, no_nc)).when().post("payment").then().assertThat().statusCode(200)
				.extract().response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("transaction has been created succefully");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

	// @Test(dataProvider = "account_holder")
	public static void nameAccHolder(String first_name, String last_name, String account_holder) {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.name_acc_holder(first_name, last_name, account_holder)).when().post("payment").then()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("transaction has been created succefully");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

	// @Test(dataProvider = "amount_currency")
	public static void amountCurrenctToken(String amount, String currency, String createToken) {
		RestAssured.baseURI = MerchantCredentials.base_Url;
		String input = given().log().all().header("Content-Type", "application/json").header("Charset", "utf-8")
				.header("Accept", "application/json")
				.header("X-NN-Access-Key", "YTg3ZmY2NzlhMmYzZTcxZDkxODFhNjdiNzU0MjEyMmM=")
				.body(Payment.amount_Currency_token(amount, currency, createToken)).when().post("payment").then()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js = new JsonPath(input);
		if (js.getString("result.status").equals("SUCCESS") && js.getString("transaction.status").equals("CONFIRMED")) {
			System.out.println("transaction has been created succefully");
		} else if (js.getString("result.status").equals("FAILURE")) {
			System.out.println("transaction has been failed " + js.getString("result.status_code") + " - "
					+ js.getString("result.status_text"));
		}
	}

	/*-------------------------------------***********************------------------------------------------------------*/

	@DataProvider(name = "iban_bic")
	public Object[] getIbanBic() {
		return new Object[][] { { PaymentCredentials.de_iban, PaymentCredentials.de_bic },
				{ PaymentCredentials.at_iban, PaymentCredentials.at_bic },
				{ PaymentCredentials.ch_iban, PaymentCredentials.ch_bic },
				{ "DE123456790123654789123", PaymentCredentials.de_bic },
				{ "'~!@#$%^&*()_+/*-+./.,?><';:][|}{", PaymentCredentials.de_bic },
				{ "<script>alert(1)</script>", PaymentCredentials.de_bic }, { "", PaymentCredentials.de_bic },
				{ PaymentCredentials.de_iban, "DE123456790123654789123" },
				{ PaymentCredentials.de_iban, "'~!@#$%^&*()_+/*-+./.,?><';:][|}{" },
				{ PaymentCredentials.de_iban, "<script>alert(1)</script>" }, { PaymentCredentials.de_iban, "" },
				{ PaymentCredentials.de_iban, "13123456789" }, { "", "" } };
	}

	@DataProvider(name = "no_nc")
	public Object[] getNoNc() {
		return new Object[][] { { CustomerCredentials.email, CustomerCredentials.mobile_no, CustomerCredentials.no_nc },
				{ CustomerCredentials.email, CustomerCredentials.mobile_no, "0" },
				{ CustomerCredentials.email, "", CustomerCredentials.no_nc },
				{ "", CustomerCredentials.mobile_no, CustomerCredentials.no_nc }, { "", "", CustomerCredentials.no_nc },
				{ "", "", "" } };
	}

	@DataProvider(name = "account_holder")
	public Object[] getFnameLnameAccHolder() {
		return new Object[][] { { CustomerCredentials.first_name, CustomerCredentials.last_name, "" },
				{ CustomerCredentials.first_name, CustomerCredentials.last_name, CustomerCredentials.account_holder },
				{ CustomerCredentials.first_name, CustomerCredentials.last_name, "'~!@#$%^&*()_+/*-+./.,?><';:][|}{" },
				{ CustomerCredentials.first_name, CustomerCredentials.last_name, "<script>alert(1)</script>" } };
	}

	@DataProvider(name = "amount_currency")
	public Object[] getAmountCurrencyToken() {
		return new Object[][] { { "0", "EUR", "" }, { "0", "EUR", "1" }, { "1", "EUR", "" }, { "500000", "EUR", "" },
				{ "100", "CHF", "" }, { "100", "USD", "" } };
	}

}
