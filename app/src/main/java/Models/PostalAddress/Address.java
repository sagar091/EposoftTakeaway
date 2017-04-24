package Models.PostalAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/4/2016.
 */
public class Address {

    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("town")
    @Expose
    private String town;

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
     * The street
     */
    public String getStreet() {
        return street;
    }

    /**
     *
     * @param street
     * The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     *
     * @return
     * The town
     */
    public String getTown() {
        return town;
    }

    /**
     *
     * @param town
     * The town
     */
    public void setTown(String town) {
        this.town = town;
    }

}