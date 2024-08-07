import java.util.*;
 public class Foodozers {
    static int cpkm=10;
    static int camnt=5;
    static Scanner sc=new Scanner(System.in);
    static ArrayList<Customer>customer=new ArrayList<>();
    static ArrayList<DeliveryAgent>deliveryAgent=new ArrayList<>();
    static ArrayList<Hotel>hotel=new ArrayList<>();
    static ArrayList<String>orders=new ArrayList<>();
    static ArrayList<String>locations=new ArrayList<>();

    public static void main(String[] args) {
        String AppName="Foodozers";
        locations.add("a"); locations.add("b"); locations.add("c"); locations.add("d");
        orders.add("Hotel name || Food || Cnt  || Customer name || Delivery Agent name || Total price ");

        while(true) {
            System.out.println("          ^_^   Welcome to "+AppName+" , User select your option    ^_^          ");
            System.out.println("1-> Login \n2-> Sign-up \n3-> To view cost parameters");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    System.out.println("Cost per kilo meter : "+cpkm);
                    System.out.println("Default offer value for new customers : "+camnt);
                    break;
                default:
                    System.out.println("Invalid key try again");
            }
        }
    }
    public static void login()
    {
        System.out.println("1-> Customer \n2-> Delivery Agent \n3-> Hotel \n4-> Admin");
        int ch=sc.nextInt();
        sc.nextLine();
          if(ch==1) cusLog();
          else if(ch==2) delivLog();
          else if(ch==3) hotelLog();
          else if(ch==4) adminLog();
          else System.out.println("Invalid key exiting...");
    }
    public static void cusLog()
    {
        System.out.println("\n");
        System.out.println("enter phone : ");
        long phone=sc.nextLong();
        for(Customer c: customer)
        {
            if(c.phone==phone)
            {
                while(true)
                {
                    System.out.println("Welcome "+c.name);
                    System.out.println("1-> To order \n2-> To check History\n3-> Location Change \n4-> Exit");
                    int ch=sc.nextInt(); sc.nextLine();
                    switch(ch)
                    {
                        case 1:
                            order(phone); break;
                        case 2:
                            for(String s:c.history)
                            {
                                System.out.println(s);
                            }
                            break;
                        case 3:
                            System.out.println("enter location to change : ");
                            System.out.println("Valid locations : ");
                            System.out.println(locations);
                            String cloc=sc.nextLine();
                            if(validChk(cloc)==false)
                            {
                                System.out.println("Choosen location not available exiting.. ");
                                break;
                            }
                            c.location=cloc;
                            System.out.println("Location Changed successfully "+ c.location);
                            break;
                        case 4:
                            System.out.println("exiting.. "); return;
                        default:
                            System.out.println("Invalid key , try again...");
                    }

                }
            }
        }
        System.out.println("No entry found in the db exiting...."); return;
    }
    public static void order(long phone)
    {
        for(Customer c: customer)
        {
           if (c.phone == phone)
            {
                System.out.println("\n");
                System.out.println("select shop and food in the menu");
                for(Hotel h:hotel)
                {
                    if(h.status)
                    {
                        System.out.println(h.hname);
                        Iterator it = h.menu.entrySet().iterator();
                        System.out.println("Item -------  Price");
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                        System.out.println("Do you wish to order here ?");
                        System.out.println("1-> Yes \n2-> No");
                        int ch=sc.nextInt(); sc.nextLine();
                        if (ch == 1) {
                        while(true)
                            {
                                System.out.println("Enter food and count ");
                                String food = sc.nextLine();
                                int cnt = sc.nextInt();
                                sc.nextLine();
                                if (h.menu.containsKey(food)) {
                                     int amount=cnt*h.menu.get(food);
                                    String value = h.hname + " || " + food + "  ||  " + cnt + " ||  " + c.name;
                                    amount-=c.coupon;
                                    c.coupon=0;
                                    String order=delivChk(c.location,h.address,value,amount);
                                    if(order.length()>0)
                                    {
                                        System.out.println("Order placed ^_^  Bill details : ");
                                        System.out.println(order);
                                        c.ocnt++;
                                        if(c.ocnt%3 == 0)
                                            c.coupon+=camnt;
                                        c.history.add(order);
                                        orders.add(order);
                                    }
                                    else {
                                        System.out.println("No Delivery guy available on your location");
                                    }

                                } else {
                                    System.out.println("Dont trick me , specified food not available");
                                }
                                System.out.println("1-> exit this hotel menu \nAny other key to resume again..");
                                int ex=sc.nextInt(); sc.nextLine();
                                if(ex==1)
                                    break;

                            }
                        }
                        System.out.println("-------------------------");
                    }
                }

            }
        }
    }
    public static String delivChk(String clocation,String hlocation,String value,int amount)
    {
        ArrayList<DeliveryAgent> freeAgents=new ArrayList<>();
        for(DeliveryAgent d:deliveryAgent)
        {
            if(d.status)
            {
                freeAgents.add(d);
            }
        }
        if(freeAgents.size()==0) return "";
        DeliveryAgent choosed=freeAgents.get(0);
        int min=Math.abs(choosed.location.charAt(0)-hlocation.charAt(0));
        for(DeliveryAgent fd:freeAgents)
        {
            if(Math.abs(fd.location.charAt(0)-hlocation.charAt(0)) < min )
            {
                choosed=fd;
                min=Math.abs(fd.location.charAt(0)-hlocation.charAt(0));
            }

        }
        amount+=(Math.abs(clocation.charAt(0)-hlocation.charAt(0))*cpkm);
        value+="  || "+choosed.name +" || "+amount;
            choosed.history.add(value);
            choosed.status=false;
            choosed.earnings+=amount;
            choosed.location=clocation;
            return value;
    }

    public static void delivLog()
    {
        System.out.println("\n");
        System.out.println("enter vehicle no");
        int vno=sc.nextInt();
        for(DeliveryAgent d:deliveryAgent)
        {
            if(d.vno==vno)
            {
                System.out.println("Welcome "+d.name);
                while(true)
                {
                    System.out.println("1-> status Change \n2-> Location Change \n3-> View History \nAny num to exit");
                    int ch=sc.nextInt(); sc.nextLine();

                    if(ch==1)
                    {
                        boolean flag=sc.nextBoolean(); sc.nextLine();
                        d.status=flag;
                        System.out.println("Status changed successfully "+ d.status);
                    }
                    else if(ch==2)
                    {
                        System.out.println("Enter change location : ");
                        System.out.println("Valid locations : ");
                        System.out.println(locations);
                        String clocation=sc.nextLine();
                        if(validChk(clocation)==false)
                        {
                            System.out.println("Choosen location not available exiting.. ");
                            break;
                        }
                        d.location=clocation;
                        System.out.println("Location changed successfully "+ d.location);
                    }
                    else if(ch==3)
                    {
                        for(String s: d.history)
                        {
                            System.out.println(s);
                        }
                        System.out.println("-----------------------");
                    }
                    else
                        return;
                }
            }
        }
        System.out.println("No entry found in the db exiting...."); return;
    }

