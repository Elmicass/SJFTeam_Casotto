package Casotto.model;

/**
 * Rappresenta l'insieme di prodotti attualmente a disposizione dello chalet.
 */
public interface IStock {

    /**
     * TODO
     * @param product
     * @return
     */
    int getQuantity(Product product);

    /**
     * TODO
     * @param product
     * @return 
     */
    boolean addProduct(Product product);

    /**
     * TODO
     * @param product
     * @return
     */
    boolean subtractProduct(Product product);

    /**
     * TODO
     * @return
     */
    int getProductsNumber();
    
}
