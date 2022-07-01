package gui;

import gui.OrdersPanel;
import gui.TablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    JPanel tablePanel;
    JPanel ordersPanel;
    JPanel currentPanel;
    JPanel productsPanel;
    JPanel orderId;
    JMenuBar menuBar;

    public MainFrame(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Убери тетрадку, блин!");
        setSize(new Dimension(500,500));
        setVisible(true);
        tablePanel = new TablePanel();
        ordersPanel = new OrdersPanel();
        orderId = new SingleOrderTable();
        productsPanel = new ProductsPanel();
        createMenuBar();
        setJMenuBar(menuBar);
        add(tablePanel);
        currentPanel = tablePanel;
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();

        JMenu sales = new JMenu("Продажи");
        JMenuItem newOrder = new JMenuItem("Новая продажа");
        JMenuItem allOrders = new JMenuItem("Все сделки за день");
        JMenuItem orderById = new JMenuItem("Продажа по id");
        JMenuItem allProducts = new JMenuItem("Все продажи за день");

        newOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(currentPanel);
                currentPanel = tablePanel;
                add(tablePanel);
                repaint();
                revalidate();
            }
        });

        allOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(currentPanel);
                currentPanel = ordersPanel;
                add(ordersPanel);
                repaint();
                revalidate();
            }
        });

        orderById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(currentPanel);
                currentPanel = orderId;
                add(orderId);
                repaint();
                revalidate();
            }
        });

        allProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(currentPanel);
                currentPanel = productsPanel;
                add(productsPanel);
                repaint();
                revalidate();
            }
        });

        JMenu reports = new JMenu("Отчеты");
        JMenuItem money = new JMenuItem("Деньги - в разработке");

        sales.add(newOrder);
        sales.addSeparator();
        sales.add(allOrders);
        sales.addSeparator();
        sales.add(allProducts);
        sales.addSeparator();
        sales.add(orderById);

        reports.add(money);

        menuBar.add(sales);
        menuBar.add(reports);

    }

}
