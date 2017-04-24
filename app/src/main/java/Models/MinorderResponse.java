package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/9/2016.
 */
public class MinorderResponse {

    @SerializedName("collection")
    @Expose
    private String collection;
    @SerializedName("delivery")
    @Expose
    private String delivery;

    /**
     *
     * @return
     * The collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     * The collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     *
     * @return
     * The delivery
     */
    public String getDelivery() {
        return delivery;
    }

    /**
     *
     * @param delivery
     * The delivery
     */
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
