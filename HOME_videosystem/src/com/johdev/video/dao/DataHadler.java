package com.johdev.video.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johdev.video.common.ConfigUtil;
import com.johdev.video.common.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
 
public class DataHadler {  
  static final Logger log = LoggerFactory.getLogger(DataHadler.class);
  Properties prop = ConfigUtil.getInstance().getProp();
  
  String dbUrl = prop.getProperty("db.url");
  int dbPort = Integer.parseInt(prop.getProperty("port"));
  String dbName = prop.getProperty("db.name");
  
  MongoClient mongo = null;
  public DataHadler(){
    try {
      mongo = new MongoClient(dbUrl, dbPort);      
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
    
  public void insertIntoVideoData(String tag, String fileName, String dspName, String dateTag){
    try {      
      DB db = mongo.getDB(dbName);
      
      DBCollection table = db.getCollection(prop.getProperty("db.table.video"));
      
      BasicDBObject document = new BasicDBObject();
           
      document.put("tag", tag);
      document.put("fileName", fileName);
      document.put("dspName", dspName);
      document.put("dateTag", dateTag);
      document.put("updDate", Util.getFullDateStr());
      
      log.info("Video data insert query - " + document.toString());
      
      table.insert(document);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void insertIntoJoblist(String tag, String dspName, String jobTiming){
    try {      
      DB db = mongo.getDB(dbName);
      
      DBCollection table = db.getCollection(prop.getProperty("db.table.joblist"));
      
      BasicDBObject document = new BasicDBObject();
           
      document.put("tag", tag);
      document.put("dspName", dspName);
      document.put("jobTiming", jobTiming);
      document.put("status", "A");
      
      log.info("Job queue insert query - " + document.toString());
      
      table.insert(document);      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public List<DBObject> findJoblist(){
    List<DBObject> jobList = new ArrayList<DBObject>();
    
    try {            
      DB db = mongo.getDB(dbName);
      
      DBCollection table = db.getCollection(prop.getProperty("db.table.joblist"));
      
      DBCursor cursor = table.find();
      
      log.info("Job Queue List data ...");
      
      while (cursor.hasNext()) {
        DBObject data = cursor.next();
        
        jobList.add(data);
        
        log.info(data.toString());
      }
    } catch(Exception e){
      e.printStackTrace();
    }
    
    return jobList;
  }
  
  public static void main(String args[]){
    DataHadler handler = new DataHadler();
    //handler.insertIntoVideoData("tae", "taeyang0331-640.mp4", "태양의 후예 - 0331", "0331");
    //handler.insertIntoJoblist("news", "KBS 9시 뉴스", "0123456");
    handler.findJoblist();
  }
}
