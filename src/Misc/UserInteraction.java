package Misc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInteraction implements Runnable{
	private final String startMessage = "Your command has been received, keep calm ...";
    private final String meanwhileMessage = "Please wait ...";
    private final String errorMessage = "An error has occourred.";
    private String canal;
    private boolean alive = true;
    private Map<String, List<String>> requestPropertiesGlobal;
    private boolean error = false;
    
    public void prepareInfo(Map<String, List<String>> requestProperties) {
		
        boolean private_group;
        requestPropertiesGlobal = GeneralStuff._copyMap(requestProperties);
        if(requestProperties.get("channel_name") != null){
            private_group = requestProperties.get("channel_name").get(0).equals("privategroup");
        }
        else{
            private_group = false;
        }
        
        if(requestProperties.get("channel_id") != null){
           canal = requestProperties.get("channel_id").get(0);
			try {
				canal = GeneralStuff.getChannelInfo(canal, private_group);
			} catch (IOException ex) {
				Logger.getLogger(UserInteraction.class.getName()).log(Level.SEVERE, null, ex);
			}
        }
        else{
            canal = GeneralStuff.DEFAULTCHANNEL;
        }
		
		requestPropertiesGlobal.put(
			"text", Arrays.asList(new String[]{startMessage}) );
		
		try {
			GeneralStuff._sendMessage(
				GeneralStuff._forgeMessage(requestPropertiesGlobal));
		} catch (IOException ex) {
			Logger.getLogger(UserInteraction.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    @Override
    public void run(){
        long LastMessage = System.currentTimeMillis();
        while(alive && !(Thread.interrupted())){
			if(!alive){
				break;
			}
			if(System.currentTimeMillis() - LastMessage > 5000 && alive && !(Thread.interrupted())){
				if(!alive){
					break;
				}
				LastMessage = System.currentTimeMillis();
				requestPropertiesGlobal.put("text",Arrays.asList(new String[] {meanwhileMessage}));
				System.out.println(meanwhileMessage);
				 try {
					 GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestPropertiesGlobal));
				 } catch (IOException ex) {
					 Logger.getLogger(UserInteraction.class.getName()).log(Level.SEVERE, null, ex);
				 }
			}
        }
        if(error){
               requestPropertiesGlobal.put("text",Arrays.asList(new String[] {errorMessage}));
               
			try {
				GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestPropertiesGlobal));
			} catch (IOException ex) {
				Logger.getLogger(UserInteraction.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		// Here lays the tomb of an unnecesary return statement, thanks Java.
    }
    
    public void killThread(){
        alive = false;
    }
    
    public void notifyError(){
        error = true;
    }
    
}
