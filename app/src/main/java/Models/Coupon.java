package Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Models.PlaceOrder.MakeOrder;

/**
 * Created by Mobo on 8/4/2016.
 */
public class Coupon implements Parcelable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     *
     * @param discount
     * The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(discount);
        dest.writeString(name);
        dest.writeString(type);

    }

    public Coupon(){

    }

    public Coupon(Parcel in){
        code = in.readString();
        discount = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
