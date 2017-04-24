package Models.Addons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobo on 7/26/2016.
 */
public class Addon {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("aname")
    @Expose
    private String aname;
    @SerializedName("special_addon")
    @Expose
    private String specialAddon;
    @SerializedName("addon_items")
    @Expose
    private List<AddonItem> addonItems = new ArrayList<>();

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
     * The limit
     */
    public String getLimit() {
        return limit;
    }

    /**
     *
     * @param limit
     * The limit
     */
    public void setLimit(String limit) {
        this.limit = limit;
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
     * The specialAddon
     */
    public String getSpecialAddon() {
        return specialAddon;
    }

    /**
     *
     * @param specialAddon
     * The special_addon
     */
    public void setSpecialAddon(String specialAddon) {
        this.specialAddon = specialAddon;
    }

    /**
     *
     * @return
     * The addonItems
     */
    public List<AddonItem> getAddonItems() {
        return addonItems;
    }

    /**
     *
     * @param addonItems
     * The addon_items
     */
    public void setAddonItems(List<AddonItem> addonItems) {
        this.addonItems = addonItems;
    }
}