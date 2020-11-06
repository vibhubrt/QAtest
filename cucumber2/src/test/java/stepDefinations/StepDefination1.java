package stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

import java.text.ParseException;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import pojo.GetResponse;

import pojo.InvalidResponse;
import resources.Utils;

import static io.restassured.RestAssured.*;

public class StepDefination1 extends Utils {

	RequestSpecification req;
	RequestSpecification res;
	GetResponse ResponseBody;
	InvalidResponse invalidresponse;
	Response resp;
	JsonPath js;

	@Given("valid latitude {double} and longitude {double}")
	public void valid_latitude_and_longitude(Double latitude, Double longitude) {

		if(latitude == null || longitude ==null){
			throw new IllegalArgumentException("Argument was null!"); 
		}
		res = given().log().all().spec(requestSpecification(latitude, longitude));

	}

	@Given("valid latitude {double} and longitude {double} and validdate {string} and format {int}")
	public void valid_latitude_and_longitude_and_validdate_and_format(Double latitude, Double longitude, String date,
			int format) {
		res = given().log().all().spec(requestSpecification(latitude, longitude, date, format));
	}
	
	@Given("valid latitude {double} and longitude {double} and format {int}")
	public void valid_latitude_and_longitude_and_dateformat(Double latitude, Double longitude, int format) {

		res = given().log().all().spec(requestSpecification(latitude, longitude, format));

	}

	@Given("valid latitude {double} and longitude {double} and invaliddate {string}")
	public void valid_latitude_and_longitude_and_invaliddate(Double latitude, Double longitude, String date) {
		res = given().log().all().spec(requestSpecification(latitude, longitude, date));

	}
	@When("user calls API with get Http request")
	public void user_calls_api_with_get_http_request() {

		resp = res.when().get("/json").then().extract().response();
		if (resp.getStatusCode() == 400) {
			invalidresponse = res.expect().defaultParser(Parser.JSON).when().get("/json").as(InvalidResponse.class);
		}

		else if (resp.getStatusCode() == 200) {
			ResponseBody = res.expect().defaultParser(Parser.JSON).when().get("/json").as(GetResponse.class);
			System.out.println(ResponseBody.getResults().getDay_length());
		}

		// System.out.println("Iam from POJO"
		// +getResponseBody.getResults().getSunrise());
	}

	@Then("API call code is {string}")
	public void api_call_success_code_is(String code) {

		assertEquals(Integer.toString(resp.getStatusCode()), code);
		// assertEquals(resp.getStatusCode(),200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedvalue) {

		String response = resp.asString();
		System.out.println(response);
		js = new JsonPath(response);
		assertEquals(js.get(key).toString(), expectedvalue);

	}

	

	@Then("verify sunrise and sunset times")
	public void verify_sunrise_and_sunset_times() {

		assertNotNull(ResponseBody.getResults().getSunrise());
		assertNotNull(ResponseBody.getResults().getSunset());

	}

	@Then("check default date is today")
	public void check_default_date_is_today() throws ParseException {

		
		
		String date =getDatefromResponse(ResponseBody.getResults().getSunrise());
		
		assertEquals(gettodayUTCDate(), date);

	}

	@Then("verify if data is unformatted")
	public void verify_if_data_is_unformatted() throws ParseException {
		
         
		String date = getDatefromResponse(ResponseBody.getResults().getSunset());
		assertTrue(ResponseBody.getResults().getSunrise().contains(date));

	}

	@Then("verify sunrise and sunset of given {string}")
	public void verify_sunrise_and_sunset_of_given(String inputdate) throws ParseException {

		String sunrisedate = getDatefromResponse(ResponseBody.getResults().getSunset());
		String sunsetdate = getDatefromResponse(ResponseBody.getResults().getSunrise());
		assertEquals(sunrisedate,inputdate);
		assertEquals(sunsetdate,inputdate);
	}

	

	@Then("check time between sunrise and sunset is equal to daylength")
	public void check_time_between_sunrise_and_sunset_is_equal_to_daylength() {
		String daylength = getDaylength(ResponseBody.getResults().getSunrise(), ResponseBody.getResults().getSunset());

		assertEquals(daylength, ResponseBody.getResults().getDay_length());
	}

}
