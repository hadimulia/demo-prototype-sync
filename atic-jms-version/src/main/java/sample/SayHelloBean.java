package sample;

public class SayHelloBean {

	private static final  String HELLO_MSG = "Hello ";
	 
	public String sayHelloTo(String name){
		System.out.println(name);
		return HELLO_MSG+name;
	}
}
