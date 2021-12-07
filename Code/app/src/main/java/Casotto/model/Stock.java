package Casotto.model;

/**
 * Singleton pattern
 */
public class Stock {

    private static Stock stock;


    private Stock() {

    }

    public static Stock getInstance() { 
        if (stock == null) { 
            stock = new Stock(); 
        }
        return stock;
    }
}