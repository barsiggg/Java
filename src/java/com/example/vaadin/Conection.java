package dbconnection;

import java.sql.*;
import java.util.Properties;

public class Conection {

     Connection con;
     PreparedStatement create, select, insert;
    Statement qwe;
    ResultSet rs = null;

    

    public Connection getConection(String jdbcUrl, String login, String password) throws SQLException {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            final Properties prop = new Properties();
            prop.put("user", login);
            prop.put("password", password);
            prop.put("charSet", "Cp1251");
            prop.put("lc_ctype", "WIN1251");
            return DriverManager.getConnection(jdbcUrl, prop);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
   