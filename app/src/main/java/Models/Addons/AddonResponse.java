package Models.Addons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import Models.Addons.Addon;
import Models.Addons.AddonItem;

/**
 * Created by Mobo on 7/26/2016.
 */
public class AddonResponse {
    @SerializedName("addon")
    @Expose
    private Addon addon;
    @SerializedName("addon_items")
    @Expose
    private List<AddonItem> addonItems = new ArrayList<>();

    /**
     *
     * @return
     * The addon
     */
    public Addon getAddon() {
        return addon;
    }

    /**
     *
     * @param addon
     * The addon
     */
    public void setAddon(Addon addon) {
        this.addon = addon;
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
