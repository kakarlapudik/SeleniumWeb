package rough_work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionWindowsAuthentication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//The URL format for jTDS is: jdbc:jtds:<server_type>://<server>[:<port>][/<database>][;<property>=<value>[;...]]
		
		
		try{
			
			String url ="jdbc:sqlserver://icsqld92.hsn.com/qa1;databaseName=HSN_Commerce;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url);
			
			Statement stmt = conn.createStatement(); //select webp_id from hsn_content..cnt_product where pfid = '181450'
			ResultSet rs = stmt.executeQuery("SELECT Prod_Name FROM [HSN_Content].[dbo].[cnt_product] where  pfid = '100000'");
			while(rs.next()){
				System.out.println("Prod_Name is :: "+rs.getString(1));
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}

	}

}
