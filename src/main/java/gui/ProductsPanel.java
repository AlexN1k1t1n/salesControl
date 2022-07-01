package gui;

import models.Order;
import models.Product;
import util.PropertiesStorage;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ProductsPanel extends JPanel {

    private Object[] header = new String[]{"N", "Наименование", "Количество", "Цена", "Стоимость"};
    private JTable table;
    private String prefixAllPrice = "Общая стоимость: ";
    private String prefixOrderNumber = "Сделка номер: ";
    private JLabel orderNumber;
    private JLabel allPrice;
    private DefaultTableModel tableModel;
    private JTextField textDate;
    private JButton search;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    ProductsPanel() {
        //setLayout(new GridLayout(4,1));
        allPrice = new JLabel();
        orderNumber = new JLabel();
        allPrice.setText(prefixAllPrice + 0);
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(header);

        textDate = new JTextField();
        textDate.setText(dateFormat.format(new Date()));

        table = new JTable(tableModel);

        search = new JButton("Искать по дате");

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                try {
                    String[][] data = searchOrder(textDate.getText());
                    for (int i = 0; i < data.length; i++){
                        tableModel.addRow(data[i]);
                    }
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
            }
        });

        JPanel header = new JPanel(new GridLayout(1,2));
        header.add(textDate);
        header.add(search);

        add(header);

        orderNumber.setText(prefixOrderNumber + textDate.getText());
        add(orderNumber);
        add(allPrice);

        Box contents = new Box(BoxLayout.LINE_AXIS);
        contents.add(new JScrollPane(table));
        add(contents);
    }

    private String[][] searchOrder(String date) throws FileNotFoundException {
        int count = 0;
        int allPriceOfOrders = 0;
        ArrayList<Product> products = new ArrayList<>();
        File file = new File("sales.txt");
        Scanner in = new Scanner(file);
        String line;
        boolean isThisOrder = false;
        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.contains("{") && date.equals(line.split(" ")[1])){
                isThisOrder = true;
                continue;
            }
            if (line.contains("}")){
                isThisOrder = false;
                continue;
            }
            if (line.equals("")){
                continue;
            }
            if (isThisOrder){
                count++;
                allPriceOfOrders+=Integer.parseInt(line.split(",")[4]);
                products.add(new Product(
                        count,
                        line.split(",")[1],
                        Integer.parseInt(line.split(",")[2]),
                        Integer.parseInt(line.split(",")[3]),
                        Integer.parseInt(line.split(",")[4])
                ));
            }
        }
        allPrice.setText(prefixAllPrice + allPriceOfOrders);
        String[][] productsMassive = new String[products.size()][5];
        for (int i = 0; i < productsMassive.length; i++){
            productsMassive[i] = products.get(i).toStringMassive();
        }

        return productsMassive;
    }
}