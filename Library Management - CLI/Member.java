import java.util.*;
public class Member
{
static int inc=0;
int id;
String name;
String pass;
int age;
char gender;
String jdate;
ArrayList<Integer>issued=new ArrayList<>();

public Member(String name,String pass,int age,char gen,String jdate)
 {
  id=++inc;
  this.name=name;
  this.pass=pass;
  this.age=age;
  gender=gen;
  this.jdate=jdate;
 }
public Member()
 {}
}