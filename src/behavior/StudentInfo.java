/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package behavior;

import static behavior.Echo.helpText;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 *
 * @author Ernesto
 */
public class StudentInfo extends GeneralBehavior {

    void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        Session session = jsch.getSession(strSshUser, strSshHost, 22);
        session.setPassword(strSshPassword);

        final Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }

    @Override
    public Map<String, String> Run(Map<String, List<String>> requestProperties) {

        Map<String, String> responseProperties = new HashMap<String, String>();

        responseProperties.put("text", helpText);

        String text = null;
        if (requestProperties != null && requestProperties.get("text") != null) {
            try {
                String strSshUser = "ernestorod27@gmail.com";                  // SSH loging username
                String strSshPassword = "Megaman2015";                   // SSH login password
                String strSshHost = "ex-std-node669.prod.rhcloud.com";          // hostname or ip or SSH server
                int nSshPort = 22;                                    // remote SSH host port number
                String strRemoteHost = "ex-std-node669.prod.rhcloud.com";  // hostname or ip of your database server
                int nLocalPort = 3366;                                // local port number use to bind SSH tunnel
                int nRemotePort = 3306;                               // remote port number of your database 
                String strDbUser = "admin7wbaict";                    // database loging username
                String strDbPassword = "Exf6tmuYJXWh";                    // database login password

                responseProperties.put("text", "bien");
                return responseProperties;
               /* doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://$OPENSHIFT_POSTGRESQL_DB_HOST:$OPENSHIFT_POSTGRESQL_DB_PORT:" + nLocalPort, strDbUser, strDbPassword);
                
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery( "SELECT * FROM Estudiante;" );
                while ( rs.next() ) {
                   String  name = rs.getString("nombre");
                   text = name;
                   
                }
                rs.close();
                stmt.close();
                
                con.close();
                       */
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                responseProperties.put("text", text);
            }

        }
        
        return responseProperties;
    }

}
