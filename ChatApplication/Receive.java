 import java.net.*;
 import java.awt.*;
 import java.io.*;
 class Receive implements Runnable
 {
    DataInputStream dis;
    TextArea downloadBox;
    Receive(DataInputStream dis,TextArea downloadBox)
    {
       this.dis=dis;
       this.downloadBox=downloadBox;
    }
    public void run()
     {
      try
      {
        while(true)
        {
          String receivedData=dis.readUTF();
          if(receivedData.length()>0)
           { 
             System.out.println("Other User : "+receivedData); 
             if(Frame1.flag)
               downloadBox.append("Other User : "+receivedData);
             else
              downloadBox.append("\n"+"Other User : "+receivedData);
             Frame1.flag=false;
           }
        }
      }
      catch(Exception e)
      {System.out.println(e);}
      
     }
 }