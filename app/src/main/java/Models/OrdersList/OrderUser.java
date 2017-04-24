package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/8/2016.
 */
public class OrderUser {

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
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("add1")
    @Expose
    private String add1;
    @SerializedName("add2")
    @Expose
    private String add2;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("bname")
    @Expose
    private String bname;
    @SerializedName("land")
    @Expose
    private String land;

    /**
     * @return The fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname The fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return The lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname The lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The dno
     */
    public String getDno() {
        return dno;
    }

    /**
     * @param dno The dno
     */
    public void setDno(String dno) {
        this.dno = dno;
    }

    /**
     * @return The postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode The postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return The add1
     */
    public String getAdd1() {
        return add1;
    }

    /**
     * @param add1 The add1
     */
    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    /**
     * @return The add2
     */
    public String getAdd2() {
        return add2;
    }

    /**
     * @param add2 The add2
     */
    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The bname
     */
    public String getBname() {
        return bname;
    }

    /**
     * @param bname The bname
     */
    public void setBname(String bname) {
        this.bname = bname;
    }

    /**
     * @return The land
     */
    public String getLand() {
        return land;
    }

    /**
     * @param land The land
     */
    public void setLand(String land) {
        this.land = land;
    }

}
