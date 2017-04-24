package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobo on 8/8/2016.
 */
public class OrderItems {
    @SerializedName("item")
    @Expose
    private Item_ item;
    @SerializedName("addon")
    @Expose
    private List<OrderAddon> addon = new ArrayList<OrderAddon>();

    /**
     *
     * @return
     * The item
     */
    public Item_ getItem() {
        return item;
    }

    /**
     *
     * @param item
     * The item
     */
    public void setItem(Item_ item) {
        this.item = item;
    }

    /**
     *
     * @return
     * The addon
     */
    public List<OrderAddon> getAddon() {
        return addon;
    }

    /**
     *
     * @param addon
     * The addon
     */
    public void setAddon(List<OrderAddon> addon) {
        this.addon = addon;
    }

}
