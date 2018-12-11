package test;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class Test1 {
	public static void main(String a[]) {
		
		RestAssured.useRelaxedHTTPSValidation();
//		RestAssured.set
//		Response res1 =RestAssured.get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
//		Response res2 =RestAssured.get("http://10.184.14.68/CT_CONTIBMAP_API/api/ContributionSummary");
//		System.out.println(res2.asString());
//		System.out.println(res2.getBody().toString());
	
//	XML	
//		RestAssured.baseURI ="http://restapi.demoqa.com/utilities/weatherxml/city/hyderabad";
//JSON
//		RestAssured.baseURI ="http://restapi.demoqa.com/utilities/weatherfull/city/hyderabad";
//CT
//		RestAssured.baseURI ="http://10.184.14.68/CT_CONTIBMAP_API/api/ContributionSummary";
		RestAssured.baseURI ="http://10.184.14.67:8080/CT_CONTRIBUTORMAP_API/api/ContributionSummary";


		RequestSpecification rs =RestAssured.given();
		Response response = rs.get("");
		ResponseBody body = response.getBody();
//		System.out.println(body.asString());
		
		JsonPath jsonPathEvaluator =response.jsonPath();
		
		System.out.println(jsonPathEvaluator.getString("ActiveContributors"));
		System.out.println(jsonPathEvaluator.getString("ContributedDataYTD"));
		
		System.out.println(jsonPathEvaluator.getString("ContributionsByCountry.Country"));
		System.out.println(jsonPathEvaluator.getString("ContributionsByCountry.MapCode"));
		System.out.println(jsonPathEvaluator.getString("ContributionsByCountry.NoOfContributions"));
		System.out.println(jsonPathEvaluator.getString("ContributionsByCountry.HeatMapID"));
		
		System.out.println(jsonPathEvaluator.getString("ContributorsByMonth.Month"));
		System.out.println(jsonPathEvaluator.getString("ContributorsByMonth.NoOfNewContributors"));
		System.out.println(jsonPathEvaluator.getString("NewContributors"));
		
//		List list = new ArrayList<>();
//		list.add(jsonPathEvaluator.getString("Country"));
//		System.out.println(list);
		
		
//		XmlPath xmlPathEvaluator = response.xmlPath();
//		System.out.println(xmlPathEvaluator.getString("Weather.WindDirectiondegree"));
		
		
//		ConnectionConfig conf = new ConnectionConfig();
//		conf.
//		Config.
	}

}
