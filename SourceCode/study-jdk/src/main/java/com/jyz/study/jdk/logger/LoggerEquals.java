package com.jyz.study.jdk.logger;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *  http://lavasoft.blog.51cto.com/62575/184492
 *	@author JoyoungZhang@gmail.com
 */
public class LoggerEquals {

	 public static void main(String[] args) throws SecurityException, IOException { 
         Logger log = Logger.getLogger("lavasoft"); 
         log.setLevel(Level.INFO); 
         Logger log1 = Logger.getLogger("lavasoft"); 
         System.out.println(log==log1);     //true 
         Logger log2 = Logger.getLogger("lavasoft.blog"); 
         System.out.println(log1==log2);     //true 
//         log2.setLevel(Level.WARNING); 

         ConsoleHandler consoleHandler = new ConsoleHandler(); 
         consoleHandler.setLevel(Level.ALL); 
         log.addHandler(consoleHandler); 
         FileHandler fileHandler = new FileHandler("test.log"); 
         fileHandler.setLevel(Level.INFO); 
//         fileHandler.setFormatter(new MyLogHander()); 
         log.addHandler(fileHandler); 
         
         log.info("aaa"); 
         log2.info("bbb"); 
         log2.fine("fine");
         tt(log2);
	 } 
	 
	 static void tt(Logger log) throws SecurityException, IOException{
		 log.info("ccc"); 
	 }
	 
}

class MyLogHander extends Formatter { 
    @Override 
    public String format(LogRecord record) { 
            return record.getLevel() + ":" + record.getMessage()+"\n"; 
    } 
}
