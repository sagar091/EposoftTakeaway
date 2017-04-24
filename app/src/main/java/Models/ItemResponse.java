package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 7/26/2016.
 */
public class ItemResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("coupon")
    @Expose
    private String coupon;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("addon")
    @Expose
    private String addon;
    @SerializedName("aname")
    @Expose
    private String aname;
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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * The coupon
     */
    public String getCoupon() {
        return coupon;
    }

    /**
     *
     * @param coupon
     * The coupon
     */
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The options
     */
    public String getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(String options) {
        this.options = options;
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
     * The addon
     */
    public String getAddon() {
        return addon;
    }

    /**
     *
     * @param addon
     * The addon
     */
    public void setAddon(String addon) {
        this.addon = addon;
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
