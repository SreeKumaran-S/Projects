import java.net.*;
import java.io.*;
import java.util.*;
public class Client
{
    public static void main(String[]args)throws Exception
     {
        Socket s=null;
        System.out.println("Client running...");
        while(s==null)
        {
         try
          {        
            s=new Socket("localhost",9999);
          }
         catch(Exception e)
          {}
        }
        System.out.println("----- Connection established with Server -----");

        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        
        Frame1 frame=new Frame1();
        frame.setTitle("Client Machine"); 

        Send send=new Send(dos,frame.downloadBox,frame.uploadBox,frame.submit);
        Receive receive=new Receive(dis,frame.downloadBox);   

        Thread receiver =new Thread(receive,"receiverThread");
        receiver.start();
     
        receiver.join();

        System.out.println("----- Session Closed -----");
        System.exit(0);       
     }
}
