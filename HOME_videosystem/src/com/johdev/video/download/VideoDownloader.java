package com.johdev.video.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;

import com.johdev.video.common.Util;

public class VideoDownloader {
  public VideoDownloader(){}  
   
  public boolean downloadFile(String fileUrl, String localFileNm){
    boolean isSuccess = true;
    
    URL url = null;
    URLConnection con = null;
        
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    
    try {
        url = new URL(fileUrl);
        con = url.openConnection();
        
        Util.addHttpHeader(con);
          
        File file = new File(localFileNm);
                
        bis = new BufferedInputStream(
                con.getInputStream());
        bos = new BufferedOutputStream(
                new FileOutputStream(file));
        
        int i;
        
        while ((i = bis.read()) != -1) {
            bos.write(i);            
        }       
    } catch (MalformedInputException malformedInputException) {
        malformedInputException.printStackTrace();
    } catch (IOException ioException) {
        ioException.printStackTrace();
    } catch(Exception e){
      isSuccess = false;
    }finally{
      try {
        bos.flush();
        bis.close();
        bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return isSuccess;
  }
  
  public static void main(String[] args) throws Exception {
    //String url = "http://vod30.dabdate.com/video2/taeyang0331-640.mp4";
    
    VideoDownloader downloader = new VideoDownloader();
    downloader.downloadFile("http://vod30.dabdate.com/video2/news0406-640.mp4","/Users/joh/Downloads/test2.mp4");
  }
}
