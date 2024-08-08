import java.util.*;

interface MustUse
{
void createPost(int uid,int pid);
void getNewsFeed(int uid);
void follow(int followerId, int followeeId);
void unfollow(int followerId, int followeeId);
void deletePost(int uid,int pid);
void getNewsFeedPaginated(int uid,int pageNumber);
}

public class FaceBook implements MustUse
{
static Scanner sc=new Scanner(System.in);
static ArrayList<Users> users;
public static ArrayList<Users> createUsers(int n)
 {
   ArrayList<Users> newUsers=new ArrayList<Users>();
   for(int i=0;i<n;i++)
     {
       Users u=new Users();
       newUsers.add(u);
     }
  return newUsers;
   
 }
public static void main(String[]args)
 {
 FaceBook fb=new FaceBook();
 users=createUsers(4); 
 while(true)
 {
 System.out.println("***** enter your choice ******");
 System.out.println("1->createPost \n2->getNewsFeed \n3->follow \n4->unfollow \n5->deletePost \n6->NewsFeed");
 int choice=sc.nextInt();
 switch(choice)
  {
    case 1:
          System.out.println("enter userId and postId");
          fb.createPost(sc.nextInt(),sc.nextInt());
          break;
    case 2:
          System.out.println("enter userId");
          fb.getNewsFeed(sc.nextInt());
          break;
    case 3:
          System.out.println("enter followerId and followeeId");
          fb.follow(sc.nextInt(),sc.nextInt());
          break;
    case 4:
          System.out.println("enter followerId and followeeId");
          fb.unfollow(sc.nextInt(),sc.nextInt());
          break;
    case 5:
          System.out.println("enter userId and postId");
          fb.deletePost(sc.nextInt(),sc.nextInt());
          break;
    case 6:
          System.out.println("enter userId and pageNumber");
          fb.getNewsFeedPaginated(sc.nextInt(),sc.nextInt());
          break;
    case 7:
          fb.view(sc.nextInt());
          break;
   default:
           System.out.println("INVALID INPUT");
   }
 }
 }

public void createPost(int uid,int pid)
 {   
  int post=pid;
  for(Users u:users)
     {
      if(u.uid==uid)
        {
          u.pidLst.add(pid);
          u.post.add(uid+" -> "+pid);
          break;
        }
       
     }
 }

@SuppressWarnings("unchecked")
public void getNewsFeed(int uid)
 {
  for(Users u:users)
    {
       if(u.uid==uid)
         {
          ArrayList<String>temp=(ArrayList<String>)u.post.clone();
          Collections.reverse(temp);
          System.out.println("self posts "+temp);
           for(int person : u.followings)
            {
              for(Users z:users) 
                 {
                  if(z.uid==person)
                    {
                    temp=(ArrayList<String>)z.post.clone();
                    Collections.reverse(temp);
                    System.out.println("UserId :"+z.uid +" posts "+temp);
                    }
                 }
            }
         }
    }
 }
public void follow(int followerId, int followeeId)
 {
  for(Users u:users)
    {
      if(u.uid==followerId)
        {
          u.followings.add(followeeId);
        }
      if(u.uid==followeeId)
       {
          u.followers.add(followerId);
       }
    }
  System.out.println();
 }
public void unfollow(int followerId, int followeeId)
 {
  for(Users u:users)
    {
      if(u.uid==followerId)
        {
          u.followings.remove(u.followings.indexOf(followeeId));
        }
      if(u.uid==followeeId)
       {
          u.followers.remove(u.followers.indexOf(followerId));
       }
    }
 }
public void deletePost(int uid,int pid)
 {
  for(Users u:users)
    {
      if(u.uid==uid)
        {
          u.pidLst.remove(pid);
          u.post.remove(pid);
          break;
        }
    }
 }
@SuppressWarnings("unchecked")
public void getNewsFeedPaginated(int uid,int pageNumber)
 {
  int length=3;
  int a=0;
  int b=length;
  while(pageNumber-- > 0)
 {
  for(Users u:users)
    {
       if(u.uid==uid)
         {
          ArrayList<String>temp=(ArrayList<String>)u.post.clone();
          Collections.reverse(temp);
          for(int i=a;i<b;i++)
           {  
              if(i<temp.size())
              System.out.println(" self posts "+temp.get(i));
              else
              break;
           }
           for(int person : u.followings)
            {
              for(Users z:users) 
                 {
                  if(z.uid==person)
                    {
                    temp=(ArrayList<String>)z.post.clone();
                    Collections.reverse(temp);
                   
                         for(int i=a;i<b;i++)
                            {  
                             if(i<temp.size())
                               System.out.println(" UserId :"+z.uid +" posts "+temp.get(i));
                             else
                               break;
                            }
                    }
                 }
            }
         }
    }
    a=b;
    b=b+length;
   }
 }
public void view(int uid)
{
for(Users u:users)
{
if(u.uid==uid)
 {
  System.out.println(u.followers);
  System.out.println(u.followings);
  break;
 }
}
}



}