package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/8/2016.
 */
public class Order {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sno")
    @Expose
    private String sno;
    @SerializedName("otype")
    @Expose
    private String otype;
    @SerializedName("ptype")
    @Expose
    private String ptype;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("coupon_discount")
    @Expose
    private String couponDiscount;
    @SerializedName("coupon_desc")
    @Expose
    private String couponDesc;
    @SerializedName("inst")
    @Expose
    private String inst;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("bag")
    @Expose
    private String bag;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_desc")
    @Expose
    private String discountDesc;
    @SerializedName("gift")
    @Expose
    private String gift;
    @SerializedName("gift_qty")
    @Expose
    private String giftQty;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The sno
     */
    public String getSno() {
        return sno;
    }

    /**
     * @param sno The sno
     */
    public void setSno(String sno) {
        this.sno = sno;
    }

    /**
     * @return The otype
     */
    public String getOtype() {
        return otype;
    }

    /**
     * @param otype The otype
     */
    public void setOtype(String otype) {
        this.otype = otype;
    }

    /**
     * @return The ptype
     */
    public String getPtype() {
        return ptype;
    }

    /**
     * @param ptype The ptype
     */
    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The deliveryCharge
     */
    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    /**
     * @param deliveryCharge The delivery_charge
     */
    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    /**
     * @return The couponDiscount
     */
    public String getCouponDiscount() {
        return couponDiscount;
    }

    /**
     * @param couponDiscount The coupon_discount
     */
    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    /**
     * @return The couponDesc
     */
    public String getCouponDesc() {
        return couponDesc;
    }

    /**
     * @param couponDesc The coupon_desc
     */
    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    /**
     * @return The inst
     */
    public String getInst() {
        return inst;
    }

    /**
     * @param inst The inst
     */
    public void setInst(String inst) {
        this.inst = inst;
    }

    /**
     * @return The bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank The bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return The bag
     */
    public String getBag() {
        return bag;
    }

    /**
     * @param bag The bag
     */
    public void setBag(String bag) {
        this.bag = bag;
    }

    /**
     * @return The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * @param discount The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * @return The discountDesc
     */
    public String getDiscountDesc() {
        return discountDesc;
    }

    /**
     * @param discountDesc The discount_desc
     */
    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    /**
     * @return The gift
     */
    public String getGift() {
        return gift;
    }

    /**
     * @param gift The gift
     */
    public void setGift(String gift) {
        this.gift = gift;
    }

    /**
     * @return The giftQty
     */
    public String getGiftQty() {
        return giftQty;
    }

    /**
     * @param giftQty The gift_qty
     */
    public void setGiftQty(String giftQty) {
        this.giftQty = giftQty;
    }


}
