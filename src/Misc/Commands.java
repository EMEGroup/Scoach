package Misc;

import behavior.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Commands {
	public void help(Map<String, List<String>> requestProperties){
		Help _help = new Help();
                UnitofInteraction talking = new UnitofInteraction(requestProperties);
		talking.startThread();
                
		Map<String, String> result = _help.Run(requestProperties);
		
		if(result == null){
                    talking.stopThread();
                    return;
                }
                
                talking.stopThread();
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		try {
			GeneralStuff._sendMessage( GeneralStuff._forgeMessage(requestProperties) );
		} catch (IOException ex) {
			Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void echo(Map<String, List<String>> requestProperties){
		Echo _echo = new Echo();
                UnitofInteraction talking = new UnitofInteraction(requestProperties);
		talking.startThread();
		
		Map<String, String> result = _echo.Run(requestProperties);
		
		if(result == null){
                    talking.stopThread();
                    return;
                }
                
                talking.stopThread();
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		try {
			GeneralStuff._sendMessage( GeneralStuff._forgeMessage(requestProperties) );
		} catch (IOException ex) {
			Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void submissions(Map<String, List<String>> requestProperties){
		Submissions _submissions = new Submissions();
                UnitofInteraction talking = new UnitofInteraction(requestProperties);
		talking.startThread();
		
		Map<String, String> result = null;
		
		try {
			result = _submissions.Run( GeneralStuff._getArguments(requestProperties) );
		} catch (IOException ex) {
			Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if(result == null){
                    talking.stopThread();
                    return;
                }
			
		talking.stopThread();
                
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		try {
			GeneralStuff._sendMessage( GeneralStuff._forgeMessage(requestProperties) );
		} catch (IOException ex) {
			Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void studentInfo(Map<String, List<String>> requestProperties) throws IOException, InterruptedException{
		StudentInfo _studentInfo= new StudentInfo();
                UnitofInteraction talking = new UnitofInteraction(requestProperties);
		talking.startThread();
                
		Map<String, String> result = 
			_studentInfo.Run( GeneralStuff._getArguments(requestProperties));		
		
		if(result == null) {
                    talking.stopThread();
                    return;
                }
		talking.stopThread();
                
		String text= result.get("text");
		System.out.println(text);
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		
		GeneralStuff._sendMessage( GeneralStuff._forgeMessage(requestProperties) );
	}	
        
        private class UnitofInteraction{
            Thread thread;
            UserInteraction instance;
            
            public UnitofInteraction(Map<String, List<String>> requestProperties){
                this.instance = new UserInteraction();
                this.instance.prepareInfo(requestProperties);
                this.thread = new Thread(this.instance);
            }
            
            public void startThread(){
                this.thread.start();
            }
            
            public void notifyError(){
                this.instance.notifyError();
            }
            
            public void stopThread(){
                this.instance.killThread();
                this.thread.interrupt();
                try{
                  this.thread.join();  
                }catch(Exception e){
                  
                }
            }
            
        }
}
