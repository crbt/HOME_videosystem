package com.johdev.video.common;

import java.util.Date;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
 
public class SimpleMongo {
       
        MongoClient mongoClient = null;
        DB db=null;
       
        public void mongoTest(String ip,int port,String dbname) throws Exception{
              
               mongoClient = new MongoClient(new ServerAddress(ip,port));
               db = mongoClient.getDB(dbname);
              
               DBCollection userTable = db.getCollection("users");
               BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));
 
               userTable.insert(doc);
        }
        
        private void selectData(String ip, int port, String dbname) throws Exception{
          /**** Connect to MongoDB ****/
          // Since 2.10.0, uses MongoClient
          MongoClient mongo = new MongoClient("localhost", 27017);

          /**** Get database ****/
          // if database doesn't exists, MongoDB will create it for you
          DB db = mongo.getDB("testdb");

          /**** Get collection / table from 'testdb' ****/
          // if collection doesn't exists, MongoDB will create it for you
          DBCollection table = db.getCollection("user");

          /**** Insert ****/
          // create a document to store key and value
          BasicDBObject document = new BasicDBObject();
          document.put("name", "mkyong");
          document.put("age", 30);
          document.put("createdDate", new Date());
          table.insert(document);

          /**** Find and display ****/
          BasicDBObject searchQuery = new BasicDBObject();
          searchQuery.put("name", "mkyong");

          DBCursor cursor = table.find(searchQuery);

          while (cursor.hasNext()) {
              System.out.println(cursor.next());
          }
        }
       
        public static void main(String args[]) throws Exception{
          System.out.println("MongoDB start...");
          
          SimpleMongo testRunner = new SimpleMongo();
          
          //testRunner.mongoTest("localhost", 27017, "users");          
          testRunner.selectData("localhost", 27017, "db");
          
          System.out.println("MongoDB end...");
        }
}