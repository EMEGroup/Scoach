/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.sql.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ernesto
 */
public class BD {

    Connection con = null;
    Statement stmt = null;

    public void startConection() {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {

                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }

            con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                    "admin7wbaict", "Exf6tmuYJXWh");

        } catch (SQLException ex) {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<ArrayList<String>> getStudent(String est, String specify) {

        ArrayList<ArrayList<String>> resultSet = new ArrayList<>();

        String sql = "";
        if (specify.toLowerCase().compareTo("nick") == 0) {
            sql = "select Estudiante.username as user , Estudiante.nombre,Estudiante.apellido, Estudiante.fecha_nacimiento, Estudiante.fecha_ingreso,  Grado.tipo ,  Coach.Nombre as SNOMBRE, coach.apellido as SAPELLIDO \n"
                    + "from estudiante, grado, coach, Estudiante_Juez \n"
                    + "where  estudiante.id_estudiante  = Estudiante_Juez.id_estudiante AND\n"
                    + "Estudiante_Juez.nick = '" + est + "' AND  \n"
                    + "grado.id_grado = estudiante.grado AND estudiante.coach = coach.id_Coach ;";
        } else if (specify.toLowerCase().compareTo("name") == 0) {

            sql = "select Estudiante.username as user , Estudiante.nombre, Estudiante.apellido, Estudiante.fecha_nacimiento,\n"
                    + "Estudiante.fecha_ingreso,  Grado.tipo ,  \n"
                    + "Coach. Nombre as SNOMBRE, coach.apellido as SAPELLIDO\n"
                    + "from estudiante, grado, coach\n"
                    + "where \n"
                    + "(lower(estudiante.NOMBRE)= lower('" + est + "') OR\n"
                    + "lower(estudiante.APELLIDO)= lower('" + est + "') )AND \n"
                    + "grado.id_grado = estudiante.grado AND\n"
                    + "estudiante.coach = coach.id_Coach";

        } else if (specify.toLowerCase().compareTo("user") == 0) {
            sql = "select Estudiante.username as user , Estudiante.nombre,Estudiante.apellido, Estudiante.fecha_nacimiento, Estudiante.fecha_ingreso,  Grado.tipo ,  Coach. Nombre as SNOMBRE, coach.apellido as SAPELLIDO \n"
                    + "from estudiante, grado, coach, Estudiante_Juez \n"
                    + "where  estudiante.id_estudiante  = Estudiante_Juez.id_estudiante AND  \n"
                    + "grado.id_grado = estudiante.grado AND estudiante.coach = coach.id_coach AND\n"
                    + "estudiante.USERNAME = '" + est + "';";
        }

        try {
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ArrayList<String> Info = new ArrayList<>();
                Info.add(rs.getString("user"));
                Info.add(rs.getString("Nombre"));
                Info.add(rs.getString("Apellido"));
                Info.add(rs.getString("Fecha_nacimiento"));
                Info.add(rs.getString("Fecha_ingreso"));
                Info.add(rs.getString("Tipo"));
                Info.add(rs.getString("SNombre"));
                Info.add(rs.getString("SApellido"));
                resultSet.add(Info);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }
    public ArrayList<String> getStudentByAlias(String est)
    {
        ArrayList<String> Info = new ArrayList<>();

        String sql = "";
    
        try {
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

                 rs.next();
                
                Info.add(rs.getString("user"));
                Info.add(rs.getString("Nombre"));
                Info.add(rs.getString("Apellido"));
                Info.add(rs.getString("Fecha_nacimiento"));
                Info.add(rs.getString("Fecha_ingreso"));
                Info.add(rs.getString("Tipo"));
                Info.add(rs.getString("SNombre"));
                Info.add(rs.getString("SApellido"));

            rs.close();
            stmt.close();
        } catch (SQLException ex)
        {
            
        }
        return Info;
    }
    
    public void addStudent() {

    }

    public ArrayList<String> getStudentsInGroup(String group) 
    {
        ArrayList<String> tabla = new ArrayList<String>();

        String sql = "select Estudiante.username as user \n"
                + "from Grupo_Estudiante, Grupo ,Estudiante, Estudiante_juez\n"
                + "where lower(Grupo.Nombre) = lower('" + group + "') AND\n"
                + "Grupo_Estudiante.ID_Estudiante = Estudiante.ID_Estudiante AND\n"
                + "Estudiante_Juez.ID_Estudiante = Estudiante.ID_Estudiante ";

        try {
            ResultSet rs;
           
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) 
                {   
                    tabla.add(rs.getString("user"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return tabla;
    }

    
}
