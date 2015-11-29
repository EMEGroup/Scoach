/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

    public class UserInteraction implements Runnable{
    private final String meanwhileMessage = "Please wait...";
    private final String errorMessage = "An error has occourred.";
    private final String doneMessage = "Finished.";
    private String canal;
    private boolean alive = true;
    private Map<String, List<String>> requestPropertiesGlobal;
    private boolean error = false;
    
    public void prepareInfo(Map<String, List<String>> requestProperties) {
        //Implementar la interaccion con el usuario en un hilo que va con el comando principal.
        boolean private_group;
        requestPropertiesGlobal = Commands._copyMap(requestProperties);
        if(requestProperties.get("channel_name") != null){
            private_group = requestProperties.get("channel_name").get(0).equals("privategroup");
        }
        else{
            private_group = false;
        }
        
        if(requestProperties.get("channel_id") != null){
           canal = requestProperties.get("channel_id").get(0);
           canal = GeneralStuff.getChannelInfo(canal, private_group);
        }
        else{
            canal = GeneralStuff.defaultChannel;
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
               Commands._sendMessage(Commands._forgeMessage(requestPropertiesGlobal));
               
           }
        }
        if(error){
               requestPropertiesGlobal.put("text",Arrays.asList(new String[] {errorMessage}));
               Commands._sendMessage(Commands._forgeMessage(requestPropertiesGlobal));
           }
        else{
                requestPropertiesGlobal.put("text",Arrays.asList(new String[] {doneMessage}));
                Commands._sendMessage(Commands._forgeMessage(requestPropertiesGlobal));
        }
        return;
    }
    
    
    public void killThread(){
        alive = false;
    }
    
    public void notifyError(){
        error = true;
    }
    
}
