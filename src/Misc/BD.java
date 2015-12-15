/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import java.sql.*;
import java.util.AbstractMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ernesto
 */
public class BD {

    public Map<String, String> getStudent(String est, String specify) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        Map<String, String> resultSet
                = new HashMap<String, String>();

        String sql = "";
        if (specify.toLowerCase().compareTo("nick") == 0) {
            sql = "select Estudiante.username as user , Estudiante.nombre,Estudiante.apellido, Estudiante.fecha_nacimiento, Estudiante.email as email, Estudiante.fecha_ingreso,  Grado.tipo ,  Coach.Nombre as SNOMBRE, coach.apellido as SAPELLIDO \n"
                    + "from estudiante, grado, coach, Estudiante_Juez \n"
                    + "where  estudiante.id_estudiante  = Estudiante_Juez.id_estudiante AND\n"
                    + "Estudiante_Juez.nick = '" + est + "' AND  \n"
                    + "grado.id_grado = estudiante.grado AND estudiante.coach = coach.id_Coach ;";
        } else if (specify.toLowerCase().compareTo("name") == 0) {

            sql = "select Estudiante.username as user , Estudiante.nombre, Estudiante.apellido, Estudiante.fecha_nacimiento, Estudiante.email as email , \n"
                    + "Estudiante.fecha_ingreso,  Grado.tipo ,  \n"
                    + "Coach. Nombre as SNOMBRE, coach.apellido as SAPELLIDO\n"
                    + "from estudiante, grado, coach\n"
                    + "where \n"
                    + "(lower(estudiante.NOMBRE)= lower('" + est + "') OR\n"
                    + "lower(estudiante.APELLIDO)= lower('" + est + "') )AND \n"
                    + "grado.id_grado = estudiante.grado AND\n"
                    + "estudiante.coach = coach.id_Coach";

        } else if (specify.toLowerCase().compareTo("user") == 0) {
            sql = "	select Estudiante.username as user , Estudiante.nombre as nombre,Estudiante.apellido as apellido , Estudiante.fecha_nacimiento as Fecha_nacimiento, Estudiante.email as email, Estudiante.fecha_ingreso as Fecha_ingreso,\n"
                    + "Grado.tipo as tipo,  Coach. Nombre as SNOMBRE, coach.apellido as SAPELLIDO from estudiante, grado, coach\n"
                    + " where\n"
                    + "grado.id_grado = estudiante.grado AND estudiante.coach = coach.id_coach AND estudiante.USERNAME = '" + est + "'";
        } else {
            return null;
        }

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        rs.next();

        resultSet.put("user", rs.getString("user"));
        resultSet.put("Name", rs.getString("Nombre"));
        resultSet.put("LastName", rs.getString("Apellido"));
        resultSet.put("BirthDay", rs.getString("Fecha_nacimiento"));
        resultSet.put("email", rs.getString("email"));
        resultSet.put("SignInD", rs.getString("Fecha_ingreso"));
        resultSet.put("Type", rs.getString("Tipo"));
        resultSet.put("CName", rs.getString("SNombre"));
        resultSet.put("CLastName", rs.getString("SApellido"));

        stmt.close();

        con.close();
        return resultSet;
    }

    public ArrayList<String> getStudentByAlias(String est) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        ArrayList<String> Info = new ArrayList<String>();

        String sql = "";

        try {
            Statement stmt = con.createStatement();

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
            stmt.close();

        } catch (SQLException ex) {

        }

        con.close();
        return Info;
    }

    public void addStudent(ArrayList<String> studentData) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");

        con.setAutoCommit(false);
        String sql = " ";
        if (studentData.size() == 4) {
            sql = "INSERT INTO Estudiante (username, nombre, apellido, fecha_nacimiento, fecha_ingreso,grado,coach)\n"
                    + " VALUES ('" + studentData.get(0) + "' ,'" + studentData.get(1)
                    + "','" + studentData.get(2) + "', '" + studentData.get(3) + "', current_date , 1, 1\n"
                    + ")";
        } else if (studentData.size() == 5) {
            sql = "INSERT INTO Estudiante (username, nombre, apellido, fecha_nacimiento, fecha_ingreso,grado,coach,email)\n"
                    + " VALUES ('" + studentData.get(0) + "' ,'" + studentData.get(1)
                    + "','" + studentData.get(2) + "', '" + studentData.get(3) + "', current_date , 1, 1, '" + studentData.get(4) + "'\n"
                    + ")";
        }
        //else return;

        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();
        con.close();

    }

    public void rmStudent(String student) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        String sql = "delete from estudiante where username = '" + student + "';";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();
        con.close();

    }

    public ArrayList<String> getStudentsInGroup(String group) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        ArrayList<String> tabla = new ArrayList<String>();
        ResultSet rs;

        String sql = "select Estudiante.username as user, Estudiante.Nombre , Estudiante.Apellido\n"
                + "from Estudiante, grupo, grupo_Estudiante\n"
                + "where \n"
                + "Estudiante.id_Estudiante = Grupo_Estudiante.ID_Estudiante AND\n"
                + "Grupo.Nombre = Grupo_Estudiante.Nombre_Grupo AND\n"
                + "lower(Grupo.Nombre )= lower('" + group + "')\n"
                + "; ";

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            tabla.add(rs.getString("user"));
        }

        stmt.close();

        con.close();
        return tabla;
    }

    public boolean GroupExists(String group) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        String sql = "select count(*) as total\n"
                + "from Grupo \n"
                + "where lower(Grupo.Nombre) = lower('" + group + "') \n"
                + ";";

        ResultSet rs;

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();
        if (rs.getInt("total") == 1) {
            stmt.close();

            con.close();
            return true;
        }
        stmt.close();

        con.close();
        return false;

    }
    
