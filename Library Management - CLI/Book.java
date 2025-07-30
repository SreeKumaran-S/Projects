import java.util.*;
public class Book
{
static int inc=0;
int id;
String title;
String description;
String Author_name;
String date;
int edition;
int price;
int cnt;
 
public Book(String title,String des,String Aname,String date,int edition,int price,int cnt)
 { 
  
  id=++inc;
  this.title=title;
  description=des;
  Author_name=Aname;
  this.date=date;
  this.edition=edition;
  this.price=price;
  this.cnt=cnt;
 }
public Book()
 {}
}