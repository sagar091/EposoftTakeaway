package Models.PlaceOrder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobo on 8/4/2016.
 */
public class Item implements Parcelable {

    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("sub")
    @Expose
    private List<Sub> sub = new ArrayList<Sub>();

    /**
     *
     * @return
     * The itemid
     */
    public String getItemid() {
        return itemid;
    }

    /**
     *
     * @param itemid
     * The itemid
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
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
     * The sub
     */
    public List<Sub> getSub() {
        return sub;
    }

    /**
     *
     * @param sub
     * The sub
     */
    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemid);
        dest.writeString(this.qty);
        dest.writeList(this.sub);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.itemid = in.readString();
        this.qty = in.readString();
        this.sub = new ArrayList<Sub>();
        in.readList(this.sub, Sub.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}