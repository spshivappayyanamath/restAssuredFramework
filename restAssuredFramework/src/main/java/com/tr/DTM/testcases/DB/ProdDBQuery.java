package com.tr.DTM.testcases.DB;

import com.tr.Base.BaseClass;
import com.tr.Utils.Utility;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.Hashtable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ProdDBQuery extends BaseClass{
	
	@Test(dataProvider="ExcelData",groups={"Smoke"})
	public void getProdDBQuery(String tcname,Hashtable<String,String> excel) throws Exception{
			if(excel.get("RunMode").equalsIgnoreCase("N"))
			{
				throw new SkipException("Skipping the test case as run mode N");
			}
			Utility.log("---------------Test case Name : "+tcname +"-----------------");
		
		System.out.println(excel.get("Query"));
		RestAssured.useRelaxedHTTPSValidation();
		Response res =RestAssured.get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
		System.out.println(res.getStatusCode());
		System.out.println(res.getContentType());
		System.out.println(res.asString());
		System.out.println(res.detailedCookies());
		
		
//		RestAssured.baseURI = "https://maps.googleapis.com";
//		Response res2 = given().param ("query", "restaurants in mumbai").param ("key", "Xyz").when().get ("/maps/api/place/textsearch/json");
	
		
		
		RestAssured.defaultParser = Parser.JSON;
		Response res1 =RestAssured.get("https://api.apigarden.int.thomsonreuters.com/sdworkflow/v1/tasks?Assignee=0118633&EndTime=9999-12-31&TaskStatus=%27Assigned%27&RowsPerPage=50&PageNumber=1&X-TR-API-APP-ID=haZt28ACf6O3P4tcp8lGqGHAZiAHGQox");
/*		.given()
		.headers("Content-Type", "text/html;charset=utf-8")
		.when()
		.get("https://api.apigarden.int.thomsonreuters.com/sdworkflow/v1/tasks?Assignee=0118633&EndTime=9999-12-31&TaskStatus=%27Assigned%27&RowsPerPage=50&PageNumber=1&X-TR-API-APP-ID=haZt28ACf6O3P4tcp8lGqGHAZiAHGQox")
		.then()
		.contentType(ContentType.JSON)
		.extract().response();
*/		
		System.out.println(res1.contentType());
		System.out.println(res1.getHeader("content-type"));

		System.out.println("Test NG working Fine");
		
	}


	@BeforeMethod(groups={"Smoke"})
	public void isTcSkip()
	{	
		if(!exl.isExecutable(this.getClass().getSimpleName())){
			throw new SkipException("skipping the Scenario as Run Mode : N");
		}
	}
	
	@DataProvider(name="ExcelData" )
	public Object[][] getExcelData()
	{
		return exl.getData(this.getClass().getSimpleName(),this.getClass().getPackage().getName());
	}
	

}
