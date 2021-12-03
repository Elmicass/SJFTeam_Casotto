package Casotto.model;

/**
 * Rappresenta un singolo prodotto.
 */
public interface IProduct {
    
    /**
     * @return ID del prodotto.
     */
    String getID();

    /**
     * @return nome del prodotto.
     */
    String getName();

    /**
     * @return descrizione del prodotto.
     */
    String getDescription();

    /**
     * @return prezzo del prodotto.
     */
    double getPrice();

    /**
     * @return quantità di prodotto disponibile in magazzino.
     */
    int getQuantity();

    /**
     * @return informazioni base di ogni prodotto.
     */
    String[] getProductsBasicInformations();

    /**
     * @param product - uno specifico prodotto.
     * @return informazioni dettagliate di uno specifico prodotto.
     */
    String[] getProductFullInformation(Product product);



}