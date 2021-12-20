package Casotto.model;

import java.util.List;

import Casotto.controller.OrdersManager;

public class Order implements IOrder{
    private String ID;
    private Customer customer;
    private String qrCode;
    private List<Product> product;
    private Double dueAmount;

    /**
	 * @return - ID dell'ordinazione
	 */
    @Override
    public String getID() {
        return ID;
    }

    /**
	 * @return - oggetto customer associato all'ordinazione
	 */
    @Override
    public Customer getCustomer() {
        return customer;
    }

    /**
	 * @return - Lista di Prodotti associati all'ordine
	 */
    @Override
    public List<Product> getProducts() {
        return product;
    }

    /**
	 * @return - prezzo totale dell'ordinazione
	 */
    @Override
    public double getDueAmount() {
        return dueAmount;
    }

    /**
     * @param product il prodotto da aggiungere
	 * @return - true se il prodotto è stato correttamente aggiunto, false altrimenti
	 */
    @Override
    public boolean addProduct(Product product, Integer productQuantity) {
        OrdersManager ordersManager = new OrdersManager();
        return ordersManager.addProduct(product, productQuantity);

    }

    @Override
    public boolean rimuoviProdotto(Product product) {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
