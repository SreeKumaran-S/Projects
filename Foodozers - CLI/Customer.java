import java.util.*;
public class Customer {
	int coupon;
    String name;
    long phone;
    String location;
    ArrayList<String>history;
	int ocnt;
    Customer(String name,long phone,String location)
    {
        this.name=name;
        this.phone=phone;
        history=new ArrayList<>();
        this.location=location;
		this.coupon=Foodozers.camnt;
		ocnt=0;
    }
}
