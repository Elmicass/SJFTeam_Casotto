public interface IOrder {

    /**
     * getID() 
     * @return ID ordinazione
     */
    getID();

    /**
     * getIDCliente() ritorna ID Cliente associato a ID ordinazone
     * @return ID cliente stringa
     */
    getCliente();

    /**
     * 
     * @return lista prodotti
     */
    getProdotti();

    /**
     * 
     * 
     */
    getTotale();

    /**
     * 
     * 
     */
    aggiungiProdotto();

    /**
     * 
     * 
     */
    rimuoviProdotto();
}