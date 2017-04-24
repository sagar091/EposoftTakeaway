package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/9/2016.
 */
public class OrderAddon {
    @SerializedName("addon_name")
    @Expose
    private String addonName;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;

    /**
     *
     * @return
     * The addonName
     */
    public String getAddonName() {
        return addonName;
    }

    /**
     *
     * @param addonName
     * The addon_name
     */
    public void setAddonName(String addonName) {
        this.addonName = addonName;
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
}
