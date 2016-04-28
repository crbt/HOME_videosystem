package com.johdev.video.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {   
    static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);
    
    public static ConfigUtil cache = null;
    public static Properties prop;
    
    public ConfigUtil(){
        prop = new Properties();    
        
        log.debug("ConfigUtil is loaded...");
        
        /*
        RuntimeMXBean rtmxbean = ManagementFactory.getRuntimeMXBean();
        
        for(String arg : rtmxbean.getInputArguments()){
          System.out.println(arg);
        }
        */        
            
        final String CONFIGURATION_HOME =  System.getProperty("user.home");
        final String appConfigFile = CONFIGURATION_HOME + "/config/video.properties";
        
        log.debug("ConfigUtil - properties location : " + appConfigFile);
        
        File file = new File(appConfigFile);
        InputStream inputStream = null;
        
        try {
            inputStream = new FileInputStream(file);
            
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            log.error("ConfigUtil - " + e1.getMessage());
        }
    }
    
    public Properties getProp(){
        return prop;
    }
    
    public static synchronized ConfigUtil getInstance(){
        if(cache == null){
            cache = new ConfigUtil();
        }
        return cache;
    }
    
    public static void main(String args[]){
      ConfigUtil c = new ConfigUtil();
    }
}
