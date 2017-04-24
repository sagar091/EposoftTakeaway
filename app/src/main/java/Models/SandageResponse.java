package Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/3/2016.
 */
public class SandageResponse implements Parcelable {
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("bag")
    @Expose
    private String bag;

    /**
     *
     * @return
     * The bank
     */
    public String getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     * The bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     * The bag
     */
    public String getBag() {
        return bag;
    }

    /**
     *
     * @param bag
     * The bag
     */
    public void setBag(String bag) {
        this.bag = bag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bank);
        dest.writeString(this.bag);
    }

    public SandageResponse() {
    }

    protected SandageResponse(Parcel in) {
        this.bank = in.readString();
        this.bag = in.readString();
    }

    public static final Creator<SandageResponse> CREATOR = new Creator<SandageResponse>() {
        @Override
        public SandageResponse createFromParcel(Parcel source) {
            return new SandageResponse(source);
        }

        @Override
        public SandageResponse[] newArray(int size) {
            return new SandageResponse[size];
        }
    };
}