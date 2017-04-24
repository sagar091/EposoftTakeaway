package Models.PostalAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Models.PostalAddress.Address;
import Models.PostalAddress.Config;

/**
 * Created by Mobo on 8/4/2016.
 */
public class PostalResponse {
    @SerializedName("info")
    @Expose
    private String info;


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("round")
    @Expose
    private String round;
    @SerializedName("amt")
    @Expose
    private String amt;
    @SerializedName("action")
    @Expose
    private Boolean action;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("config")
    @Expose
    private Config config;

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The dist
     */
    public Integer getDist() {
        return dist;
    }

    /**
     * @param dist The dist
     */
    public void setDist(Integer dist) {
        this.dist = dist;
    }

    /**
     * @return The round
     */
    public String getRound() {
        return round;
    }

    /**
     * @param round The round
     */
    public void setRound(String round) {
        this.round = round;
    }

    /**
     * @return The amt
     */
    public String getAmt() {
        return amt;
    }

    /**
     * @param amt The amt
     */
    public void setAmt(String amt) {
        this.amt = amt;
    }

    /**
     * @return The action
     */
    public Boolean getAction() {
        return action;
    }

    /**
     * @param action The action
     */
    public void setAction(Boolean action) {
        this.action = action;
    }

    /**
     * @return The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The config
     */

    public String getInfo() {
        return info;
    }

    /**
     * @param info The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return The config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * @param config The config
     */
    public void setConfig(Config config) {
        this.config = config;
    }

}
