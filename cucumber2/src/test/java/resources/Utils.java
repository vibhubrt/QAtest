package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Utils {
	RequestSpecification req;
	
	public RequestSpecification requestSpecification(Double latitude, Double longitude){
		
		
		req = new RequestSpecBuilder().setBaseUri("https://api.sunrise-sunset.org").addQueryParam("lat", latitude).addQueryParam("lng", longitude).build();
		return req;
	}
	
    public RequestSpecification requestSpecification(Double latitude, Double longitude,int format){
		
		
		req = new RequestSpecBuilder().setBaseUri("https://api.sunrise-sunset.org").addQueryParam("lat", latitude).addQueryParam("lng", longitude).addQueryParam("formatted",format).build();
		return req;
	}
	
public RequestSpecification requestSpecification(Double latitude, Double longitude,String date){
		
		
		req = new RequestSpecBuilder().setBaseUri("https://api.sunrise-sunset.org").addQueryParam("lat", latitude).addQueryParam("lng", longitude).addQueryParam("date",date).build();
		return req;
	}

public RequestSpecification requestSpecification(Double latitude, Double longitude,String date,int format ){
	
	
	req = new RequestSpecBuilder().setBaseUri("https://api.sunrise-sunset.org").addQueryParam("lat", latitude).addQueryParam("lng", longitude).addQueryParam("date",date).addQueryParam("formatted",format).build();
	return req;
}




public String getDaylength(String sunrise, String sunset){
	
	StringBuilder sb = new StringBuilder();
	String starttime = sunrise;
	String endtime   = sunset; 
	SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
    
    Date d1 = null;
    Date d2 = null;

    try {
        d1 = format.parse(starttime);
        d2 = format.parse(endtime);

        // in milliseconds
        long diff = Math.abs(d2.getTime() - d1.getTime());
        long diffHours = diff / (60 * 60 * 1000) % 24; 
        long diffSeconds = (diff / 1000) % 60;
        long diffMinutes = (diff / (60 * 1000) % 60);
        
        
        sb.append(diffHours);
        sb.append(":");
        sb.append(diffMinutes);
        sb.append(":");
        sb.append(diffSeconds);
        
        //System.out.println(sb);
        //System.out.print(diffMinutes + " minutes and " + diffSeconds + " seconds." + diffHours);
    } catch (Exception e) {
        System.out.println("Invalid fromat");
    }
	
	
	
	
	
	return sb.toString();
	
}



public String gettodayUTCDate(){
	
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	format.setTimeZone(TimeZone.getTimeZone("UTC"));
	System.out.println(format.format(new Date()));
	String today = format.format(new Date());
	return today;
}


public String getDatefromResponse(String responseDate) throws ParseException{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	String fechaStr = responseDate;
	Date fechaNueva = format.parse(fechaStr);
	System.out.println(format.format(fechaNueva));

	String defaultdate = format.format(fechaNueva);
	
	
	
	return defaultdate;
}
	
}
