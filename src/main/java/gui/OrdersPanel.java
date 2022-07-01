package gui;

import models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class OrdersPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private Object[] header = new Object[]{"N", "Дата", "Количество товаров", "Сумма"};
    private JTextField dateText;
    private JButton search;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private JLabel summaryPrice;
    private String prefixSummaryPrice = "Всего продаж на : ";

    OrdersPanel(){

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(header);

        table = new JTable(tableModel);

        dateText = new JTextField();
        dateText.setText(dateFormat.format(new Date()));
        search = new JButton("Искать по дате");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I'm doing not real search");
                tableModel.setRowCount(0);
                try {
                    String[][] data = searchOrders(dateText.getText());
                    for (int i = 0; i < data.length; i++){
                        tableModel.addRow(data[i]);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        summaryPrice = new JLabel();

        JPanel header = new JPanel(new GridLayout(1,2));
        header.add(dateText);
        header.add(search);
        add(header);

        Box contents = new Box(BoxLayout.LINE_AXIS);
        contents.add(new JScrollPane(table));
        add(contents);

        add(summaryPrice);
    }

    private String[][] searchOrders(String date) throws FileNotFoundException {
        int allPriceOfOrders = 0;
        ArrayList<Order> orders = new ArrayList<>();
        File file = new File("sales.txt");
        Scanner in = new Scanner(file);
        String line;
        // вся сумма продажи
        int summary = 0;
        // счетчик сделок
        int amount = 0;
        // количество товаров в сделке
        int amountProducts = 0;
        boolean inThisDate = false;
        while (in.hasNextLine()){
            line = in.nextLine();
            if (line.contains("{") && date.equals(line.split(" ")[1])){
                inThisDate = true;
                orders.add(new Order());
                orders.get(amount).setDate(line.split(" ")[1]);
                orders.get(amount).setId(Integer.parseInt(line.split(" ")[2]));
                continue;
            }
            if (line.contains("}") && inThisDate){
                orders.get(amount).setAmountOfSales(amountProducts);
                orders.get(amount).setPrice(summary);
                amount++;
                allPriceOfOrders += summary;
                amountProducts = 0;
                summary = 0;
                inThisDate = false;
                continue;
            }
            if (line.equals("")){
                continue;
            }
            if (inThisDate) {
                summary += Integer.parseInt(line.split(",")[4]);
                amountProducts += Integer.parseInt(line.split(",")[2]);
            }
        }
        String[][] ordersMassive = new String[orders.size()][4];
        for (int i = 0; i < orders.size(); i++){
            ordersMassive[i] = orders.get(i).toStringMassive();
        }
        summaryPrice.setText(prefixSummaryPrice + allPriceOfOrders);
        return ordersMassive;
    }

}
