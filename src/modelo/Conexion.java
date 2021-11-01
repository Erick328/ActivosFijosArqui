
package modelo;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Erick Vidal
 */
public class Conexion {
    Connection con;
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/activosfijos";
        String user="root";
        String pass="";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
            System.err.println(e);
        }
        return con;
    }
}
