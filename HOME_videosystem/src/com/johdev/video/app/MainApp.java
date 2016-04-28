package com.johdev.video.app;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johdev.video.common.ConfigUtil;
import com.johdev.video.common.Util;
import com.johdev.video.dao.DataHadler;
import com.johdev.video.download.VideoDownloader;
import com.mongodb.DBObject;

public class MainApp {
  public MainApp(){}
  
  static final Logger log = LoggerFactory.getLogger(MainApp.class);
  Properties prop = ConfigUtil.getInstance().getProp();
  
  private void download(){
    //get Today month + date and day to select job list
    String jobDate = Util.getDateStr();
    int jobDay = Util.getDayNum();
    
    log.info("Today is " + jobDate + ", job day is " + jobDay);
    
    //get the job queue for selecting download list        
    DataHadler handler = new DataHadler();
    
    List<DBObject> jobQueue = handler.findJoblist();
    
    VideoDownloader downloader = new VideoDownloader();
    
    String videoUrl = prop.getProperty("video.url");
    String videoDefinition = prop.getProperty("video.definition");
    String videoFileExt = prop.getProperty("video.file.extention");
    String downloadLocation = prop.getProperty("video.download.location");
    
    log.info("Download start...");
    
    long startTime = System.currentTimeMillis();
    
    for(DBObject data : jobQueue){
      if(data.get("status") != null && data.get("status").toString().equalsIgnoreCase("A") 
          && data.get("jobTiming").toString().contains(jobDay+"")){  //if status us Active
        String localFileName = data.get("tag") +  jobDate + videoDefinition + videoFileExt;
        String downloadFileURI = videoUrl + localFileName;
        
        log.info("Download file - " + localFileName + ", Full http URL for download - " + downloadFileURI);
        
        if(downloader.downloadFile(downloadFileURI, downloadLocation + localFileName)){ //if download success
          handler.insertIntoVideoData(data.get("tag").toString(), localFileName, data.get("dspName") + "-" +  jobDate + " " + Util.getKrDay(), jobDate);
        }
      }
    }
    
    log.info("Download elapsed time - " + ((System.currentTimeMillis() - startTime) / 1000 ) + "secs");
  }
      
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    MainApp app = new MainApp();
    app.download();
  }
}
