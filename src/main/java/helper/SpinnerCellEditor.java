/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

/**
 *
 * @author ASUS
 */
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.util.EventObject;

public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JSpinner spinner;

  public SpinnerCellEditor() {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        spinner = new JSpinner(spinnerModel);
        
        // Tăng kích thước phông chữ cho trường nhập liệu của JSpinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setFont(new Font("Arial", Font.PLAIN, 16)); // Thay đổi kích thước phông chữ ở đây
    }
    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

   @Override
public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    // Kiểm tra nếu giá trị là một số nguyên
    if (value instanceof Integer) {
        int intValue = (int) value;
        // Đảm bảo giá trị nằm trong phạm vi giới hạn
        intValue = Math.max(intValue, 1); // Giá trị tối thiểu là 1
        intValue = Math.min(intValue, Integer.MAX_VALUE); // Giá trị tối đa là Integer.MAX_VALUE
        spinner.setValue(intValue);
    } else {
        // Nếu giá trị không phải là một số nguyên, sử dụng giá trị mặc định là 1
        spinner.setValue(1);
    }
    return spinner;
}

}

