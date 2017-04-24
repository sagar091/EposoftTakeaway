package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aman Singh on 10/20/2016.
 */

public class KeyGenerationResponse {

    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("appkey")
    @Expose
    private String appkey;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     *
     * @return
     * The appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     *
     * @param appid
     * The appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     *
     * @return
     * The appkey
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     *
     * @param appkey
     * The appkey
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
