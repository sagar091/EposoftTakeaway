package Models.PlaceOrder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Models.Coupon;

/**
 * Created by Mobo on 8/4/2016.
 */
public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dno")
    @Expose
    private String dno;
    @SerializedName("add1")
    @Expose
    private String add1;
    @SerializedName("add2")
    @Expose
    private String add2;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The fname
     */
    public String getFname() {
        return fname;
    }

    /**
     *
     * @param fname
     * The fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     *
     * @return
     * The lname
     */
    public String getLname() {
        return lname;
    }

    /**
     *
     * @param lname
     * The lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The dno
     */
    public String getDno() {
        return dno;
    }

    /**
     *
     * @param dno
     * The dno
     */
    public void setDno(String dno) {
        this.dno = dno;
    }

    /**
     *
     * @return
     * The add1
     */
    public String getAdd1() {
        return add1;
    }

    /**
     *
     * @param add1
     * The add1
     */
    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    /**
     *
     * @return
     * The add2
     */
    public String getAdd2() {
        return add2;
    }

    /**
     *
     * @param add2
     * The add2
     */
    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    /**
     *
     * @return
     * The postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     *
     * @param postcode
     * The postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fname);
        dest.writeString(this.lname);
        dest.writeString(this.phone);
        dest.writeString(this.dno);
        dest.writeString(this.add1);
        dest.writeString(this.add2);
        dest.writeString(this.postcode);
        dest.writeString(this.username);
        dest.writeString(this.status);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.fname = in.readString();
        this.lname = in.readString();
        this.phone = in.readString();
        this.dno = in.readString();
        this.add1 = in.readString();
        this.add2 = in.readString();
        this.postcode = in.readString();
        this.username = in.readString();
        this.status = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}