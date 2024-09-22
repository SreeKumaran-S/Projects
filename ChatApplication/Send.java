import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Send
 {
    DataOutputStream dos;
    Scanner sc;
    TextArea downloadBox;
    Button submit;
    TextField uploadBox;
    Send(DataOutputStream dos,TextArea downloadBox,TextField uploadBox,Button submit)
    {   
       this.sc=new Scanner(System.in);
       this.dos=dos;
       this.downloadBox=downloadBox;
       this.uploadBox=uploadBox;
       this.submit=submit;
       this.submit.addActionListener(new ActionListener()
       {
         public void actionPerformed(ActionEvent ae)
         {
          try
          {
            String receivedData=uploadBox.getText();
            if(receivedData.length()>0)
            {
              Calendar calendarData=Calendar.getInstance();
              int hour=calendarData.get(Calendar.HOUR_OF_DAY);
              int min=calendarData.get(Calendar.MINUTE);
              String meridian="";
              meridian=(hour>12)?"PM":"AM";
              
              hour=(hour%12==0)?12:(hour%12);
              receivedData=receivedData+"    [ "+hour+" : "+min+" "+meridian+" ]";

              uploadBox.setText("");
              System.out.println("You : "+receivedData);
              dos.writeUTF(receivedData);
             if(Frame1.flag)
               downloadBox.append("You: "+receivedData);
             else
              downloadBox.append("\n"+"You: "+receivedData);

              Frame1.flag=false;
            }
          }
          catch(Exception e)
          {System.out.println(e);}
         }
        });
    }  
 }