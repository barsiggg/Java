package com.example.vaadin;




import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Администратор
 */
public class TM extends AbstractTableModel {

    public ArrayList<ArrayList> result;
    public ArrayList ColumName;

    public TM(ArrayList<ArrayList> data, ArrayList ColumName) {
        result = data;
        this.ColumName = ColumName;

    }

    @Override
    public int getRowCount() {
        return result.size();
    }

    @Override
    public int getColumnCount() {
        return result.size() == 0 ? 0 : result.get(0).size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return result.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return ColumName.get(column).toString();
    }

    public void setTableData(ArrayList<ArrayList> tableData) {
        result = tableData;
        fireTableDataChanged();
    }

  
}
