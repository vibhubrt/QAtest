Feature: Validating Sunset and sunrise times API

Scenario Outline: Verify successful response from API including sunrise and sunset times
        
        Given valid latitude <latitude> and longitude <longitude>
        When user calls API with get Http request
        Then API call code is "200"
        And "status" in response body is "OK"
        And verify sunrise and sunset times 
        

Examples: 
    |latitude |longitude|
    |10         |20.6     |  

Scenario Outline: Verify default date in response is today
        
        Given valid latitude <latitude> and longitude <longitude> and format <format>
        When user calls API with get Http request
        Then API call code is "200"
        And "status" in response body is "OK"
        And check default date is today 
        

Examples: 
    |latitude |longitude|format|
    |30.5     |20.6     |0     |     
 
Scenario Outline: Verify unformatted response when requested      

        Given valid latitude <latitude> and longitude <longitude> and format <format>
        When user calls API with get Http request
        Then API call code is "200"
        And "status" in response body is "OK"
        And verify if data is unformatted
        
        
Examples: 
    |latitude |longitude|format |
    |20.5     |20.6     |0      |
    

Scenario Outline: Verify invalid response when provided invalid date       

        Given valid latitude <latitude> and longitude <longitude> and invaliddate "<date>"
        When user calls API with get Http request 
        Then API call code is "400"
        And "status" in response body is "INVALID_DATE"
        
        
Examples: 
    |latitude |longitude|date |
    |30.5     |20.6     |@    |
    |20.5     |20.6     |$    |
    

Scenario Outline: Verify sunrise and sunset of specified date    

        Given valid latitude <latitude> and longitude <longitude> and validdate "<date>" and format <format>
        When user calls API with get Http request 
        Then API call code is "200"
        And "status" in response body is "OK"
        And check sunrise and sunset of "<date>"
        
        
Examples: 
    |latitude |longitude|date |format|
    |30.5     |20.6     |2020-11-05|0| 
    
Scenario Outline: Verify time between sunrise and sunset is same as day length      

        Given valid latitude <latitude> and longitude <longitude> and validdate "<date>" and format <format>
        When user calls API with get Http request 
        Then API call code is "200"
        And "status" in response body is "OK"
        And check time between sunrise and sunset is equal to daylength  
        
        
Examples: 
    |latitude |longitude|date |format|
    |30.5     |20.6     |2020-11-05|1 |
   
   