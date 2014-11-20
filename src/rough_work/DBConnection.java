package rough_work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//The URL format for jTDS is: jdbc:jtds:<server_type>://<server>[:<port>][/<database>][;<property>=<value>[;...]]
		
		String dburl = "jdbc:jtds:sqlserver://icsqld92.hsn.com:2499/";
    	//String url = "jdbc:jtds:sqlserver://172.20.254.98:1433/";
		String dbname = "HSN_Commerce";
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String userName = "TFSUser"; //
		String password = "DjRk8$crup3"; //
		
		try{
			
			
			Class.forName(driver).newInstance(); //initialize driver
			
			Connection conn = DriverManager.getConnection(dburl+dbname,userName,password);
			
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
