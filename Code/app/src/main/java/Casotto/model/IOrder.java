package Casotto.model;

/**
 * TODO
 */
public interface IOrder {

    /**
     * @return ID dell'ordinazione.
     */
    String getID();

    /**
     * @return il cliente associato all'ordinazione.
     */
    Customer getClient();

    /**
     * @return lista di prodotti ordinati.
     */
    Product[] getProducts();

    /**
     * @return il totale conto, dovuto dal cliente.
     */
    double getDueAmount();

    /**
     * TODO
     */
    boolean addProduct(Product product);

    /**
     * TODO
     */
    boolean rimuoviProdotto(Product product);

}