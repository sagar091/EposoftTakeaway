package Models.PlaceOrder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/4/2016.
 */
public class Sub implements Parcelable {

    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ext")
    @Expose
    private String ext;

    /**
     *
     * @return
     * The item
     */
    public String getItem() {
        return item;
    }

    /**
     *
     * @param item
     * The item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     *
     * @return
     * The qty
     */
    public String getQty() {
        return qty;
    }

    /**
     *
     * @param qty
     * The qty
     */
    public void setQty(String qty) {
        this.qty = qty;
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

    /**
     *
     * @return
     * The ext
     */
    public String getExt() {
        return ext;
    }

    /**
     *
     * @param ext
     * The ext
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.item);
        dest.writeString(this.qty);
        dest.writeString(this.type);
        dest.writeString(this.ext);
    }

    public Sub() {
    }

    protected Sub(Parcel in) {
        this.item = in.readString();
        this.qty = in.readString();
        this.type = in.readString();
        this.ext = in.readString();
    }

    public static final Parcelable.Creator<Sub> CREATOR = new Parcelable.Creator<Sub>() {
        @Override
        public Sub createFromParcel(Parcel source) {
            return new Sub(source);
        }

        @Override
        public Sub[] newArray(int size) {
            return new Sub[size];
        }
    };
}