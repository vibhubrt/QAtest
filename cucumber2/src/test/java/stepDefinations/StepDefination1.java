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

	
	RequestSpecification request;
	GetResponse responsebody;
	InvalidResponse invalidresponse;
	Response resp;
	JsonPath js;

	@Given("valid latitude {double} and longitude {double}")
	public void valid_latitude_and_longitude(Double latitude, Double longitude) {

		if(latitude == null || longitude ==null){
			throw new IllegalArgumentException("Argument was null!"); 
		}
		request = given().log().all().spec(requestSpecification(latitude, longitude));

	}

	@Given("valid latitude {double} and longitude {double} and validdate {string} and format {int}")
	public void valid_latitude_and_longitude_and_validdate_and_format(Double latitude, Double longitude, String date,
			int format) {
		request = given().log().all().spec(requestSpecification(latitude, longitude, date, format));
	}
	
	@Given("valid latitude {double} and longitude {double} and format {int}")
	public void valid_latitude_and_longitude_and_dateformat(Double latitude, Double longitude, int format) {

		request = given().log().all().spec(requestSpecification(latitude, longitude, format));

	}

	@Given("valid latitude {double} and longitude {double} and invaliddate {string}")
	public void valid_latitude_and_longitude_and_invaliddate(Double latitude, Double longitude, String date) {
		request = given().log().all().spec(requestSpecification(latitude, longitude, date));

	}
	@When("user calls API with get Http request")
	public void user_calls_api_with_get_http_request() {

		resp = request.when().get("/json").then().extract().response();
		if (resp.getStatusCode() == 400) {
			invalidresponse = request.expect().defaultParser(Parser.JSON).when().get("/json").as(InvalidResponse.class);
		}

		else if (resp.getStatusCode() == 200) {
			responsebody = request.expect().defaultParser(Parser.JSON).when().get("/json").as(GetResponse.class);
			System.out.println(responsebody.getResults().getDay_length());
		}

		
	}

	@Then("API call code is {string}")
	public void api_call_success_code_is(String code) {

		assertEquals(Integer.toString(resp.getStatusCode()), code);
		
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

		assertNotNull(responsebody.getResults().getSunrise());
		assertNotNull(responsebody.getResults().getSunset());

	}

	@Then("check default date is today")
	public void check_default_date_is_today() throws ParseException {

		
		
		String date =getDatefromResponse(responsebody.getResults().getSunrise());
		
		assertEquals(gettodayUTCDate(), date);

	}

	@Then("verify if data is unformatted")
	public void verify_if_data_is_unformatted() throws ParseException {
		
         
		String date = getDatefromResponse(responsebody.getResults().getSunset());
		assertTrue(responsebody.getResults().getSunrise().contains(date));

	}

	@Then("check sunrise and sunset of {string}")
	public void check_sunrise_and_sunset_of(String inputdate) throws ParseException {

		String sunrisedate = getDatefromResponse(responsebody.getResults().getSunset());
		String sunsetdate = getDatefromResponse(responsebody.getResults().getSunrise());
		assertEquals(sunrisedate,inputdate);
		assertEquals(sunsetdate,inputdate);
	}

	

	@Then("check time between sunrise and sunset is equal to daylength")
	public void check_time_between_sunrise_and_sunset_is_equal_to_daylength() {
		String daylength = getDaylength(responsebody.getResults().getSunrise(), responsebody.getResults().getSunset());

		assertEquals(daylength, responsebody.getResults().getDay_length());
	}

}
