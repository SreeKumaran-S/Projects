import java.util.*;
import java.io.*;
import java.time.*;

public class TelephonicBill 
{
    public static void main(String[] args) throws IOException
    {
     int CPM=1;     
     try
      {
      Scanner sc=new Scanner(System.in);

   
      File f=new File("C:/Users/SreeKumaran/Desktop/TeleProj/Bills.txt");
      FileWriter w= new FileWriter(f,true);
 
      LocalDate myObj = LocalDate.now(); 
      w.write("Date : "+myObj+"\n");
      w.write("NAME ----- CALL TIME ----- CHARGE\n");
      w.close();

    while(true)
    {
      System.out.print("Please enter your Name : ");
      String name=sc.next();
      System.out.print("Please enter your Call Duration : ");
      int callTime=(int)Math.ceil(sc.nextDouble());
      
      int total=callTime*CPM;
      FileWriter Nw= new FileWriter(f,true);
      Nw.write(name+"       "+callTime+"                "+total+"\n");
      Nw.close();
      FileReader r=new FileReader(f);
      int i;
      while((i=r.read())!=-1)
       {
        System.out.print((char)i);
       }
       r.close();
     }

    }
    catch(Exception e)
      {e.getStackTrace();}
           
  }
}
