import java.util.*;

public class LibraryMgmt
{
static Scanner sc=new Scanner(System.in);
static ArrayList<Book> books=new ArrayList<>();
static ArrayList<Member> members=new ArrayList<>();
public static void main(String[]args)
 {
  while(true)
  {
  System.out.println();
  System.out.println("^^^^^^_________ Welcome user please select your choice __________^^^^^");
  System.out.println("1-> Login as admin \n2-> Login as member");
  int ch=sc.nextInt(); 
   switch(ch)
    {
       case 1:
             logAdmin();
             break;
       case 2:
             logMember();
             break;
       default:
             System.out.println("Invalid key...");
             break;
    }
  }
 }

 // admin code.... 

 public static void logAdmin()
   {
     System.out.println("Enter name and password"); 
     String Aname=sc.next();
     String Apass=sc.next();
     if(Aname.equals("1") && Apass.equals("1"))
         adminAccess();
     else
        System.out.println("Invalid credentials exiting...");
   }
public static void adminAccess()
  {
    System.out.println("********** Welcome Admin please select your choice *********");
    while(true)
    {
    System.out.print("\n1->Add book \n2->Remove book \n3->View book list");
    System.out.println("\n4->Add member \n5->Remove member \n6->View member list");
    int ch=sc.nextInt();
    switch(ch)
     {
       case 1:
             addBook();
             break;
       case 2:
             removeBook();
             break;
       case 3:
             viewBooks();
             break;
       case 4:
             addMember();
             break;
       case 5:
             removeMember();
             break;
       case 6:
             viewMembers();
             break;
      default:
             System.out.println("Invalid key exiting...");
             return;
     }
    }
  }
 public static void addBook()
   {
        System.out.println("enter book name: ");
        String title=sc.next();
        System.out.println("enter  description: ");
        String des=sc.next();
        System.out.println("enter Author name: ");
        String Aname=sc.next();
        System.out.println("enter publish date: ");
        String date=sc.next();
        System.out.println("enter edition number: ");
        int edition=sc.nextInt();
        System.out.println("enter price: ");
        int price=sc.nextInt();
        System.out.println("enter book count: ");
        int cnt=sc.nextInt();

        Book b=new Book(title,des,Aname,date,edition,price,cnt);      
        books.add(b);
        System.out.println("Book name *** "+title+" *** added successfully");
   }
  
  public static void removeBook()
   {
        System.out.print("enter book id : ");
        int id=sc.nextInt();
        
        for(Book b:books)
          {
             if(b.id==id)
               {
                 books.remove(b);
		 System.out.println("Book id : "+id+" removed successfully"); 
                 return;
               }
          }
       System.out.println("book id : "+id+" not exists");
        
   }

  public static void viewBooks()
    {      
            System.out.println("bookId | bookTitle | bookDescription | bookAuthor_name | bookPublishDate | bookEdition | bookPrice | bookCnt");
        for(Book b:books)
         {
            System.out.println(b.id+" "+b.title+" "+b.description+" "+b.Author_name+" "+b.date+" "+b.edition+" "+b.price+" "+b.cnt);
         }
    }

  public static void addMember()
   {
        System.out.println("enter member name: ");
        String name=sc.next();
        System.out.println("enter password: ");
        String pass=sc.next();
        System.out.println("enter age: ");
        int age=sc.nextInt();
        System.out.println("enter gender (f/m): ");
        char gender=sc.next().charAt(0);
        System.out.println("enter joining date: ");
        String jdate=sc.next();

        Member m=new Member(name,pass,age,gender,jdate);      
        members.add(m);
        System.out.println("member name *** "+name+" *** added successfully");
   }
  
   public static void  removeMember()
    {
        System.out.print("enter member id : ");
        int id=sc.nextInt();
        
        for(Member m:members)
          {
             if(m.id==id)
               {
                 members.remove(m);
		 System.out.println("Member id : "+id+" removed successfully"); 
                 return;
               }
          }
       System.out.println("Member id : "+id+" not exists");
        
    }

     public static void viewMembers()
    {
             System.out.println("memberId | memberName | memberPassword | memberAge | memberGender | memberJoiningDate | memberIssuedBooksList");
        for(Member m:members)
         {
            System.out.println(m.id+" "+m.name+" "+m.pass+" "+m.age+" "+m.gender+" "+m.jdate+" "+m.issued);
         }
    }

  // member code....

 public static void logMember()
   {
     System.out.println("Enter name and password");
     String name=sc.next();
     String pass=sc.next(); 
     boolean flag=true;
      for(Member m: members)
          {
             if(m.name.equals(name) && m.pass.equals(pass))
               {
                memberAccess(m); 
                flag=false;
                break;               
               }
          }
      if(flag)
         System.out.println("Invalid credentials / user not found ..."); 
   }
   public static void memberAccess(Member m)
     {
       System.out.println("Welcome *** "+m.name+" *** to go further select your choice");  
       while(true)
        {
         System.out.println("1->Search book \n2->Issue book \n3->Return book");
         int choice=sc.nextInt();
         switch(choice)
          {
            case 1:
                    searchBook();
                    break;
	    case 2:
                    issueBook(m);
                    break;
	    case 3:
                    returnBook(m);
                    break;
            default:
                    System.out.println("Invalid key exiting...");
                    return;
          }
       } 
     }
  
   public static void searchBook()
    {
       System.out.println("Enter book name to search");
       String search=sc.next();
       boolean flag=true;
       for(Book b:books)
        {
           if(b.title.equals(search))
                 {
                  System.out.println("Book name *** "+search+" *** is available");
                  flag=false;
                  break;
                 }
        }
      if(flag)
         System.out.println("Book name *** "+search +" *** is not available");
    }

   public static void issueBook(Member m)   
     {
        System.out.println("Enter the book id");
        int issueId=sc.nextInt();
        boolean flag=true;
        for(Book b:books)
        {
           if(b.id==issueId)
                 {
                 
                  System.out.println("Book id *** "+issueId+" *** is available checking for availability....");
                  if(m.issued.size()>=5)
                   { 
                     System.out.println("Cannot issue you have reached your book issue limit 5");
                     break;
                   }
                  else
                   {
                     if(b.cnt < 1)
                        {System.out.println("Book is not available in the library sorry.... exiting"); break;}
                     m.issued.add(issueId);
                     b.cnt--;
                     System.out.println("Book id : "+issueId+" issued to "+m.name +" successfully");
                   }
                  flag=false;
                  break;
                 }
        }
      if(flag)
         System.out.println("Book id *** "+issueId +" *** is not available");
     }


   public static void returnBook(Member m)
    {
        System.out.println("Enter the book id");
        int returnId=sc.nextInt();
        boolean flag=true;
        for(Book b:books)
        {
           if(b.id==returnId)
                 {
                     System.out.println("Book id *** "+returnId+" *** is available");
                     m.issued.remove(m.issued.indexOf(returnId));
                     b.cnt++;
                     System.out.println("Book id : "+returnId+" returned from "+m.name +" successfully");
		     flag=false;
                     break;
                 }
                 
                 
        }
      if(flag)
         System.out.println("Book id *** "+returnId +" *** is not available");
    }
   

}