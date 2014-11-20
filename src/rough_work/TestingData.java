package rough_work;

import com.hsn.util.TestUtil;
import com.hsn.util.Xls_Reader;

public class TestingData {

	public TestingData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestingData nt = new TestingData();
		nt.start();

	}
	
	public void start(){
		
		Object[][] myTestData = null;
		
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//hsn//xls//Smoke.xlsx");
		System.out.println("The shee exists "+xls.isSheetExist("Data"));
		
		myTestData =  TestUtil.getData("SmokeTest", xls);
		System.out.println(myTestData.toString());
	}

}
