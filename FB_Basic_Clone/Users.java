import java.util.*;

public class Users
{
static int Tid=0;
int uid;
ArrayList<Integer>pidLst;
ArrayList<String>post;
ArrayList<Integer>followers;
ArrayList<Integer>followings;
public Users()
 {
  Tid++;
  this.uid=Tid;
  this.pidLst=new ArrayList<>();
  this.followers=new ArrayList<>();
  this.followings=new ArrayList<>();
  this.post=new ArrayList<>();
 }
}