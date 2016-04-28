package com.johdev.video.common;

import java.net.URLConnection;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {
  public Util(){}
  
  public static URLConnection addHttpHeader(URLConnection con){
    try{
      //setting http header value to avoid http 403 error.
      con.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
      con.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      con.addRequestProperty("Accept-Encoding","gzip, deflate");
      con.addRequestProperty("Cookie", "h_guest=4373832685702b7a49b7f3; __auc=9a735d66153e29d5f58d5f2ca62; h_id=xxx%40gmail.com; __asc=4eaa651d153e7815da67c3ae020; player_link=OK");
      con.addRequestProperty("Connection", "keep-alive");
    } catch(Exception e){
      e.printStackTrace();
    }
    
    return con;
  }
  
  public static String getDateStr(){
    SimpleDateFormat fmt = new SimpleDateFormat("MMdd");
    
    Calendar cal = Calendar.getInstance();
    return fmt.format(cal.getTime());        
  }
  
  public static String getFullDateStr(){
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    Calendar cal = Calendar.getInstance();
    return fmt.format(cal.getTime());        
  }
  
  public static int getDayNum(){
    Calendar cal = Calendar.getInstance();
    
    return cal.get(Calendar.DAY_OF_WEEK); 
  }
  
  public static String getKrDay(){
    Locale locale = new Locale("ko", "KR");
    
    DateFormatSymbols dfs = new DateFormatSymbols(locale);
    String weekdays[] = dfs.getWeekdays();
    
    Calendar cal = Calendar.getInstance();
    
    return weekdays[cal.get(Calendar.DAY_OF_WEEK)]; 
  }
  
  public static void main(String args[]){
    System.out.println(Util.getFullDateStr());
  }
}
