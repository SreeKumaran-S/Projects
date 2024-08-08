import java.util.*;

public class StopWatch
{
static  int i,hour,min,sec;
static  List<String>laps;
public static void main(String[] args) 
{     
  laps=Collections.synchronizedList(new ArrayList<>());
  Scanner sc=new Scanner(System.in); 

  Thread stopWatch=new Thread(()->{
     try
       {      
        while(true)
        {
         clrscr(); 
         synchronized(laps)
         {
         System.out.println("\n\n\n\n\n\n\n\n\n\t\t\t\t--- Stop Watch ---");
         System.out.println("\t\t\t\t------------------------");     
         System.out.println("\t\t\t\t||  Hour :: Min :: Sec ||");
         System.out.println("\t\t\t\t||  "+hour+"   ::  "+min+"   ::  "+sec+" ||");
         System.out.println("\t\t\t\t------------------------");
         System.out.println("\n\n\n\n\n\n\n\n\n\t\t\t\t------ Laps --------");
         
	 for(String str:laps)
           System.out.println("\t\t\t\t[  "+str+"  ]");
         }
         
         

         
         Thread.sleep(30);
         sec++;
         if(sec==60)
          {
            min++;
            sec=0;
            if(min==60)
              {
                hour++;
                min=0;
              }
          }
                 
        } 
      
       }

    catch(Exception e)
      { e.printStackTrace(); }
    });
  stopWatch.start();

  while(true)
  {
   String lapper=sc.nextLine();
   synchronized(laps)
   {
    laps.add(hour+" :: "+min+" :: "+sec);
   }
  }
  
 
  
   
 }

 public static void clrscr()
  {
    try
    {
      if(System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
      else
        Runtime.getRuntime().exec("clear");
    }
   catch(Exception e)
     { e.printStackTrace(); }
  }
}