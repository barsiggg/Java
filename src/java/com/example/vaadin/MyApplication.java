/*
 * MyApplication.java
 *
 * Created on 11 Июль 2013 г., 11:57
 */
package com.example.vaadin;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import com.vaadin.Application;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.addon.sqlcontainer.query.FreeformQuery;
import com.vaadin.addon.sqlcontainer.query.TableQuery;
import com.vaadin.ui.*;
import com.vaadin.data.*;
import com.vaadin.data.Property.ValueChangeEvent;
import java.awt.BorderLayout;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Администратор
 * @version
 */
public class MyApplication extends Application {

    public SimpleJDBCConnectionPool pool;
    public Table table;
    public Table table1;
    FreeformQuery fr;
    public Label label;
    private Connection con;
    MyConnection m;
    String TableName;
    int i;
    public TableModel model;
    JTextField Field, Field2;
    JLabel Label, Label2;
    public JButton but, but1, but2, but3;
    public TM model1;
    public ArrayList<ArrayList> data;
    public ArrayList detaName;
    String query = null;
    Container c;
    private Button butto;
    private Button past;
    public Object val1;

    
    
    public void init() {
        Window mainWindow = new Window("MyApplication");

        try {
            pool = new SimpleJDBCConnectionPool("org.firebirdsql.jdbc.FBDriver", "jdbc:firebirdsql:localhost:C:/db.fdb?lc_ctype=WIN1251", "sysdba", "masterkey");

            final SQLContainer container;
            fr = new FreeformQuery("SELECT * FROM TEST", pool);
            container = new SQLContainer(fr);
            table = new Table("Test", container);
            table1=new Table("Тест-1",container);
            table.setContainerDataSource(container);
            table.setSelectable(true);
            table.setImmediate(true);
            table.setEditable(true);
            butto = new Button("Обновить");
            past = new Button("Добавить");
            butto.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    val1 = null;
                    if (table.getValue() == null) {
                    } else {
                        val1 = table.getValue();
                        System.out.println(container.getItem(container.getIdByIndex(val1.hashCode() - 1)).getItemProperty("ID").getValue().toString());
                    }
                    table.refreshRowCache();
                    table.requestRepaint();
                }
            });

            past.addListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Connection conn = null;
                    val1 = null;
                    if (table.getValue() != null) {
                        val1 = table.getValue();
                        try {
                            conn = pool.reserveConnection();
                            Statement statement = conn.createStatement();
                            System.out.println(val1.hashCode());
                            System.out.println(container.getItem(container.getIdByIndex(val1.hashCode() - 1)).getItemProperty("ID").getValue().toString());
                            System.out.println(container.lastItemId().hashCode());
                            statement.executeUpdate("Delete from TEST where ID=" + table.getItem(container.getIdByIndex(val1.hashCode() - 1)).getItemProperty("ID").getValue().toString() + " ");
                            if (container.isLastId(val1) == true) {
                                table.select(container.prevItemId(val1));
                            }
                            conn.commit();
                            statement.close();

                            container.refresh();
                        



                        } catch (SQLException ex) {
                            Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
                            try {
                                conn.rollback();
                            } catch (SQLException ex1) {
                                Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        } finally {
                            pool.releaseConnection(conn);
                            table.requestRepaint();

                        }

                    }
                }
            });



        } catch (SQLException ex) {
            Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
        
        
      }
        
        LoginForm login=new LoginForm();
        login.setPasswordCaption("Пароль");
        login.setLoginButtonCaption("Войти");
        login.setUsernameCaption("Логин");
        FormLayout form=new FormLayout();
        GridLayout grid=new GridLayout(4, 4);
        Label lab=new Label();
        lab.setCaption(String.valueOf(grid.getRows()));
        grid.addComponent(table);
        TabSheet tab=new TabSheet();
        tab.addComponent(grid);
        tab.addComponent(table1);
        
        Panel panel= new Panel("Инфо");
        panel.addComponent(tab);
        panel.addComponent(lab);
        panel.addComponent(butto);
        panel.addComponent(past);
        form.addComponent(panel);
      /*mainWindow.addComponent(form);
        /*mainWindow.addComponent();
        mainWindow.addComponent(butto);
        mainWindow.addComponent(past);
      */
      
        HorizontalLayout layout = new HorizontalLayout();
            layout.setSpacing(true);
            layout.setMargin(true);

            layout.addComponent(butto);
            layout.addComponent(past);
           
            layout.setComponentAlignment(butto, Alignment.BOTTOM_LEFT);
            mainWindow.addComponent(layout);
         setMainWindow(mainWindow);
    
           
     
    }
    
       
}
