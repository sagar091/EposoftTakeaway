package Models.PlaceOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/4/2016.
 */
public class OrderResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
