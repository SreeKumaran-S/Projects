
import java.util.*;

public class Hotel {
    String hname;
    String oname;
    String address;
    long phone;
    boolean status;
    HashMap<String , Integer>menu;

    public Hotel(String hname,String oname,String address,long phone)
    {
        this.hname=hname;
        this.oname=oname;
        this.address=address;
        this.phone=phone;
        this.status=true;
        menu=new HashMap<String,Integer>();
    }
}
