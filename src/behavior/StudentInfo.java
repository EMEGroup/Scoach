
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
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ernesto
 */
public class StudentInfo extends GeneralBehavior {

    public static final String HELPTEXT = " student             Get information about a student\n"
            + "--nick                           search by contestant's nickname on OJ\n"
            + "--user                           search by contestant's username\n"
            + "--name                            search by any name/last name\n"
            + "--add                             add contestant \n"
            + "                                 (args.: username, contestant'sName, contestant'sLastName,"
            + "                                         Contestan'sBirthDay (1/dec/2015),  )  "
            + "--rm                              remove contestant with his username"
            + "For more info about a specific command, run help <command>.";
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////---add NYI
    
    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) {
        Map<String, String> responseProperties;
        responseProperties = new HashMap<>();
        responseProperties.put("text", HELPTEXT);

        BD basedato = new BD();
       

        List<String> students = new ArrayList<>();

        try {

            //----------------------------------------------------------------------------
            if (requestProperties.get("nick") != null) {
                for (String s : requestProperties.get("nick")) {
                    students.add(s);
                }
                Map<String, List<String>> StudentData = new HashMap<>();
                for (String s : students) {
                    for (ArrayList<String> datosBD : basedato.getStudent(s, "nick")) {
                        StudentData.put(s, datosBD);
                    }

                }
                // Converting to string for Printing
                responseProperties.put("text", makeReport(StudentData));
                return responseProperties;

            }
            //------------------------------------------------------------------------------------------
            if (requestProperties.get("name") != null) {
                //Taking out arguments of name, all the names
                for (String s : requestProperties.get("name")) {
                    students.add(s);
                }

                Map<String, List<String>> StudentData = new HashMap<>();

                for (String s : students) {
                    for (ArrayList<String> datosBD : basedato.getStudent(s, "name")) {
                        StudentData.put(s, datosBD);
                    }

                }
                responseProperties.put("text", makeReport(StudentData));
                return responseProperties;

            }
            //------------------------------------------------------------------------------------------
            if (requestProperties.get("user") != null) {
                for (String s : requestProperties.get("user")) {
                    students.add(s);
                }
                Map<String, List<String>> StudentData = new HashMap<>();

                for (String s : students) {
                    for (ArrayList<String> datosBD : basedato.getStudent(s, "user")) {
                        StudentData.put(s, datosBD);
                    }

                }
                // Converting to string for Printing
                responseProperties.put("text", makeReport(StudentData));
                return responseProperties;

            }

            //------------------------------------------------------------------------------------------
            if (requestProperties.get("group") != null) 
            {
                ArrayList<String> Groups = new ArrayList<>();
                String report = "";

                for (String s : requestProperties.get("group")) {
                    Groups.add(s);
                }
                Map<String, List<String>> StudentData = new HashMap<>();
                //ArrayList<String> miembros = new ArrayList<>();

                for (String gName : Groups) 
                {
                    report += gName + "\n";
                    for (String SingleStudent : basedato.getStudentsInGroup(gName)) {
                        //Since by id there is only one student, there's no fear of missing more answers
                        ArrayList<ArrayList<String>> datosBD = basedato.getStudent(SingleStudent, "user");
                        for (ArrayList<String> als : datosBD) {
                            StudentData.put(SingleStudent, als);
                        }

                    }
                    report += makeReport(StudentData);
                    report += "\n";

                }
                // Converting to string for Printing
                responseProperties.put("text", report);
                return responseProperties;
            }
            
                            //----------------------------------------------------------------------------
                if (requestProperties.get("add") != null) 
                {
                    
                    ArrayList<String> newStudent = new  ArrayList<>();
                
                    for (String s : requestProperties.get("add")) 
                    {
                        newStudent.add(s);
                    }
                    if(newStudent.size()<4)
                    {
                        responseProperties.put("text", "No sufficient arguments!\n");
                        return responseProperties;
                    }
                    if(basedato.studentExists(newStudent.get(0)))
                    {
                        responseProperties.put("text", "Student already exists!\n");
                        return responseProperties;
                        
                    }
                    basedato.addStudent(newStudent);
                
                    responseProperties.put("text", "Student added!\n");
                        return responseProperties;

                }
                //----------------------------------------------------------------------------
                if (requestProperties.get("rm") != null) 
                {
                    String  delStudent =requestProperties.get("rm").get(0);
                    
                    if(basedato.studentExists(delStudent))
                    {
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

        responseProperties.put("text", "Nobody found");

        return responseProperties;

    }

    public String makeReport(Map<String, List<String>> StudentData) {
        String respuesta = "```   username   | Name  | Last Name | Birthday | Sign up Date |   type   | Coach's Name | Coach's lastN\n";
        Set<Map.Entry<String, List<String>>> entrySet = StudentData.entrySet();
        for (Map.Entry<String, List<String>> x : entrySet) {
            //respuesta += x.getKey();
            for (String s : x.getValue()) {
                respuesta += "  | " + s;
            }
            respuesta += "\n```";
        }
        return respuesta;
    }

}
