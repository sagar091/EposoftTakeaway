package Models.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aman Singh on 9/15/2016.
 */

public class StaffList {
    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("display")
    @Expose
    private String display;

    /**
     *
     * @return
     * The _0
     */
    public String get0() {
        return _0;
    }

    /**
     *
     * @param _0
     * The 0
     */
    public void set0(String _0) {
        this._0 = _0;
    }

    /**
     *
     * @return
     * The _1
     */
    public String get1() {
        return _1;
    }

    /**
     *
     * @param _1
     * The 1
     */
    public void set1(String _1) {
        this._1 = _1;
    }

    /**
     *
     * @return
     * The _2
     */
    public String get2() {
        return _2;
    }

    /**
     *
     * @param _2
     * The 2
     */
    public void set2(String _2) {
        this._2 = _2;
    }

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
     * The display
     */
    public String getDisplay() {
        return display;
    }

    /**
     *
     * @param display
     * The display
     */
    public void setDisplay(String display) {
        this.display = display;
    }
}
