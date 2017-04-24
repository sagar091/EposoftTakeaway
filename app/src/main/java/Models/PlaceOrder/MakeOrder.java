package Models.PlaceOrder;

/**
 * Created by Mobo on 8/4/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Models.Coupon;
import Models.SandageResponse;

public class MakeOrder implements Parcelable {

    @SerializedName("refno")
    @Expose
    private String refno;
    @SerializedName("ddesc")
    @Expose
    private String ddesc;
    @SerializedName("free")
    @Expose
    private String free;
    @SerializedName("fgift")
    @Expose
    private String fgift;
    @SerializedName("sgift")
    @Expose
    private String sgift;
    @SerializedName("sandage")
    @Expose
    private SandageResponse sandage;
    @SerializedName("inst")
    @Expose
    private String inst;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("coupon")
    @Expose
    private Coupon coupon;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("otype")
    @Expose
    private String otype;
    @SerializedName("ptype")
    @Expose
    private String ptype;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("app_key")
    @Expose
    private String appKey;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("tablename")
    @Expose
    private String tablename;
    @SerializedName("staff")
    @Expose
    private String staff;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getDdesc() {
        return ddesc;
    }

    public void setDdesc(String ddesc) {
        this.ddesc = ddesc;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getFgift() {
        return fgift;
    }

    public void setFgift(String fgift) {
        this.fgift = fgift;
    }

    public String getSgift() {
        return sgift;
    }

    public void setSgift(String sgift) {
        this.sgift = sgift;
    }

    public SandageResponse getSandage() {
        return sandage;
    }

    public void setSandage(SandageResponse sandage) {
        this.sandage = sandage;
    }

    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.refno);
        dest.writeString(this.ddesc);
        dest.writeString(this.free);
        dest.writeString(this.fgift);
        dest.writeString(this.sgift);
        dest.writeParcelable(this.sandage, flags);
        dest.writeString(this.inst);
        dest.writeTypedList(this.items);
        dest.writeString(this.discount);
        dest.writeParcelable(this.coupon, flags);
        dest.writeString(this.delivery);
        dest.writeParcelable(this.user, flags);
        dest.writeString(this.otype);
        dest.writeString(this.ptype);
        dest.writeString(this.appId);
        dest.writeString(this.appKey);
        dest.writeString(this.request);
        dest.writeString(this.tablename);
        dest.writeString(this.staff);
    }

    public MakeOrder() {
    }

    protected MakeOrder(Parcel in) {
        this.refno = in.readString();
        this.ddesc = in.readString();
        this.free = in.readString();
        this.fgift = in.readString();
        this.sgift = in.readString();
        this.sandage = in.readParcelable(SandageResponse.class.getClassLoader());
        this.inst = in.readString();
        this.items = in.createTypedArrayList(Item.CREATOR);
        this.discount = in.readString();
        this.coupon = in.readParcelable(Coupon.class.getClassLoader());
        this.delivery = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.otype = in.readString();
        this.ptype = in.readString();
        this.appId = in.readString();
        this.appKey = in.readString();
        this.request = in.readString();
        this.tablename = in.readString();
        this.staff = in.readString();
    }

    public static final Parcelable.Creator<MakeOrder> CREATOR = new Parcelable.Creator<MakeOrder>() {
        @Override
        public MakeOrder createFromParcel(Parcel source) {
            return new MakeOrder(source);
        }

        @Override
        public MakeOrder[] newArray(int size) {
            return new MakeOrder[size];
        }
    };
}