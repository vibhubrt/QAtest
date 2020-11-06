package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResponse {

	
	private Results results;
	private String status;
	
	private GetResponse(){
		
		
	}
	
	public GetResponse(Results results, String status) {
		this();
		this.results = results;
		this.status = status;
	}
	
	
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
