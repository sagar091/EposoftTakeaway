package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aman Singh on 9/20/2016.
 */

public class DiscountResponse  {
    @SerializedName("item1")
    @Expose
    private String item1;
    @SerializedName("item2")
    @Expose
    private String item2;
    @SerializedName("item3")
    @Expose
    private String item3;
    @SerializedName("item4")
    @Expose
    private String item4;
    @SerializedName("item5")
    @Expose
    private String item5;
    @SerializedName("gift_min")
    @Expose
    private String giftMin;
    @SerializedName("gift_status")
    @Expose
    private String giftStatus;
    @SerializedName("del_status")
    @Expose
    private String delStatus;
    @SerializedName("col_status")
    @Expose
    private String colStatus;
    @SerializedName("waiting_status")
    @Expose
    private String waitingStatus;
    @SerializedName("type_del")
    @Expose
    private String typeDel;
    @SerializedName("type_coll")
    @Expose
    private String typeColl;
    @SerializedName("type_wait")
    @Expose
    private String typeWait;
    @SerializedName("cto")
    @Expose
    private String cto;
    @SerializedName("cfrom")
    @Expose
    private String cfrom;
    @SerializedName("dto")
    @Expose
    private String dto;
    @SerializedName("dfrom")
    @Expose
    private String dfrom;
    @SerializedName("wto")
    @Expose
    private String wto;
    @SerializedName("wfrom")
    @Expose
    private String wfrom;
    @SerializedName("gto")
    @Expose
    private String gto;
    @SerializedName("gfrom")
    @Expose
    private String gfrom;
    @SerializedName("delivery_discount")
    @Expose
    private String deliveryDiscount;
    @SerializedName("collection_discount")
    @Expose
    private String collectionDiscount;
    @SerializedName("waiting_discount")
    @Expose
    private String waitingDiscount;
    @SerializedName("del_dis_min")
    @Expose
    private String delDisMin;
    @SerializedName("coll_dis_min")
    @Expose
    private String collDisMin;
    @SerializedName("wait_dis_min")
    @Expose
    private String waitDisMin;
    @SerializedName("coupon_status")
    @Expose
    private String couponStatus;

    /**
     *
     * @return
     * The item1
     */
    public String getItem1() {
        return item1;
    }

    /**
     *
     * @param item1
     * The item1
     */
    public void setItem1(String item1) {
        this.item1 = item1;
    }

    /**
     *
     * @return
     * The item2
     */
    public String getItem2() {
        return item2;
    }

    /**
     *
     * @param item2
     * The item2
     */
    public void setItem2(String item2) {
        this.item2 = item2;
    }

    /**
     *
     * @return
     * The item3
     */
    public String getItem3() {
        return item3;
    }

    /**
     *
     * @param item3
     * The item3
     */
    public void setItem3(String item3) {
        this.item3 = item3;
    }

    /**
     *
     * @return
     * The item4
     */
    public String getItem4() {
        return item4;
    }

    /**
     *
     * @param item4
     * The item4
     */
    public void setItem4(String item4) {
        this.item4 = item4;
    }

    /**
     *
     * @return
     * The item5
     */
    public String getItem5() {
        return item5;
    }

    /**
     *
     * @param item5
     * The item5
     */
    public void setItem5(String item5) {
        this.item5 = item5;
    }

    /**
     *
     * @return
     * The giftMin
     */
    public String getGiftMin() {
        return giftMin;
    }

    /**
     *
     * @param giftMin
     * The gift_min
     */
    public void setGiftMin(String giftMin) {
        this.giftMin = giftMin;
    }

    /**
     *
     * @return
     * The giftStatus
     */
    public String getGiftStatus() {
        return giftStatus;
    }

    /**
     *
     * @param giftStatus
     * The gift_status
     */
    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    /**
     *
     * @return
     * The delStatus
     */
    public String getDelStatus() {
        return delStatus;
    }

    /**
     *
     * @param delStatus
     * The del_status
     */
    public void setDelStatus(String delStatus) {
        this.delStatus = delStatus;
    }

    /**
     *
     * @return
     * The colStatus
     */
    public String getColStatus() {
        return colStatus;
    }

    /**
     *
     * @param colStatus
     * The col_status
     */
    public void setColStatus(String colStatus) {
        this.colStatus = colStatus;
    }

    /**
     *
     * @return
     * The waitingStatus
     */
    public String getWaitingStatus() {
        return waitingStatus;
    }

    /**
     *
     * @param waitingStatus
     * The waiting_status
     */
    public void setWaitingStatus(String waitingStatus) {
        this.waitingStatus = waitingStatus;
    }

    /**
     *
     * @return
     * The typeDel
     */
    public String getTypeDel() {
        return typeDel;
    }

