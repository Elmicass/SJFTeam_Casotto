package Casotto.controller;

import Casotto.model.*;

/**
 * TODO
 */
public interface IProductsManager {

    /**
     * TODO
     * @param productID
     * @return
     */
    Product getProductInstance(String productID);

    /**
     * TODO
     * @return
     */
    String[] getProductsBasicInformations();

    /**
     * TODO
     * @param productID
     * @return
     */
    String[] getProductFullInformation(String productID);


}
