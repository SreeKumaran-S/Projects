import java.util.*;
public class DeliveryAgent {
    String name;
    int vno;
    int licence;
    long earnings;
    long phone;
    boolean status;
    String location;
    ArrayList<String>history;

    public DeliveryAgent(String name,long phone,int vno,int licence,String location)
    {
        this.name=name;
        this.phone=phone;
        this.vno=vno;
        this.licence=licence;
        status=true;
        history=new ArrayList<>();
        this.location=location;

    }
}
