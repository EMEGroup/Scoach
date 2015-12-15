
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behavior;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Misc.*;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ernesto
 */
public class StudentInfo extends GeneralBehavior {

    public static final String HELPTEXT = "``` student             Get information about a student\n"
            + "--nick                           search by contestant's nickname on OJ\n"
            + "--user                           search by contestant's username\n"
            + "                                  use with --judge to give a user a nick in OJ\n"
            + "                                   Ex: contestants --user user --judge OJ=nick"
            + "--name                            search by any name/last name\n"
            + "--add                             add contestant \n"
            + "                                 (args.: username, contestant'sName, contestant'sLastName,\n"
            + "                                         Contestan'sBirthDay (1/dec/2015),  ) \n "
            + "--rm                              remove contestant with his username"
            + "For more info about a specific command, run help <command>.```";

///////////////////////////////////////////////////////////////////////////////////////////////////////////---add NYI
    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) {
        Map<String, String> responseProperties;
        responseProperties = new HashMap<String, String>();
        responseProperties.put("text", HELPTEXT);

        List<String> options = new ArrayList<String>();
        options.add("--nick");
        options.add("--user");
        options.add("--name");
        
        BD basedato = new BD();

        List<String> students = new ArrayList<>();
        Map<String, Map<String, String>> StudentData = new HashMap<>();
        try {
            for(String op : options)
            {
                if (requestProperties.get(op) != null) 
                {
                    if(requestProperties.get("--judge")!= null)
                    {
                        responseProperties.put("text", manageJudge(requestProperties));
                        return responseProperties;
                    }
                    List<String> Data = requestProperties.get(op);
                    if(Data ==null)
                    {
                        responseProperties.put("text", "Tag not found!\n");
                        return responseProperties;
                        
                    }
                    for (String s : Data) {
                        students.add(s);
                    }

                    for (String s : students) {
                        StudentData.put(s, basedato.getStudent(s, op.replace('-', ' ').trim()));
                    }
                    // Converting to string for Printing
                    responseProperties.put("text", makeReport(StudentData));
                    return responseProperties;

                }
                
            }
            

            //------------------------------------------------------------------------------------------
            if (requestProperties.get("--group") != null) {
               
                ArrayList<String> Groups = new ArrayList<>();
                String report = "";

                for (String s : requestProperties.get("--group")) {
                    Groups.add(s);
                }
                
                for (String gName : Groups) {
                    report += gName + "\n";
                    for (String SingleStudent : basedato.getStudentsInGroup(gName)) {
                        Map<String, String> datosBD = basedato.getStudent(SingleStudent, "user");
                        StudentData.put(SingleStudent, datosBD);
                    }
                    report += makeReport(StudentData);

                }
                // Converting to string for Printing
                responseProperties.put("text", report);
                return responseProperties;
            }

            //----------------------------------------------------------------------------
            if (requestProperties.get("--add") != null) {
                //System.out.println("\n\n add\n\n");
                ArrayList<String> newStudent = new ArrayList<>();

                for (String s : requestProperties.get("--add")) {
                    newStudent.add(s);
                }
                if (newStudent.size() < 4) {
                    responseProperties.put("text", "No sufficient arguments!\n");
                    return responseProperties;
                }
                if (basedato.studentExists(newStudent.get(0))) {
                    responseProperties.put("text", "Student already exists!\n");
                    return responseProperties;

                }
                basedato.addStudent(newStudent);

                responseProperties.put("text", "Student added!\n");
                return responseProperties;

            }
            //----------------------------------------------------------------------------
            if (requestProperties.get("--rm") != null) {
                String delStudent = requestProperties.get("--rm").get(0);

                if (basedato.studentExists(delStudent)) {
                    basedato.rmStudent(delStudent);
                    responseProperties.put("text", "Student deleted!\n");
                    return responseProperties;
                }

                // Converting to string for Printing
                responseProperties.put("text", "Student not Found!\n");
                return responseProperties;

            }

        } catch (ClassNotFoundException | SQLException ex) {
            responseProperties.put("text", "Problem searching database.");
            return responseProperties;
        }

        responseProperties.put("text", HELPTEXT);

        return responseProperties;

    }

    public String makeReport(Map<String, Map<String, String>> StudentData) {

        String respuesta = "";
        String[] headers = {"Username", "Name", "Last Name", "Birthday", "E-mail", "Sign in Date", "Type", "Coach's Name", "Coach's lastN"};
        String[] keys = {"user", "Name", "LastName", "BirthDay", "email", "SignInD", "Type", "CName", "CLastName"};
        Collection<Map<String, String>> table = StudentData.values();

        int[] values = getSpaces(headers, table);

        Set<Map.Entry<String, Map<String, String>>> entrySet = StudentData.entrySet();

        for (int i = 0; i < headers.length; i++) {
            respuesta += headers[i];

            for (int j = headers[i].length(); j <= values[i]; j++) {
                respuesta += " ";
            }
            if (i != headers.length - 1) {
                respuesta += " | ";
            }
        }
        int total_length = respuesta.length();
        respuesta += "\n";
        for (int i = 0; i <= total_length; i++) {
            respuesta += "-";
        }

        respuesta += "\n";

        for (Map.Entry<String, Map<String, String>> x : entrySet) {
            //respuesta += x.getKey();
            for (int i = 0; i < keys.length; i++) {

                String val = x.getValue().get(keys[i]);
                System.out.println("i= " + i + " Key: " + keys[i] + " value :" + val);
                if (val == null) {
                    for (int j = 0; j <= values[i]; j++) {
                        respuesta += " ";
                    }
                    if (i != headers.length - 1) {
                        respuesta += " | ";
                    }
                    continue;
                }
                respuesta += val;

                for (int j = val.length(); j <= values[i]; j++) {
                    respuesta += " ";
                }
                if (i != headers.length - 1) {
                    respuesta += " | ";
                }
            }
            respuesta += "\n";

        }
        respuesta += "\n";

        return respuesta;
    }

    public int[] getSpaces(String[] headers, Collection<Map<String, String>> map) {
        String[] keys = {"user", "Name", "LastName", "BirthDay", "email", "SignInD", "Type", "CName", "CLastName"};
        int[] spaces = new int[headers.length];

        ArrayList<Map<String, String>> values = new ArrayList<>(map);
        Arrays.fill(spaces, 0);

        for (int i = 0; i < headers.length; i++) {

            if (headers[i].length() > spaces[i]) {
                spaces[i] = headers[i].length();
            }
            for (Map<String, String> value : values) {
                if (value.get(keys[i]) == null) {
                    continue;
                }

                if (value.get(keys[i]).length() > spaces[i]) {
                    spaces[i] = value.get(keys[i]).length();
                }
            }
        }
        return spaces;
    }
    
    public String manageJudge(Map<String, List<String>> requestProperties) throws SQLException, ClassNotFoundException {
        String answer="";
        
        BD dataBase = new BD();
        if(requestProperties.get("--user")!= null)
        {
            List<String> users = requestProperties.get("--user");
            if(users.size()!=1)
            {
                return "Must specify one username per command!";
            }
            
            if(dataBase.studentExists(users.get(0)))
            {
                List<String> args = requestProperties.get("--judge");
                for (String relation : args)
                {
                    String [] OJ_Student = relation.split("=");
                    if(dataBase.nickExists(users.get(0),OJ_Student[0]))
                    {
                    
                        dataBase.updateNickStudent(users.get(0),OJ_Student[0],OJ_Student[1]);
                        
                        answer += "Values altered for student " + users.get(0) + "!\n";
                    }
                    else
                    dataBase.insertNickStudent(users.get(0),OJ_Student[0],OJ_Student[1]);
                } 
                return answer + "Nickname added!";
            }
            
        }
        else return "Must specify username!";
        
        return answer;
    }

}
