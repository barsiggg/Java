package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class MyConnection {

    Connection con;
    PreparedStatement create, select, insert;
    Statement qwe;
    ResultSet rs = null;
    public ResultSet sel, listTable;
    public ArrayList element;
    public ArrayList<ArrayList> result;
    public ArrayList CollumName;
    public ArrayList<String> TableName;
    public String text;

    public MyConnection(Connection connect) {
        con = connect;

    }
        
    public String getText(){
        text="123";
        return text;
    }
    
    public static Connection getConection(String jdbcUrl, String login, String password) throws SQLException {
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

    public ArrayList getData() throws SQLException {


        result = new ArrayList();
        try {
            sel = new Select().rs(con, "select * from TEST");
            int columnCount = sel.getMetaData().getColumnCount();
            CollumName = new ArrayList();
            for (int i = 0; i < sel.getMetaData().getColumnCount(); i++) {
                CollumName.add(sel.getMetaData().getColumnName(i + 1).toString());
            }
            while (sel.next()) {
                element = new ArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    element.add(sel.getObject(i));
                }
                result.add(element);
            }
            
            MyConnection m = new MyConnection(con);
            m.getTableList();
            
           if (result.size()==0){
                element = new ArrayList();
               for (int i = 1; i <= columnCount; i++) {
                    element.add(null);
           }
                result.add(element);
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
      
        return result;
    }

    public ArrayList getColumName() throws SQLException {
        return CollumName;
    }

    public ArrayList getTableList() throws SQLException {
        TableName = new ArrayList();
        listTable = con.getMetaData().getTables("", "", "%", null);
        while (listTable.next()) {
            TableName.add(listTable.getString(3));
        }
        return TableName;

    }
}
