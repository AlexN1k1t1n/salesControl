package gui;

import util.PropertiesStorage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TablePanel extends JPanel {

    private Object[] header = new String[]{"N", "Наименование", "Количество", "Цена", "Стоимость"};
    private JTable table;
    private String prefixAllPrice = "Общая стоимость: ";
    private String prefixOrderNumber = "Сделка номер: ";
    private JLabel orderNumber;
    private JLabel allPrice;
    private DefaultTableModel tableModel;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private PropertiesStorage storage = PropertiesStorage.getInstance();

    public TablePanel(){
        //setLayout(new GridLayout(4,1));
        allPrice = new JLabel();
        orderNumber = new JLabel();
        orderNumber.setText(prefixOrderNumber + storage.getProperties().get("currentOrder"));
        allPrice.setText(prefixAllPrice + 0);
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(header);

        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int summary = 0;
                for (int row = 0; row < table.getRowCount(); row++){
                    if (!table.getValueAt(row, 3).toString().equals("") &&
                            !table.getValueAt(row, 2).toString().equals("")) {
                        table.setValueAt(Integer.parseInt(table.getValueAt(row, 3).toString()) *
                                Integer.parseInt(table.getValueAt(row, 2).toString()), row, 4);
                        summary += Integer.parseInt(table.getValueAt(row, 4).toString());
                    }
                    allPrice.setText(prefixAllPrice + summary);
                }

            }
        });

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // вставка строки в конце
                int idx = table.getRowCount();
                tableModel.addRow(new String[] {
                        "" + (idx + 1),"", "", "", ""});
            }
        });

        JButton remove = new JButton("Удалить");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Номер выделенной строки
                int idx = table.getSelectedRow();
                // Удаление выделенной строки
                tableModel.removeRow(idx);
            }
        });

        JButton saveOrder = new JButton("Cохранить продажу");
        saveOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int curOrd = Integer.parseInt(storage.getProperties().get("currentOrder"));
                System.out.println("Saving...");
                String line;
                String data = "{ " + dateFormat.format(new Date()).toString() + " " + curOrd + "\n";
                for (int i = 0; i < table.getRowCount(); i++){
                    line = "";
                    for (int j = 0; j < table.getColumnCount(); j++){
                        line += table.getValueAt(i,j) + ",";
                    }
                    System.out.println(line);
                    data += line + "\n";
                }
                System.out.println("Saved!");
                data += "}" + "\n";
                storage.updateProperty("currentOrder", (curOrd+1)+"");
                orderNumber.setText(prefixOrderNumber + storage.getProperties().get("currentOrder"));
                // export to db
                try {
                    FileOutputStream outputStream = new FileOutputStream("sales.txt", true);
                    byte[] bytes = data.getBytes();
                    outputStream.write(bytes);
                    outputStream.close();
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        // Формирование интерфейса
        add(orderNumber);
        add(allPrice);

        Box contents = new Box(BoxLayout.LINE_AXIS);
        contents.add(new JScrollPane(table));
        add(contents);

        JPanel buttons = new JPanel();
        buttons.add(add);
        buttons.add(remove);
        buttons.add(saveOrder);
        add(buttons, "South");
    }

}
