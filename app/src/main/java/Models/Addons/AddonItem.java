package Models.Addons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 7/26/2016.
 */
public class AddonItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("aid")
    @Expose
    private String aid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("aname")
    @Expose
    private String aname;
    @SerializedName("Sub_Add_Items")
    @Expose
    private String subAddItems;
    @SerializedName("icon")
    @Expose
    private String icon;

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

    /**
     *
     * @return
     * The aid
     */
    public String getAid() {
        return aid;
    }

    /**
     *
     * @param aid
     * The aid
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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
     * The order
     */
    public String getOrder() {
        return order;
    }

    /**
     *
     * @param order
     * The order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The next
     */
    public String getNext() {
        return next;
    }

    /**
     *
     * @param next
     * The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     *
     * @return
     * The aname
     */
    public String getAname() {
        return aname;
    }

    /**
     *
     * @param aname
     * The aname
     */
    public void setAname(String aname) {
        this.aname = aname;
    }

    /**
     *
     * @return
     * The subAddItems
     */
    public String getSubAddItems() {
        return subAddItems;
    }

    /**
     *
     * @param subAddItems
     * The Sub_Add_Items
     */
    public void setSubAddItems(String subAddItems) {
        this.subAddItems = subAddItems;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}