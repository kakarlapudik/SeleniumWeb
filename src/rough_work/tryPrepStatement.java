package rough_work;

import java.util.Hashtable;

public class tryPrepStatement {

    public static void main(String[] args) throws InterruptedException {

    	
    
			
		try{
			
			Hashtable<String,String> table = new Hashtable<String,String>();
			
			table.put("col_emailID", "harry@gmail.com");
			
			String str_sqlString = "SELECT * where Email = col_emailID and A.Email_ID = B.Email_ID and B.Cust_ID = C.Cust_Id";
			
			String str_updated = "";
			
			String[] strArr =  str_sqlString.split(" ");
			
			for(int arrindex = 0; arrindex < strArr.length ; arrindex ++){
				
				if ( strArr[arrindex].startsWith("col_"))
				{
					System.out.println(strArr[arrindex]);
					str_updated = str_updated + "'"+table.get(strArr[arrindex])+"'" + " ";
				
				}
				else 
					
					str_updated = str_updated + strArr[arrindex] + " ";
			}
			
			System.out.println("prepared statement is "+str_updated.toString());
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
    }	
		
}