package com.hsn.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class HSNBusinessFunctions {
	
	
	
		public String SignIn(String object,String data){
		
		try {
			
			System.out.println("signed in well ");
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return Constants.KEYWORD_FAIL;
		}
	}
	

}
