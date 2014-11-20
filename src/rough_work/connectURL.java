package rough_work;

import java.sql.*;

import com.hsn.util.Xls_Reader;

public class connectURL {

    public static void main(String[] args) throws InterruptedException {

    	
    	//String url = "jdbc:jtds:sqlserver://ilsqlp91c.hsnlab.com:1433/";
    	String url = "jdbc:jtds:sqlserver://172.20.254.98:1433/";
		String dbname = "HSN_Commerce";
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String userName = "userQA";
		String password = "Q%USR#33";
		
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//sql_results.xlsx");
		
		if(!xls.isSheetExist("Customerquery")){
			xls.addSheet("Customerquery");
		}
		else{
			xls.removeSheet("Customerquery");
			Thread.sleep(3000);
		    xls.addSheet("Customerquery");
		}
		
		
		try{
			
			Class.forName(driver).newInstance(); //initialize driver
			
			Connection conn = DriverManager.getConnection(url+dbname,userName,password);
			
			String str_sqlString = "SELECT distinct (A.Email) , A.Email_ID , C.Order_Id FROM [HSN_Commerce].[dbo].[cmr_Email] As A , [HSN_Commerce].[dbo].[cmr_CustomerEmail] As B , [HSN_Commerce].[dbo].[cmr_FirstCustomerOrder] AS C where Email = '5613perfusrrun30010006@hsnlab.com' and A.Email_ID = B.Email_ID and B.Cust_ID = C.Cust_Id";
			
			PreparedStatement ps = conn.prepareStatement(str_sqlString);
			
			System.out.println("my string is "+ps.toString());
			
			//Statement stmt = conn.createStatement(); //select webp_id from hsn_content..cnt_product where pfid = '181450'
			ResultSet rs = ps.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
		    int numberOfColumns = rsmd.getColumnCount();
		    
		    for(int i = 1 ; i <= numberOfColumns;i++ ){
				System.out.println("Collumn Name  :: "+ rsmd.getColumnName(i));
				xls.addColumn("Customerquery", rsmd.getColumnName(i));
			}
			
		    int xlsrownum = 2;
		    
			while(rs.next()){
				for(int i = 1 ; i <= numberOfColumns;i++ ){
					System.out.println("value in col "+i+"is"+ rs.getString(i));
					xls.setCellData("Customerquery", rsmd.getColumnName(i) , xlsrownum, rs.getString(i));
				}
				xlsrownum++;
			}
			
			conn.close();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
    }	
		
}