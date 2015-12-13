
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
import java.util.Arrays;
import java.util.Set;

/**
 * @author Ernesto
 */
public class StudentInfo extends GeneralBehavior {

    public static final String HELPTEXT = " student             Get information about a student\n"
            + "--nick                           search by contestant's nickname on OJ\n"
            + "--user                           search by contestant's username\n"
            + "--name                            search by any name/last name\n"
            + "--add                             add contestant \n"
            + "                                 (args.: username, contestant'sName, contestant'sLastName,\n"
            + "                                         Contestan'sBirthDay (1/dec/2015),  ) \n "
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
            if (requestProperties.get("--nick") != null) {
                
                
                for (String s : requestProperties.get("--nick")) {
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
            if (requestProperties.get("--name") != null) {
                //Taking out arguments of name, all the names
                for (String s : requestProperties.get("--name")) {
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
            if (requestProperties.get("--user") != null) {
                //System.out.println("\n nick \n" );
                for (String s : requestProperties.get("--user")) {
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
            if (requestProperties.get("--group") != null) {
                //System.out.println("\n nick \n" );
                ArrayList<String> Groups = new ArrayList<>();
                String report = "";

                for (String s : requestProperties.get("--group")) {
                    Groups.add(s);
                }
                Map<String, List<String>> StudentData = new HashMap<>();
                //ArrayList<String> miembros = new ArrayList<>();

                for (String gName : Groups) {
                    report += gName + "\n";
                    for (String SingleStudent : basedato.getStudentsInGroup(gName)) {
                        //Since by id there is only one student, there's no fear of missing more answers
                        ArrayList<ArrayList<String>> datosBD = basedato.getStudent(SingleStudent, "user");
                        for (ArrayList<String> als : datosBD) {
                            StudentData.put(SingleStudent, als);
                        }

                    }
                    report += makeReport(StudentData);
                    //report += "\n";

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

    public String makeReport(Map<String, List<String>> StudentData) 
    {
        
        String respuesta = "```";
        String [] headers ={"Username","Name" , "Last Name" , "Birthday" , "Sign up Date" , "Type" , "Coach's Name" , "Coach's lastN"};
        //Object [] firstVals = StudentData.keyet().toArray();
        //System.out.println("\n ye 1 \n" );
        ArrayList<List<String>> table = new ArrayList<>(StudentData.values());

        //List<String>[] table = StudentData.values().toArray(new List<String>[]);
       
        int [] values = getSpaces(headers, table );
        
        Set<Map.Entry<String, List<String>>> entrySet = StudentData.entrySet();
        
        for(int i = 0 ; i< headers.length ; i++)
        {
            respuesta += headers[i];
            
            for(int j = headers[i].length() ; j <= values[i] ; j++ )
            {
                respuesta += " ";
            }
            if(i != headers.length-1)
            respuesta += " | ";
        }
        int total_length= respuesta.length();
        respuesta += "\n";
        for(int i = 0; i <= total_length ; i++)
        {
            respuesta += "-";
        }
        
        respuesta += "\n";
        
        for(Map.Entry<String, List<String>> x : entrySet) {
            //respuesta += x.getKey();
            int i = 0;
            for (String s : x.getValue()) {
                
                respuesta +=  s;
                for(int j = s.length() ; j <= values[i] ; j++ )
                {
                    respuesta += " ";
                }
                if(i != headers.length-1)
                     respuesta += " | ";
                i++;
            }
               respuesta += "\n";
            
        }
        respuesta += "\n```";
        
        
        
        return respuesta;
    }
    
    public int[] getSpaces(String[] headers, ArrayList<List<String>> values)
    {
        int[] spaces = new int[headers.length];
     
        Arrays.fill(spaces, 0);
        
        for(int i = 0 ; i < headers.length ; i++ )
        {
            
            if(headers[i].length() > spaces[i] )
                spaces[i] = headers[i].length(); 
            for (List<String> value : values) 
            {
                if(value.get(i).length() >  spaces[i] )
                {
                    spaces[i] = value.get(i).length();  
                }
            }
        }
        return spaces;
    }

}
