package rough_work;

import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateLink {
	
	@Test
	
	public static void fo(){
		
		String URLName = "http://img.hsni.com/images/formatting/hsn_logo_2014_v2.png";
		Assert.assertTrue(linkExists(URLName), "link is not valid "+URLName);
		
		
	}
	
	
	
	public static boolean linkExists(String URLName){
		    try {
		      HttpURLConnection.setFollowRedirects(false);
		      HttpURLConnection con =
		         (HttpURLConnection) new URL(URLName).openConnection();
		      con.setRequestMethod("HEAD");
		      return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		    }
		    catch (Exception e) {
		       e.printStackTrace();
		       return false;
		    }
		  }
	
	


}
