package rough_work;
import java.sql.*;

public class TestDBOracle {

  public static void main(String[] args)
      throws ClassNotFoundException, SQLException
  {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    String url = "jdbc:oracle:thin:@//spdudb18.hsn.net:1521/seb81uat";
    Connection conn =
    DriverManager.getConnection(url,"kakarlapudik","HSN$008022 ");
    conn.setAutoCommit(false);
    Statement stmt = conn.createStatement();
    ResultSet rset =
         stmt.executeQuery("select oi.ROW_ID from siebel.s_order_item oi, siebel.cx_s_order_item_xm pay where oi.row_id = pay.par_row_id and oi.accnt_order_num = '1001115363'");
    while (rset.next()) {
         System.out.println (rset.getString(1));
    }
    stmt.close();
    System.out.println ("Ok.");
  }
}