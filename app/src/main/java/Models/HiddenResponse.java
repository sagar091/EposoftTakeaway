package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aman Singh on 10/15/2016.
 */

public class HiddenResponse {
    @SerializedName("action")
    @Expose
    private Boolean action;
    @SerializedName("info")
    @Expose
    private String info;

    /**
     *
     * @return
           * The action
     */
    public Boolean getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(Boolean action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The info
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @param info
     * The info
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