    /**
     *
     * @param typeDel
     * The type_del
     */
    public void setTypeDel(String typeDel) {
        this.typeDel = typeDel;
    }

    /**
     *
     * @return
     * The typeColl
     */
    public String getTypeColl() {
        return typeColl;
    }

    /**
     *
     * @param typeColl
     * The type_coll
     */
    public void setTypeColl(String typeColl) {
        this.typeColl = typeColl;
    }

    /**
     *
     * @return
     * The typeWait
     */
    public String getTypeWait() {
        return typeWait;
    }

    /**
     *
     * @param typeWait
     * The type_wait
     */
    public void setTypeWait(String typeWait) {
        this.typeWait = typeWait;
    }

    /**
     *
     * @return
     * The cto
     */
    public String getCto() {
        return cto;
    }

    /**
     *
     * @param cto
     * The cto
     */
    public void setCto(String cto) {
        this.cto = cto;
    }

    /**
     *
     * @return
     * The cfrom
     */
    public String getCfrom() {
        return cfrom;
    }

    /**
     *
     * @param cfrom
     * The cfrom
     */
    public void setCfrom(String cfrom) {
        this.cfrom = cfrom;
    }

    /**
     *
     * @return
     * The dto
     */
    public String getDto() {
        return dto;
    }

    /**
     *
     * @param dto
     * The dto
     */
    public void setDto(String dto) {
        this.dto = dto;
    }

    /**
     *
     * @return
     * The dfrom
     */
    public String getDfrom() {
        return dfrom;
    }

    /**
     *
     * @param dfrom
     * The dfrom
     */
    public void setDfrom(String dfrom) {
        this.dfrom = dfrom;
    }

    /**
     *
     * @return
     * The wto
     */
    public String getWto() {
        return wto;
    }

    /**
     *
     * @param wto
     * The wto
     */
    public void setWto(String wto) {
        this.wto = wto;
    }

    /**
     *
     * @return
     * The wfrom
     */
    public String getWfrom() {
        return wfrom;
    }

    /**
     *
     * @param wfrom
     * The wfrom
     */
    public void setWfrom(String wfrom) {
        this.wfrom = wfrom;
    }

    /**
     *
     * @return
     * The gto
     */
    public String getGto() {
        return gto;
    }

    /**
     *
     * @param gto
     * The gto
     */
    public void setGto(String gto) {
        this.gto = gto;
    }

    /**
     *
     * @return
     * The gfrom
     */
    public String getGfrom() {
        return gfrom;
    }

    /**
     *
     * @param gfrom
     * The gfrom
     */
    public void setGfrom(String gfrom) {
        this.gfrom = gfrom;
    }

    /**
     *
     * @return
     * The deliveryDiscount
     */
    public String getDeliveryDiscount() {
        return deliveryDiscount;
    }

    /**
     *
     * @param deliveryDiscount
     * The delivery_discount
     */
    public void setDeliveryDiscount(String deliveryDiscount) {
        this.deliveryDiscount = deliveryDiscount;
    }

    /**
     *
     * @return
     * The collectionDiscount
     */
    public String getCollectionDiscount() {
        return collectionDiscount;
    }

    /**
     *
     * @param collectionDiscount
     * The collection_discount
     */
    public void setCollectionDiscount(String collectionDiscount) {
        this.collectionDiscount = collectionDiscount;
    }

    /**
     *
     * @return
     * The waitingDiscount
     */
    public String getWaitingDiscount() {
        return waitingDiscount;
    }

    /**
     *
     * @param waitingDiscount
     * The waiting_discount
     */
    public void setWaitingDiscount(String waitingDiscount) {
        this.waitingDiscount = waitingDiscount;
    }

    /**
     *
     * @return
     * The delDisMin
     */
    public String getDelDisMin() {
        return delDisMin;
    }

    /**
     *
     * @param delDisMin
     * The del_dis_min
     */
    public void setDelDisMin(String delDisMin) {
        this.delDisMin = delDisMin;
    }

    /**
     *
     * @return
     * The collDisMin
     */
    public String getCollDisMin() {
        return collDisMin;
    }

    /**
     *
     * @param collDisMin
     * The coll_dis_min
     */
    public void setCollDisMin(String collDisMin) {
        this.collDisMin = collDisMin;
    }

    /**
     *
     * @return
     * The waitDisMin
     */
    public String getWaitDisMin() {
        return waitDisMin;
    }

    /**
     *
     * @param waitDisMin
     * The wait_dis_min
     */
    public void setWaitDisMin(String waitDisMin) {
        this.waitDisMin = waitDisMin;
    }

    /**
     *
     * @return
     * The couponStatus
     */
    public String getCouponStatus() {
        return couponStatus;
    }

    /**
     *
     * @param couponStatus
     * The coupon_status
     */
    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }
}
