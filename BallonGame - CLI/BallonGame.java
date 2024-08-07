import java.util.*;
public class BallonGame
{
static Scanner sc=new Scanner(System.in);
static char arr[][];
static boolean barr[][];
public static void main(String args[])
 {
   System.out.println("Enter the matrix size m,n  : ");
   int r=sc.nextInt();
   int c=sc.nextInt();
   arr=new char[r][c];
   for(char temp[] : arr)
      Arrays.fill(temp,'-');
   display();
   barr=new boolean [r][c];
   
  
   boolean chk=true;
   while(chk)
    {
       drop();
       int itr=0;
       for(int i=0;i<r;i++)
          {
             for(int j=0;j<c;j++)
                {
		  if(arr[i][j]!='-')
                     itr++;
                }
          }
       if(itr==r*c)
         break;
 
       System.out.println("Do you wish to continue(Y/N) : ");
       char choice=sc.next().charAt(0); 
       if(choice=='y')
          chk = true;
       else
          chk = false;
        
    }
   System.out.println("Program Stopped");
 } 

 public static void drop()
   {
     System.out.print("Enter the column number to drop : ");
     int col=sc.nextInt();
     if(col < 1 || col > arr[0].length)
        {System.out.println("invalid option try again "); return;}
     System.out.print("Enter the color of the balloon : ");
     char color=sc.next().charAt(0);
    
     for(int i=barr.length-1;i>=0;i--)
       {
        int looper=0;
        if(barr[i][col-1]==true)
          {
            while(looper!=arr[0].length)
               {
                if(barr[i][looper]==false)
                  {
                    arr[i][looper]=color;
             	    barr[i][looper]=true;
                    display();
                    burst(i,looper);
                    return;
		  }
		looper++; 
 	       }
          }
         
         if(barr[i][col-1]==false)
           {
              arr[i][col-1]=color;
              barr[i][col-1]=true;
              display();
              burst(i,col-1);
              return;
           }             
       }
   
      
    
   }

  public static void burst(int r,int v)
   {
      for(int c=2;c<arr[0].length;c++)
        {
         if(arr[r][c-2]!='-' && arr[r][c-1]!='-' && arr[r][c]!='-')
         if(arr[r][c-2]== arr[r][c-1] && arr[r][c-1]== arr[r][c])
            {
              barr[r][c-2]=false;
              barr[r][c-1]=false;
	      barr[r][c]=false;
	      arr[r][c-2]='-';
	      arr[r][c-1]='-';
	      arr[r][c]='-';
              System.out.println("bursted the ballons in columns");
              display();
            }   
	}
       for(int d=2;d<arr.length;d++)
        {
         if(arr[d-2][v]!='-' && arr[d-1][v]!='-' && arr[d][v]!='-')
         if(arr[d-2][v]== arr[d-1][v] && arr[d-1][v] == arr[d][v])
            {
              barr[d-2][v]=false;
              barr[d-1][v]=false;
	      barr[d][v]=false;
	      arr[d-2][v]='-';
	      arr[d-1][v]='-';
	      arr[d][v]='-';
	      System.out.println("bursted the ballons in rows");
              display();
            }   
	}



   }



   
  public static void display()
   { 
     for(char temp[]: arr)
      System.out.println(Arrays.toString(temp));
   } 
 
}