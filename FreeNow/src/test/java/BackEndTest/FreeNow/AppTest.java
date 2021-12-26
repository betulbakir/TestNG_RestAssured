package BackEndTest.FreeNow;




import io.restassured.http.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.RestAssured;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.junit.Assert.*;
import org.testng.Reporter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.util.jar.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.*;    
import java.util.*;  



public class AppTest  {


	public String basePath;

	@BeforeClass
	public void setUp() {
		basePath = "http://jsonplaceholder.typicode.com";
		Reporter.log("Set Up", true);

		
	}


    @Test
    public void readUser() {
        Reporter.log("Read User",true);

        Response response = RestAssured.given().contentType(ContentType.JSON).given().
        when().
                get(basePath+"/users?username=Samantha");
      
        
		
			
     // TODO: If no result is found, 0
        // if array size is 1, continue
        JsonPath responseJsonFormat = response.jsonPath();
        
        String userName = (String) responseJsonFormat.get("userName[0]");
        
        if(userName=="")
        	System.out.println("No one with this name has been found.");
        
        else {
        
        
        response.then().
                statusCode(200).
        
                body("username[0]", equalTo("Samantha")).
                body("name[0]", equalTo("Clementine Bauch")).extract().response();
        
    
        
        Integer userId = (int) responseJsonFormat.get("id[0]");

        System.out.println("userId: " + userId);
        
        userPosts(userId);       
        }
  
    }

	
	public void userPosts(int userId) {
		
		Response response = RestAssured.given().when().get(basePath + "/posts?userId="+userId).then().extract().response();

		JsonPath responseJsonFormat = response.jsonPath();
		
		ArrayList idList = responseJsonFormat.get("id");
		
		System.out.println("ids: " + idList);
		
		for(int i=0;i<idList.size();i++) {
			
			commentsPost((int)idList.get(i));
			
		}


	}
	
	
	public void commentsPost(int postId) {
		
		Response response = RestAssured.given().when().get(basePath + "/comments?postId="+postId).then().extract().response();
		JsonPath responseJsonFormat = response.jsonPath();
		ArrayList emailList = responseJsonFormat.get("email");

		System.out.println("emails: " + emailList);
		
		for(int i=0;i<emailList.size();i++) {
			
			validateEmail(emailList.get(i).toString());
			
		}
	
		
	}
	
	@Test
	public Boolean validateEmail(String email) {

		System.out.println("E:" + email);
		String regexPattern = "^(.+)@(\\S+)$";
		Boolean validate= Pattern.compile(regexPattern)
			      .matcher(email)
			      .matches();
		System.out.println(validate);
		return validate;
		 	
		 
		
	}



}

