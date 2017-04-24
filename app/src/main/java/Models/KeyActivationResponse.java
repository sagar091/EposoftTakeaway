package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aman Singh on 10/20/2016.
 */

public class KeyActivationResponse {
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("app_key")
    @Expose
    private String appKey;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     *
     * @param appId
     * The app_id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     *
     * @return
     * The appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     *
     * @param appKey
     * The app_key
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
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
}