public static void hotelLog()
{
    System.out.println("\n");
    System.out.println("enter hotel phone no : ");
    long phone=sc.nextLong(); sc.nextLine();
    for(Hotel h:hotel)
    {
        if(h.phone==phone)
        {
            System.out.println("welcome "+ h.oname);
            while(true)
            {
                System.out.println("1-> To change status \n2-> To update Menu");
                int ch=sc.nextInt(); sc.nextLine();
                if(ch==1)
                {
                   boolean flag=sc.nextBoolean(); sc.nextLine();
                   h.status=flag;
                   System.out.println("Status changed successfully "+ h.status);
                }
                else if(ch==2)
                {
                    h.menu.clear();
                    System.out.println("enter item count");
                    int n=sc.nextInt(); sc.nextLine();
                    for(int i=0;i<n;i++)
                    {
                        System.out.println("Enter item name and price");
                        String item=sc.nextLine();
                        int price=sc.nextInt(); sc.nextLine();
                        h.menu.put(item,price);
                    }
                    System.out.println("Updation done Successfully");
                }
                else {
                    System.out.println("invalid key exiting..."); return;
                }
            }
        }
    }
    System.out.println("No matching details "); return;
}

    public static void signUp()
    {
        System.out.println("\n");
        System.out.println("1-> Customer \n2-> Delivery Agent \n3-> Hotel");
        int ch=sc.nextInt();
        sc.nextLine();
        if(ch==1)
        {
            System.out.println("enter name , phone number , location:");
            String name=sc.nextLine();
            long phone=sc.nextLong();
            sc.nextLine();
            if(cchk(phone)) {
                System.out.println("Already customer exists exiting..");
                return;
            }
            System.out.println("Valid locations : ");
            System.out.println(locations);
            String location=sc.nextLine();
            if(validChk(location)==false)
            {
                System.out.println("Choosen location not available exiting.. ");
                return;
            }
            Customer c=new Customer(name,phone,location);
            customer.add(c);
        }
        else if(ch==2)
        {
            System.out.println("enter name , phone number , vehicle number , licence number ,location");
            String name=sc.nextLine();
            long phone=sc.nextLong();
            sc.nextLine();
            int vno=sc.nextInt();
            int licence=sc.nextInt();
            sc.nextLine();
            System.out.println("Valid locations : ");
            System.out.println(locations);
            String location=sc.nextLine();
            if(validChk(location)==false)
            {
                System.out.println("Choosen location not available exiting.. ");
                return;
            }
            if(dchk(vno,licence,phone)) {
                System.out.println("Already agent exists exiting..");
                return;
            }

            DeliveryAgent d=new DeliveryAgent(name,phone,vno,licence,location);

            deliveryAgent.add(d);
        }
        else if(ch==3)
        {
            System.out.println("enter hotel name , owner name  , location , phone");
            String hname=sc.nextLine();
            String oname=sc.nextLine();
            System.out.println("Valid locations : ");
            System.out.println(locations);
            String address=sc.nextLine();
            if(validChk(address)==false)
            {
                System.out.println("Choosen location not available exiting.. ");
                return;
            }
            long phone=sc.nextLong();  sc.nextLine();
            if(hchk(address,phone)) {
                System.out.println("Already hotel details exists exiting..");
                return;
            }
            System.out.println("enter count of menu");
            int cnt=sc.nextInt();  sc.nextLine();

            Hotel h=new Hotel(hname,oname,address,phone);
            for(int i=0;i<cnt;i++)
            {
                System.out.println("enter name and price ");
                String item=sc.nextLine();
                int price=sc.nextInt(); sc.nextLine();
                h.menu.put(item,price);
            }
            hotel.add(h);

        }
    }

    public static boolean dchk(int vno,int licence,long phone)
    {
       for(DeliveryAgent d: deliveryAgent)
       {
           if(d.vno == vno || d.licence==licence || d.phone==phone)
               return true;
       }
       return false;
    }
    public static boolean cchk(long phone)
    {
        for(Customer c: customer)
        {
            if(c.phone==phone)
                return true;
        }
        return false;
    }
    public static boolean hchk(String address,long phone)
    {
        for(Hotel h:hotel)
        {
            if(h.phone==phone)
                return true;
        }
        return false;
    }
    public static void cshow() {
        System.out.println("\n");
        System.out.println("Customer Details");
        System.out.println("Customer name || phone || location || coupon value || order count || Orders ");
        for (Customer c : customer) {
            System.out.println(c.name+" "+c.phone+" "+c.location+" "+c.coupon +" "+c.ocnt);
            for(String s:c.history)
            {
                System.out.println(s);
            }
            System.out.println("------------------------");
        }
        System.out.println("-----------  End of all Details --------------");

    }
    public static void dshow()
    {
        System.out.println("\n");
        System.out.println("Delivery Agent Details");
        System.out.println("Delivery Agent name || Vehicle number || Licence number || phone || location || Earnings || status || Orders ");
        for(DeliveryAgent d:deliveryAgent)
        {
            System.out.println(d.name+" "+d.vno+" "+d.licence+" "+d.phone+" "+d.location+" "+d.earnings+" "+d.status);
            for(String s:d.history)
            {
                System.out.println(s);
            }
            System.out.println("------------------------");
        }
        System.out.println("-----------  End of all Details --------------");
    }

        public static void hshow() {
            System.out.println("\n");
            System.out.println("Hotel Details");
            System.out.println("Hotel name || Owner name || Location || status || menu");
            for (Hotel h : hotel) {
                System.out.println(h.hname+" "+h.oname+" "+h.address+" "+h.status);
                System.out.println("Item name || price");
                Iterator it = h.menu.entrySet().iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
                System.out.println("-------------------------");
            }
            System.out.println("-----------  End of all Details --------------");
        }


        public static void oshow()
        {
            System.out.println("\n");
           System.out.println("Order Details");
           for(String od:orders)
            {
              System.out.println(od);
            }
             System.out.println("-----------  End of all Details --------------");
        }
