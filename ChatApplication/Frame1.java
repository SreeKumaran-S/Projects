import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Frame1 extends Frame
{
  Label upload;
  TextField uploadBox;
  Button submit;
  Label download;
  TextArea downloadBox;
  static boolean flag=true;
  Panel chatPanel;
  Label timeLabel;
  Label timeBox;
  Calendar calendarData;
  int hour;
  int min;
  int sec;
  String meridian;
  Frame1()
  {
    chatPanel=new Panel();
    setLayout(null);
    chatPanel.setLayout(null);
    
    upload=new Label("Type Message ~>");
    uploadBox=new TextField();
    submit=new Button("submit");
    download=new Label("Received ~>");
    downloadBox=new TextArea(5,10);
    timeLabel=new Label("Time");
    timeBox=new Label();
     
        chatPanel.add(upload);
        chatPanel.add(uploadBox);
        chatPanel.add(submit);
        chatPanel.add(download);
        chatPanel.add(downloadBox);
        
        add(chatPanel);

        upload.setBounds(30,50,100,30);
        uploadBox.setBounds(130,50,400,70);
        submit.setBounds(550,90,80,30);
        download.setBounds(30,125,100,30);
        downloadBox.setBounds(130,125,400,200);
        downloadBox.setEditable(false);
        chatPanel.setBounds(30,40,650,350);
        
        Color customColor=new Color(14, 227, 81);
        chatPanel.setBackground(customColor);
        setBackground(Color.CYAN);

        timeLabel.setBounds(720,50,40,25);
        timeBox.setBounds(700,85,85,35);
        timeLabel.setAlignment(Label.CENTER);
        timeBox.setAlignment(Label.CENTER);
        timeLabel.setBackground(new Color(209, 202, 123));
        timeBox.setBackground(new Color(209, 202, 123));
       
       add(timeLabel);
       add(timeBox);
        setSize(800,420);
        setVisible(true); 
   


     
    //clock
    Thread clock=new Thread()
    {
      public void run()
      {
        try
        {
         while(true)
         {
          String currentTime=currTime();  
          timeBox.setText(currentTime);
          Thread.sleep(1000);
         }
        }
        catch(Exception e)
         {}
      }
    };
    clock.start();

    // closing...
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent we)
       {
        System.exit(0);
       }
    });
  }

  public String currTime()
  {
      calendarData=Calendar.getInstance();
      hour=calendarData.get(Calendar.HOUR_OF_DAY);
      min=calendarData.get(Calendar.MINUTE);
      sec=calendarData.get(Calendar.SECOND);
      meridian="";
      meridian=(hour>12)?"PM":"AM";
      hour=(hour%12==0)?12:(hour%12);
      return ""+hour+" : "+min+" : "+sec+" "+meridian;
  }
}