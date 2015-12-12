/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behavior;

import Misc.BD;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ernesto
 */
public class Group extends GeneralBehavior {

    public static final String HELPTEXT = " group             generate groups to refer to multiple students\n";

    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) {
        Map<String, String> responseProperties;
        responseProperties = new HashMap<>();
        responseProperties.put("text", HELPTEXT);

        String answer = "Grouping\n";

        String studNotEx = "";
        boolean NotFM = false;

        //String studAdd = "";
        ArrayList<String> MyStudents = new ArrayList<>();

        try {
            BD basedato = new BD();

            
            Iterator it = requestProperties.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry) it.next();

                it.remove(); // avoids a ConcurrentModificationException

                String group = (String) pair.getKey();
                group = group.toLowerCase();
                List<String> students = (List<String>) pair.getValue();
                 //answer += "\nGroup: \"" + group + "\" - Students:" + students+ " \n";

                if (!basedato.GroupExists(group)) {
                    basedato.createGroup(group);
                    answer += "\n    Group \"" + group + "\" was created!.\n";
                } else {
                    answer += "\n    Adding studdents to Group \"" + group + "\"\n";
                }
                answer += "\nGroup: \"" + group + "\" - Students:" + students + " \n";

                //Looking for the students
                studNotEx = "The students :\n";
                for (String student : students) {
                    //answer += "\nGroup: \"" + group + "\" - Students:" + students+ " \n";
//                    answer += "---" + student + "---";
                    if (!basedato.studentExists(student)) {
                        NotFM = true;
                        studNotEx += student + "\n";
                    } else {
                        MyStudents.add(student);
                    }
                }
                studNotEx += " Were Not Found";
                
                answer += "\nThe students found : "+ MyStudents + "\n";
                List<String> FinalStudents = basedato.filterStudentsGroup(group, MyStudents);
                answer += "Students not found in group " + Arrays.asList(FinalStudents) + "\n";

                basedato.insertGroupStudents(group, FinalStudents);
                

            }
            //answer += "\nAll Done!\n";
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            System.out.println("Error!!!! \n" + sw.toString()+ "Error!!!! \n" );

        }
        if (NotFM) {
            answer += studNotEx;
        }
        answer += "\nAll Done!\n";

        responseProperties.put("text", answer);

        return responseProperties;

    }
  
}
