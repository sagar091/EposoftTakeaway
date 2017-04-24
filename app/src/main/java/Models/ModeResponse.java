package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/25/2016.
 */

public class ModeResponse {

    @SerializedName("collection")
    @Expose
    private Boolean collection;
    @SerializedName("delivery")
    @Expose
    private Boolean delivery;
    @SerializedName("inside")
    @Expose
    private Boolean inside;
    @SerializedName("table")
    @Expose
    private Boolean table;
    @SerializedName("wholesale")
    @Expose
    private Boolean wholesale;
    @SerializedName("cash")
    @Expose
    private Boolean cash;
    @SerializedName("card")
    @Expose
    private Boolean card;
    @SerializedName("cardatshop")
    @Expose
    private Boolean cardatshop;

    /**
     *
     * @return
     * The collection
     */
    public Boolean getCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     * The collection
     */
    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    /**
     *
     * @return
     * The delivery
     */
    public Boolean getDelivery() {
        return delivery;
    }

    /**
     *
     * @param delivery
     * The delivery
     */
    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    /**
     *
     * @return
     * The inside
     */
    public Boolean getInside() {
        return inside;
    }

    /**
     *
     * @param inside
     * The inside
     */
    public void setInside(Boolean inside) {
        this.inside = inside;
    }

    /**
     *
     * @return
     * The table
     */
    public Boolean getTable() {
        return table;
    }

    /**
     *
     * @param table
     * The table
     */
    public void setTable(Boolean table) {
        this.table = table;
    }

    /**
     *
     * @return
     * The wholesale
     */
    public Boolean getWholesale() {
        return wholesale;
    }

    /**
     *
     * @param wholesale
     * The wholesale
     */
    public void setWholesale(Boolean wholesale) {
        this.wholesale = wholesale;
    }

    /**
     *
     * @return
     * The cash
     */
    public Boolean getCash() {
        return cash;
    }

    /**
     *
     * @param cash
     * The cash
     */
    public void setCash(Boolean cash) {
        this.cash = cash;
    }

    /**
     *
     * @return
     * The card
     */
    public Boolean getCard() {
        return card;
    }

    /**
     *
     * @param card
     * The card
     */
    public void setCard(Boolean card) {
        this.card = card;
    }

    /**
     *
     * @return
     * The cardatshop
     */
    public Boolean getCardatshop() {
        return cardatshop;
    }

    /**
     *
     * @param cardatshop
     * The cardatshop
     */
    public void setCardatshop(Boolean cardatshop) {
        this.cardatshop = cardatshop;
    }

}
