package rough_work;

public class Car {

	String Name;
	int Number;
	static Car c;
	
	private Car() 
	
		{
			System.out.println("created a car");
			
		}
	
	public static Car getInstance(){
		
		if (c==null)
			c= new Car();
		return c;
	}
		
	}


