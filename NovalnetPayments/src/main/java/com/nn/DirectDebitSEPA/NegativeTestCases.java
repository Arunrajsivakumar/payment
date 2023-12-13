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

public class NegativeTestCases {
	
	// TC_ID:1 To verify the API is up and running.(DUPLICATE)
	// TC_ID:8 Intiate a txn without iban
	// TC_ID:9 Intiate a txn without bic
	// TC_ID:10 Intiate a txn with invalid iban
	// TC_ID:11 Intiate a txn with invalid iban
	// TC_ID:12 Intiate a txn with invalid iban
	// TC_ID:13 Intiate a txn with invalid bic
	// TC_ID:14 Intiate a txn with invalid bic
	// TC_ID:17 Intiate a txn with invalid bic
	// TC_ID:28 Intiate a normal txn with all the common parametres (Use AUSTRIA payment details)
	// TC_ID:29 Intiate a normal txn with all the common parametres (Use SWIZERLAND payment details)
	// TC_ID:30 Intiate a txn with invalid bic
	// TC_ID:31 Intiate a txn without bic and iban

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
	
	// TC_ID:4 Intiate a txn with no_nc (Remove all customer paramteres except email and mobile_no)
	// TC_ID:5 Intiate a txn with no_nc (Remove all customer paramteres except email and mobile_no)
	// TC_ID:6 Intiate a txn with no_nc (Remove all customer paramteres except email)
	// TC_ID:7 Intiate a txn with no_nc (Remove all customer paramteres except mobile_no)
	// TC_ID:32 Without any customer details and no_nc 

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
	// TC_ID:2 Intiate a txn without account_holder
	// TC_ID:3 Intiate a txn with different first_name, last_name and  account_holder
	// TC_ID:15 Intiate a txn with invalid account_holder
	// TC_ID:16 Intiate a txn with invalid account_holder
	
	
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

	// TC_ID:18 Intiate a txn with amount: 0 (Without create_token: 1)
	// TC_ID:19 Intiate a txn with amount: 0 (With create_token: 1)
	// TC_ID:20 Intiate a txn with amount: 1 500000
	// TC_ID:21 Intiate a txn with amount: 500000
	// TC_ID:22 Intiate a txn with currency: USD
	// TC_ID:23 Intiate a txn with currency: CHF
	
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
