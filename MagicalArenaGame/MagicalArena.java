import java.util.*;

public class MagicalArena {
  static HashMap<Integer,Player>players=new HashMap<Integer,Player>();
    public static void main(String[] args) {
      Scanner sc=new Scanner(System.in);
      System.out.println("--------------- Welcome to Magical Arena ------------\n");
      System.out.println("Enter total players count : "); // Game resources values 
      int n;
      while(true)
      {
        String cnt=sc.nextLine();
        cnt=cnt.trim();
       if(typeChk(cnt,"int"))
         {System.out.println("Need only integer value for total players, provide valid input ");continue;}
       n=Integer.parseInt(cnt);
       if(n < 2)
         System.out.println("Need atleast two players , provide valid input ");
       else
         break;
      }
      
      for(int i=1;i<=n;i++)
       {
        System.out.println("Player Id : "+i);
        System.out.println("Health : ");         // health
        String Shealth=sc.nextLine();  Shealth=Shealth.trim();
          if(typeChk(Shealth,"int")) 
             { System.out.println("Need only integer values for health, provide valid input "); i--; continue; }
        System.out.println("Strength : ");      // strength
        String Sstrength=sc.nextLine(); Sstrength=Sstrength.trim();       
          if(typeChk(Sstrength,"int")) 
             { System.out.println("Need only integer values for strength, provide valid input "); i--; continue; }
        System.out.println("Attack : ");        // attack
        String Sattack=sc.nextLine();   Sattack=Sattack.trim();     
          if(typeChk(Sattack,"int"))
             { System.out.println("Need only integer values for attack, provide valid input "); i--; continue; }
       
         int health=Integer.parseInt(Shealth);
         int strength=Integer.parseInt(Sstrength);
         int attack=Integer.parseInt(Sattack);
        if(health <=0 || strength <=0 || attack <=0 )
           {
            System.out.println("Need only positive integer values try again ");
            i--;
            continue;
           }
         players.put(i,new Player(i,health,strength,attack)); // player objects added into players ArrayList
       }
      System.out.println();
      
      
         
     while(true)
     {
      details(); // Display player values details  
      int p1=-1,p2=-1;
      while(true)
       {
	       System.out.println("To start the game enter 2 valid player ids : "); // only any provided two players shall play 
         String sp1=sc.nextLine();  // player a
         sp1=sp1.trim();
         String sp2=sc.nextLine();  // player b
         sp2=sp2.trim();
         if(typeChk(sp1,"int") || typeChk(sp2,"int"))
          {System.out.println("Need only integer values for player id values , provide valid input "); continue;  }
         p1=Integer.parseInt(sp1);
         p2=Integer.parseInt(sp2);
         if(p1!=p2 && (p1!=0 && p2!=0) && p1 <= players.size()&& p2 <= players.size())
          {
            System.out.println("Players choosed succuessfully ids : "+p1+" , "+p2);
            break;
          }
         else
          {
            System.out.println("Invalid player ids , try again");
          }
       }
      Player one=new Player(players.get(p1));
      Player two=new Player(players.get(p2));
      if(two.health > one.health)
        arena(one,two);
      else 
        arena(two,one);

      System.out.println("Do you wish to play again ? \ny-> Yes\nAny other key to exit");
      if(sc.next().charAt(0)!='y')
       break;
     }
    System.out.println(".......... ^_^ Happy Playing  ^_^ .......... ");
  }

  public static void arena(Player one ,Player two)
   {
     System.out.println("**** Entered arena ****\n");
     // player with low strength is choosed as one
     int x=0;
     while(true)
      {     
        if(x%2==0)
         {
          System.out.println("\nRound : "+(x+1)+" || Striker : "+ one.id +" || Defence : "+two.id+"\n");
          if(play(one,two))
           break;
         }
        else
          {
           System.out.println("\nRound : "+(x+1)+" || Striker : "+ two.id +" || Defence : "+one.id+"\n");
           if(play(two,one))
            break;
          }
          
       
        try
         {Thread.sleep(1000);} // code pauses and play at 1 sec gap
        catch(Exception e)
         {System.out.println(e);}
        
        
        System.out.println("-----------------------------------------------------");  
        x++;
      }
      System.out.println("**** Exiting arena ****\n");
   }
   public static boolean play(Player one,Player two)
    {
        int strike=0;
        int defence=0;
        for(int x=0;x<2;x++)
         {
	         int roll=(int)(Math.random()*(6-1+1)+1);   // dice roll (1-6)
           if(x==0)
           {
            strike=one.attack*roll;  
            System.out.println(one.id +" is attacker with strike : "+one.attack +" * "+roll+" = "+strike);
	         }   
           else
           {
            defence=two.strength*roll;
            System.out.println(two.id +" is defencer with defence : "+two.strength +" * "+roll+" = "+defence);
	          int diff=strike-defence;
            if(diff <= 0)
               {
                 System.out.println("No changes in health"); 
                 break;
               }
	          two.health=two.health-diff; 
	          System.out.println(two.id +" health : "+two.health+" reduced by "+diff);
            if(two.health <= 0)
             {
               System.out.println("\n________________________ ***** Player "+one.id+" Won !! ***** _______________________\n");
               return true;
             }
           }
         }
      return false;
    }
   

  public static  void details()
     {
       System.out.println("*************** Player Details **************************");  
       System.out.println("id || health || strength || attack");
       for(Player p:players.values())
        {
         System.out.println(p.id +"      "+p.health+"        "+p.strength+"            "+p.attack);
        }
       System.out.println("*****************************************\n");  
     }

  public static boolean typeChk(String val,String dataType)
  {
     // only if the values are integers are checked due to input values
     boolean flag=false;
     if(dataType.equals("int"))
     {
      if(val.length()==0)
        return true;
     for(int i=0;i<val.length();i++)
      {
        char c=val.charAt(i);
        if(c >= '0' && c <= '9')
           continue;
        else
           {flag=true; break;}
      }
     }
    return flag;
  }

}

class Player
{
  int id;
  int health;
  int strength;
  int attack;

  Player(int id,int health,int strength,int attack)
  {
    // assigning player values
    this.health=health;
    this.strength=strength;
    this.attack=attack;
    this.id=id;
  }
  Player()
   {}
  Player(Player ob)
   {
    this(ob.id,ob.health,ob.strength,ob.attack);
   }

}