//This is the method
    public ArrayList<String> getNicksInGroup(String group, String OJ) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        ArrayList<String> tabla = new ArrayList<String>();
        ResultSet rs;

        String sql = " select Estudiante.username as user, Estudiante_Juez.alias as nick \n" +
                    "from Estudiante, grupo, grupo_Estudiante, Estudiante_juez , Juez \n" +
                    "where lower(Grupo_Estudiante.Nombre_Grupo)= lower('"+ group +"') AND\n" +
                    "Estudiante.id_Estudiante = Grupo_Estudiante.ID_Estudiante AND\n" +
                    "Estudiante.id_Estudiante = Estudiante_Juez.id_estudiante AND \n" +
                    " Estudiante_juez.id_Juez = Juez.Id_Juez AND \n" +
                    "lower(Juez.Nombre) = lower('"+ OJ+"')";

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            tabla.add(rs.getString("nick"));
        }

        stmt.close();

        con.close();
        return tabla;
    }

    public boolean studentExists(String stud) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        String sql = "select count(*) as total\n"
                + "from Estudiante \n"
                + "where username = '" + stud + "'";

        ResultSet rs;

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();
        if (rs.getInt("total") >= 1) {
            stmt.close();

            con.close();
            return true;
        }

        stmt.close();

        con.close();

        return false;

    }

    public ArrayList<String> filterStudents(ArrayList<String> studNotF) throws SQLException, ClassNotFoundException {

        ArrayList<String> studF = new ArrayList<String>();

        for (String s : studNotF) {
            if (studentExists(s)) {
                studF.add(s);
            }
        }

        return studF;
    }

    public List<String> filterStudentsGroup(String group, List<String> students) throws SQLException, ClassNotFoundException {
        List<String> tabla = new ArrayList<String>();
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        for (String s : students) {
            ResultSet rs;
            Statement stmt;
            String sql = "select Estudiante.username \n"
                    + "from Estudiante, grupo, grupo_Estudiante\n"
                    + "where \n"
                    + "Estudiante.username = '" + s + "' AND\n"
                    + " Estudiante.id_Estudiante = Grupo_Estudiante.ID_Estudiante AND\n"
                    + "Grupo.Nombre = Grupo_Estudiante.Nombre_Grupo AND\n"
                    + "Grupo.Nombre = '" + group + "' ";

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = stmt.executeQuery(sql);

            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            if (rowcount != 0) {
                continue;
            }

            tabla.add(s);

            rs.close();
            stmt.close();
        }

        return tabla;
    }

    public int getStudentID(String s) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        int id = 0;

        String sql = "select id_estudiante as id \n"
                + "from Estudiante \n"
                + "where estudiante.username = '" + s + "' ;";

        ResultSet rs;

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();

        id = rs.getBigDecimal("id").intValue();

        stmt.close();

        con.close();
        return id;
    }

    public int getOJId(String s) throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        int id = 0;

        String sql = "select id_juez as id \n"
                + "from Juez \n"
                + "where lower(nombre) = lower('" + s + "') ";

        ResultSet rs;

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();

        id = rs.getInt("id");

        stmt.close();

        con.close();
        return id;
    }

    public void createGroup(String group) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        String sql = "insert into grupo values( '" + group.toLowerCase() + "' )";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();

        con.close();
    }

    public void insertGroupStudents(String group, List<String> students) throws SQLException, ClassNotFoundException {

        String sql = "";

        for (String s : students) {
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                    "admin7wbaict", "Exf6tmuYJXWh");
            con.setAutoCommit(false);

            sql = "insert into grupo_estudiante values( '" + group.toLowerCase() + "' , " + String.valueOf(getStudentID(s)) + " )";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.commit();
            stmt.close();
            con.close();
        }

    }

    public boolean nickExists(String stud, String OJ) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        String sql = "select count(*) as total\n"
                + "from Estudiante_juez \n"
                + "where id_estudiante = '" + getStudentID(stud) + "' AND id_juez = '" + getOJId(OJ) + "'";

        ResultSet rs;

        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        rs.next();
        if (rs.getInt("total") >= 1) {
            stmt.close();

            con.close();
            return true;
        }

        stmt.close();

        con.close();

        return false;

    }

    public void insertNickStudent(String Student, String OJ, String Nick) throws ClassNotFoundException, SQLException {
        String sql = "";
        int id = getStudentID(Student);
        int oj = getOJId(OJ);

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);
        System.out.println("\nGUT!!\n");

        sql = "insert into estudiante_juez values(" + id + "," + oj + ",'" + Nick + "')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();
        con.close();

    }

    public void updateNickStudent(String Student, String OJ, String Nick) throws ClassNotFoundException, SQLException {
        String sql = "";

        int id = getStudentID(Student);
        int oj = getOJId(OJ);

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection("jdbc:postgresql://127.10.65.2:5432/scoach",
                "admin7wbaict", "Exf6tmuYJXWh");
        con.setAutoCommit(false);

        sql = "update estudiante_juez set alias = '" + Nick + "' where id_estudiante = " + id + " AND id_juez = " + oj+" ";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
        con.commit();
        stmt.close();
        con.close();

    }

}
