package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.ProductsManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class ProductPrinter implements Printer<Product> {

    @Autowired
    private ProductsManager productsManager;

    @Override
    public String shortToStringVersion(Product prod) {
        String returnValue;
        String quantity;
        if (prod.getQuantity() == 0)
            quantity = "No availability at the moment";
        else
            quantity = String.valueOf(prod.getQuantity());
        returnValue = "- [ID: " + prod.getID() + " | Name: " + prod.getName() + " | Unit price: " + prod.getUnitPrice() + "EUR | "
                + quantity + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(Product prod) {
        String returnValue;
        String quantity;
        if (prod.getQuantity() == 0)
            quantity = "No availability at the moment";
        else
            quantity = String.valueOf(prod.getQuantity());
        String firstLine = String.format("%-215s", new String("+")) + "+";
        String secondLine = String.format("%-215s", new String("| [ID: " + prod.getID() + " - Product name: " + prod.getName() + " - Unit price: "
                                + prod.getUnitPrice() + " - Availability: " + quantity + " ]")) + "|";
        String thirdLine = String.format("%-215s", new String("| Description: " + prod.getDescription())) + "|";
        String fourthLine = String.format("%-215s", new String("+")) + "+";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<Product> list) {
        if (!(list.isEmpty())) {
            for (Product product : list) {
                System.out.println(shortToStringVersion(product));
                System.out.flush();
            }
        } else {
            System.out.println("[No products exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(Product product) {
        System.out.println(shortToStringVersion(product));
        System.out.flush();
    }

    @Override
    public void printFullVersion(Product product) {
        System.out.println(fullToStringVersion(product));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void barServiceAvailableProducts() {
        if (!(productsManager.getAll().isEmpty())) {
            for (Product prod : productsManager.getAll()) {
                if (prod.getQuantity() > 0) {
                    System.out.println(shortToStringVersion(prod));
                    System.out.flush();
                }
            }
        } else
            System.out.println("[No products exists at the moment!]");
        System.out.flush();
    }
    
}
