/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableModel;

/**
 *
 * @author Администратор
 */
public class CreateForm extends JFrame implements MouseListener {
    String TableName;
    int i;
    MyConnection m;
    public Connection con;
    public TableModel model;
    JTextField Field, Field2;
    JLabel Label, Label2;
    public JButton but, but1, but2, but3;
    public JTable table;
    public TM model1;
    public ArrayList<ArrayList> data;
    public ArrayList detaName;
    String query = null;

    public CreateForm(Connection con, MyConnection m) throws SQLException {
        this.con = con;
        this.m = m;
        
    }

    public void createF() throws SQLException {
        data = m.getData();
        detaName = m.getColumName();
        model1 = new TM(data, detaName);
        model1.addTableModelListener(table);
        table = new JTable(model1);
        table.addMouseListener(this);
        model1.addTableModelListener(table);
        JPanel panel = new JPanel(new BorderLayout());
        JComboBox combobox = new JComboBox();
       
       // while (i < m.getTableList().size()) {
         //   combobox.addItem(m.getTableList().get(i));
           // i++;
        //}
        
        panel.setSize(500, 400);
        panel.add(new JScrollPane(table), BorderLayout.PAGE_START);
        JFrame frame = new JFrame("Database Table Model");
        but = new JButton("добавить");
        but1 = new JButton("удалить");
        but2 = new JButton("изменить");
        but3 = new JButton("очистить");
        but.setActionCommand("insert");
        but1.setActionCommand("delete");
        but2.setActionCommand("update");
        but3.setActionCommand("clean");
        Field = new JTextField("");
        Field2 = new JTextField("");
        Label = new JLabel();
        Label2 = new JLabel();
          if (table.getColumnCount()>0) {
             Label.setText(table.getColumnName(0));
             Label2.setText(table.getColumnName(1)); 
        }
        panel.add(new JScrollPane(but));
        ActionListener UpdateActionListener = new UpdateActionListener();
        but.addActionListener(UpdateActionListener);
        but1.addActionListener(UpdateActionListener);
        but2.addActionListener(UpdateActionListener);
        but3.addActionListener(UpdateActionListener);
        frame.setSize(500, 400);

        Box box = Box.createVerticalBox();
        Box Labelpanel = Box.createHorizontalBox();
        Box Textpanel = Box.createHorizontalBox();

        Textpanel.add(Field);
        Textpanel.add(Field2);

        Labelpanel.add(Label);
        Labelpanel.add(Label2);

        JToolBar toolBar = new JToolBar();
        box.add(new JScrollPane(table));
        box.add(combobox);
        toolBar.add(but);
        toolBar.add(but1);
        toolBar.add(but2);
        toolBar.add(but3);
        frame.setContentPane(box);
        frame.getContentPane().add(toolBar);
        frame.getContentPane().add(Labelpanel);
        frame.getContentPane().add(Textpanel);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Да", "Нет!"};
                int n = JOptionPane
                        .showOptionDialog(e.getWindow(), "Закрыть окно?",
                        "Подтверждение", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options,
                        options[0]);
                if (n == 0) {
                    e.getWindow().setVisible(false);
                    System.out.println("Window Closed");
                    try {
                        con.close();
                        System.out.println("con close");
                    } catch (SQLException ex) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Field.setText((String) model1.getValueAt(table.getSelectedRow(), 1));
        Field2.setText((String) model1.getValueAt(table.getSelectedRow(), 2));

    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void updateTable() throws SQLException {
        model1.setTableData(m.getData());
    }

    public class UpdateActionListener implements ActionListener {

      

        @Override
        public void actionPerformed(ActionEvent e) {
            query=null;
            if ("clean".equals(e.getActionCommand())) {
                query = "Delete from TEST ";
            }
            
             if ("insert".equals(e.getActionCommand())) {
                query = "INSERT INTO TEST (id,name, Secondname) VALUES ((select GEN_ID (app,1) from  RDB$DATABASE)"+ ", '"+ Field.getText() + "', '" + Field2.getText() + "')";
            }
            
            if (table.getSelectedRow()>=0){

           
            if ("delete".equals(e.getActionCommand())) {
                query = "Delete from TEST where Id='" + model1.getValueAt(table.getSelectedRow(), 0) + "'";
            }
            
            if ("update".equals(e.getActionCommand())) {
                query = "Update TEST set name='" + Field.getText() + "', secondname='" + Field2.getText() + "' where Id='" + model1.getValueAt(table.getSelectedRow(), 0) + "'";
            }
            }
            try {
                if (query!=null){
                new Select().sqlcod(con, query);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CreateForm.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    updateTable();
                    Field2.setText(null);
                    Field.setText(null);
                } catch (SQLException ex) {
                    Logger.getLogger(CreateForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        }
    }
