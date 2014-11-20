package rough_work;

import org.testng.annotations.Test;

public class AsyncValidation {
  @Test
  public void f() {
	  
	  String orderIdurl = "https://qa1.hsn.com/cmr/order_confirm/default.aspx?_hdod=166278177";
	  String orderId = "166278177";
	  
	  if(orderIdurl.contains(orderId))
		  System.out.println("ASYNC");
	  else
		  System.out.println("SYNC");;
  }
}
