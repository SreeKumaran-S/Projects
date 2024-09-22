import java.net.*;
import java.io.*;
public class Server
{
    public static void main(String[]args)throws Exception
     {
        ServerSocket ss=new ServerSocket(9999);
        System.out.println("Server running......");
        
        Socket s=ss.accept();
        System.out.println("----- Connection established with Client -----");

        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
 
        Frame1 frame=new Frame1();
        frame.setTitle("Server Machine"); 

        Send send=new Send(dos,frame.downloadBox,frame.uploadBox,frame.submit);
        Receive receive=new Receive(dis,frame.downloadBox);
           
        Thread receiver =new Thread(receive);
        receiver.start();
        receiver.join();
        System.out.println("----- Session Closed -----");
        System.exit(0);
     }

}




