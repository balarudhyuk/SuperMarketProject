public class SuperMarket {


    private String itemName;
    private int price;
    private int noOfItems;
    private  double total =0.0;
    private double specialPrice;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public  void setTotal(double total) {
        this.total = total;
    }

    public void setSpecialPrice(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public SuperMarket(String itemName, int price, int noOfItems, double SpecialPrice){

        this.itemName = itemName;
        this.price = price;
        this.noOfItems = noOfItems;
        this.specialPrice = SpecialPrice;

    }

    public String getItemName() {
        return itemName;
    }

    public int getPrice() {
        return price;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public double getTotal() {
        return total;
    }

    public double getSpecialPrice() {
        return specialPrice;
    }
}
