package Casotto.model;

public class Product implements IProduct{
    private String ID;
    private String name;
    private String description;
    private Double unitPrice;
    private Integer quantity;

    /**
	 * @return - ID del prodotto
	 */
    @Override
    public String getID() {
        return ID;
    }

    /**
	 * @return - nome del prodotto
	 */
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


    /**
	 * @return - descrizione del prodotto
	 */
    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /**
	 * @return - prezzo unitario del prodotto
	 */
    @Override
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice){
        this.unitPrice = unitPrice;
    }

    /**
	 * @return - quantità di prodotto disponibile
	 */
    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    @Override
    public String[] getProductBasicInformations(Product product) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getProductFullInformation(Product product) {
        // TODO Auto-generated method stub
        return null;
    }




    
}