public static void adminLog()
{
    System.out.println("enter uid : ");
    int id=sc.nextInt();
    if(id==99)
    {
        while(true)
        {
            System.out.println("\n");
            System.out.println("1-> To view all orders \n2-> Customer Details \n3-> Hotel Details \n4-> Delivery Agent Details \n5-> To add locations \n6-> To view Valid locations \n7-> To change coupon  or cost per km \n8-> To turn off application\nAny other key->Exit");
            int ch=sc.nextInt(); sc.nextLine();
            if (ch == 1)
                oshow();
            else if (ch == 2)
                cshow();
            else if (ch == 3)
                hshow();
            else if (ch == 4)
                dshow();
            else if(ch==5)
            {
                System.out.println("Locations : ");
                System.out.println(locations);
                String loc=sc.nextLine();
                if(locations.contains(loc))
                  {System.out.println("Given Location already in the exists "+locations); return;}
                
                locations.add(loc);
                System.out.println("Updated Locations : ");
                System.out.println(locations);
            }
            else if(ch==6)
            {
                System.out.println(locations);
            }
            else if(ch==7)
            {
                System.out.println("1-> change cpkm \n2-> change coupon value");
                int choose=sc.nextInt();  sc.nextLine();
                if(choose==1)
                {
                    int coup=sc.nextInt();  sc.nextLine();
                    camnt=coup;
                    System.out.println("New coupon value : " + camnt);
                }
                else
                {
                    int change = sc.nextInt(); sc.nextLine();
                    cpkm = change;
                    System.out.println("New cost per km : " + cpkm);
                }
            }
            else if (ch == 8)
                System.exit(0);

            else
                return;
        }

    }
    else
    {
        System.out.println("Invalid key exiting..");
    }
}
public static boolean validChk(String loc)
{
    if(locations.contains(loc))
        return true;
    return false;
}
}
