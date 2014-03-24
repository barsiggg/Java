package dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Дмитрий
 */
public class DBConnection {

    public static Connection con;
    MyConnection m;
    CreateForm create;
    NewGroovyClass nw;

    public void start() throws SQLException {
        con = MyConnection.getConection("jdbc:firebirdsql:localhost:C:/db.fdb", "sysdba", "masterkey");
        m = new MyConnection(con);
        create= new CreateForm(con,m);
        create.createF();
         nw= new NewGroovyClass();
    nw.getMetod();
                }

    public static void main(String[] args) throws SQLException {
        try {
            new DBConnection().start();
        } catch (Exception e) {
            System.out.println(e);
        } 
                }
}
