package Misc;

import behavior.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class Commands {

    public void help(Map<String, List<String>> requestProperties) {
        Help _help = new Help();
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();

        Map<String, String> result = _help.Run(requestProperties);

        talking.stopThread();

        String text = result.get("text");
        requestProperties.put("text", Arrays.asList(new String[]{text}));
        System.out.println(text);
        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void echo(Map<String, List<String>> requestProperties) {
        Echo _echo = new Echo();
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();

        Map<String, String> result = _echo.Run(requestProperties);

        talking.stopThread();

        String text = result.get("text");
        requestProperties.put("text", Arrays.asList(new String[]{text}));

        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void submissions(Map<String, List<String>> requestProperties) {
        Submissions _submissions = new Submissions();
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();

        Map<String, String> result = null;

        try {
            result = _submissions.Run(GeneralStuff._getArguments(requestProperties));
        } catch (IOException ex) {
            talking.notifyError();
        } finally {
            talking.stopThread();
        }

        String text = result.get("text");
        requestProperties.put("text", Arrays.asList(new String[]{text}));
        System.out.println(text);
        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void studentInfo(Map<String, List<String>> requestProperties) {
        StudentInfo _studentInfo = new StudentInfo();
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();

        Map<String, String> result = null;

        result = _studentInfo.Run(GeneralStuff._getArguments(requestProperties));

        talking.stopThread();

        String text = "```" + result.get("text") + "```";
        requestProperties.put("text", Arrays.asList(new String[]{text}));
        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void group(Map<String, List<String>> requestProperties) {
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();
        
        Group _group = new Group();
        Map<String, String> result = null;
        
        try {
            result = _group.Run(GeneralStuff._getArguments(requestProperties));
            
        } catch (Exception ex) {
             StringWriter str = new StringWriter();
             PrintWriter writer = new PrintWriter(str);
             ex.printStackTrace(writer);
             System.out.println(str.getBuffer().toString());
            talking.notifyError();
            
        } finally {
            talking.stopThread();
        }

        talking.stopThread();
        String text =  result.get("text") ;
        requestProperties.put("text", Arrays.asList(new String[]{text}));
        //System.out.println ("----\n " + requestProperties.get("text").get(0)+ "----\n ");
                
        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void compare(Map<String, List<String>> requestProperties) {
        Compare _compare = new Compare();
        UnitofInteraction talking = new UnitofInteraction(requestProperties);
        talking.startThread();

        Map<String, String> result = null;

        try {
            result = _compare.Run(GeneralStuff._getArguments(requestProperties));
        } catch (IOException ex) {
            talking.notifyError();
        } finally {
            talking.stopThread();
        }

        String text = result.get("text");
        requestProperties.put("text", Arrays.asList(new String[]{text}));
        System.out.println(text);

        try {
            GeneralStuff._sendMessage(GeneralStuff._forgeMessage(requestProperties));
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public void recommendations(Map<String, List<String>> requestProperties){
		Recommendations _recommendations = new Recommendations();
		UnitofInteraction talking = new UnitofInteraction(requestProperties);
		talking.startThread();
		
		// Add the "--groupname" preffix to the given command text so the
		// _getArguments method puts the groupname under the "--groupname" key.
		
		if( requestProperties.get("text") != null ){
			if( requestProperties.get("text").get(0) != null ){
				
				requestProperties.get("text").set(0,
					"--groupname " + requestProperties.get("text").get(0) );
			}
		}
		
		Map<String, String> result = null;
                
		try {
			result = _recommendations.Run( GeneralStuff._getArguments(requestProperties) );
		} catch (MessagingException ex) {
			result.put("text", result.get("text") + "`" + ex.getMessage() + "`");
			talking.notifyError();
		} catch (Exception ex) {
			talking.notifyError();
		} finally {
			talking.stopThread();
		}
		
		String text = result.get("text");
		requestProperties.put("text", Arrays.asList(new String[]{text}));
		System.out.println(text);
		try {
			GeneralStuff._sendMessage( GeneralStuff._forgeMessage(requestProperties) );
		} catch (IOException ex) {
			Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
		}
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
			this.instance.killThread();
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
