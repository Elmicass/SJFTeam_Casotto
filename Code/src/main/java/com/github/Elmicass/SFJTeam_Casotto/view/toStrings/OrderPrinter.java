package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.OrdersManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class OrderPrinter implements Printer<Order> {

    @Autowired
    private OrdersManager ordersManager;

    @Autowired
    private TimeSlotPrinter timePrinter;

    @Autowired
    private ProductPrinter productPrinter;

    @Override
    public String shortToStringVersion(Order order) {
        String returnValue;
        String open;
        if (order.isOpen())
            open = "Waiting";
        else
            open = "Closed";
        returnValue = "- [ID: " + order.getID() + " | Customer username: " + order.getCustomer()
                + " | Creation time: " + timePrinter.localDateTimeToString(order.getCreationTime()) + " | "
                + open + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(Order order) {
        String returnValue;
        String open;
        if (order.isOpen())
            open = "Waiting";
        else
            open = "Closed";
        String firstLine = String.format("%-215s", new String("+")) + "+";
        String secondLine = String.format("%-215s", new String("| [ID: " + order.getID() + " - Customer username: " + order.getCustomer()
                                + " - Creation time: " + timePrinter.localDateTimeToString(order.getCreationTime())
                                + " - " + open + " ]")) + "|";
        String thirdLine = String.format("%-215s", new String("| Products: ")) + "|";
        String fourthLine = orderProducts(order);
        String fifthLine = String.format("%-215s", new String("| Total due amount: " + order.getDueAmount() + "EUR")) + "|";
        String sixthLine = String.format("%-215s", new String("+")) + "+";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + fifthLine + "\n" + sixthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<Order> list) {
        if (!(list.isEmpty())) {
            for (Order order : list) {
                System.out.println(shortToStringVersion(order));
                System.out.flush();
            }
        } else {
            System.out.println("[No orders exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(Order order) {
        System.out.println(shortToStringVersion(order));
        System.out.flush();
    }

    @Override
    public void printFullVersion(Order order) {
        System.out.println(fullToStringVersion(order));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String orderProducts(Order order) {
        String returnValue = "";
        String init = "| ";
        for (Product product : order.getProducts()) {
            returnValue = returnValue + String.format("%-215s", init + productPrinter.shortToStringVersion(product))
                    + "|\n";
        }
        return returnValue;
    }

    public TimeSlotPrinter getTimePrinter() {
        return timePrinter;
    }

    public ProductPrinter getProductPrinter() {
        return productPrinter;
    }

}
