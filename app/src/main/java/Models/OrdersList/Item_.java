package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/8/2016.
 */
public class Item_ {

    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @param itemName
     * The item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     *
     * @return
     * The qty
     */
    public String getQty() {
        return qty;
    }

    /**
     *
     * @param qty
     * The qty
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The total
     */
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}