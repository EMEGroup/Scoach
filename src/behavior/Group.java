/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behavior;

import Misc.BD;
import static behavior.StudentInfo.HELPTEXT;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            Map<String, ArrayList<String>> map = _getArgumentsGroup(requestProperties);
            if (map == null) {
                responseProperties.put("text", "Request Mistyped");
                return responseProperties;
            }
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry) it.next();

                it.remove(); // avoids a ConcurrentModificationException

                String group = (String) pair.getKey();
                group = group.toLowerCase();
                ArrayList<String> students = (ArrayList<String>) pair.getValue();
 //answer += "\nGroup: \"" + group + "\" - Students:" + students+ " \n";

                if (!basedato.GroupExists(group)) {
                    basedato.createGroup(group);
                    answer += "\n    Group \"" + group + "\" was created!.\n";
                } else {
                    answer += "\n    Adding studdents to Group \"" + group + "\"\n";
                }
                answer += "\nGroup: \"" + group + "\" - Students:" + students + " \n";

//                answer += students.size();
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

                answer += "\nInserting\n";

                basedato.insertGroupStudents(group, MyStudents);

                answer += "\n";
            }

            answer += "\nAll Done!\n";
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            System.out.println(sw.toString());

        }
        if (NotFM) {
            answer += studNotEx;
        }
        answer += "\nDone!\n";

        responseProperties.put("text", answer);
        System.out.println(answer);

        return responseProperties;

    }

    public Map<String, ArrayList<String>> _getArgumentsGroup(Map<String, List<String>> requestProperties) {
        Map<String, ArrayList<String>> args = new HashMap<String, ArrayList<String>>();

        String text = "";

        if (requestProperties.get("text") != null) {
            if (requestProperties.get("text").get(0) != null) {

                text = requestProperties.get("text").get(0);
            }
        }
        String group = text.split(" ")[0];

        Pattern argPat = Pattern.compile(group + ".*");

        Matcher argsMatcher = argPat.matcher(text);

        String argument, key;
        String[] values;

        argsMatcher.find();

        argument = argsMatcher.group();
        argument = argument.trim();

        if (!argument.contains(" ")) {
            return null;
        }

        key = argument.substring(0, argument.indexOf(" "));
        values = argument.replaceFirst(key + "[ ]+", "").split("([ ]*[,]+[ ]*)+");

        args.put(group, new ArrayList<String>(Arrays.asList(values)));

        return args;
    }
}
