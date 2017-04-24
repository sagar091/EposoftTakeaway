package Models.allDataAtOne;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import Models.Addons.Addon;
import Models.CouponItemsList;
import Models.DiscountResponse;
import Models.FieldsSetupResponse;
import Models.MinorderResponse;
import Models.ModeResponse;
import Models.SandageResponse;
import Models.TableResponse;
import Models.staff.StaffResponse;

/**
 * Created by Aman Singh on 10/26/2016.
 */

public class AllDataResponse {


    @SerializedName("menu")
    @Expose
    private List<Menu> menu = new ArrayList<Menu>();
    @SerializedName("addon")
    @Expose
    private List<Addon> addon = new ArrayList<Addon>();

    /*********************************************************************************************************************/

    @SerializedName("mode")
    @Expose
    private ModeResponse mode;
    @SerializedName("fields")
    @Expose
    private FieldsSetupResponse fields;
    @SerializedName("tables")
    @Expose
    private List<TableResponse> tables = new ArrayList<TableResponse>();
    @SerializedName("printersetting")
    @Expose
    private Printersetting printersetting;
    @SerializedName("coupon")
    @Expose
    private List<CouponItemsList> couponList = new ArrayList<CouponItemsList>();
    @SerializedName("sandage")
    @Expose
    private SandageResponse sandage;


    @SerializedName("orderlist")
    @Expose
    private List<Orderlist> orderlist = new ArrayList<Orderlist>();


    @SerializedName("minorder")
    @Expose
    private MinorderResponse minorder;

    @SerializedName("discount")
    @Expose
    private DiscountResponse discount;

    @SerializedName("staff")
    @Expose
    private StaffResponse staff;


    @SerializedName("extra")
    @Expose
    private List<Extra> extra = new ArrayList<Extra>();

    @SerializedName("changes")
    @Expose
    private List<Change> changes = new ArrayList<Change>();


    @SerializedName("driver")
    @Expose
    private List<Driver> driver = new ArrayList<Driver>();

    public List<Driver> getDriver() {
        return driver;
    }

    /**
     *
     * @param driver
     * The driver
     */
    public void setDriver(List<Driver> driver) {
        this.driver = driver;
    }

    public List<Change> getChanges() {
        return changes;
    }

    /**
     *
     * @param changes
     * The changes
     */
    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }

    /**
     *
     * @return
     * The extra
     */
    public List<Extra> getExtra() {
        return extra;
    }

    /**
     *
     * @param extra
     * The extra
     */
    public void setExtra(List<Extra> extra) {
        this.extra = extra;
    }
    /**
     *
     * @return
     * The staff
     */
    public StaffResponse getStaff() {
        return staff;
    }

    /**
     *
     * @param staff
     * The staff
     */
    public void setStaff(StaffResponse staff) {
        this.staff = staff;
    }

    /**
     *
     * @return
     * The discount
     */
    public DiscountResponse getDiscount() {
        return discount;
    }

    /**
     *
     * @param discount
     * The discount
     */
    public void setDiscount(DiscountResponse discount) {
        this.discount = discount;
    }
    /**
     *
     * @return
     * The minorder
     */
    public MinorderResponse getMinorder() {
        return minorder;
    }

    /**
     *
     * @param minorder
     * The minorder
     */
    public void setMinorder(MinorderResponse minorder) {
        this.minorder = minorder;
    }

    /**
     *
     * @return
     * The orderlist
     */
    public List<Orderlist> getOrderlist() {
        return orderlist;
    }

    /**
     *
     * @param orderlist
     * The orderlist
     */
    public void setOrderlist(List<Orderlist> orderlist) {
        this.orderlist = orderlist;
    }

    /**
     *
     * @return
     * The mode
     */
    public ModeResponse getMode() {
        return mode;
    }

    /**
     *
     * @param mode
     * The mode
     */
    public void setMode(ModeResponse mode) {
        this.mode = mode;
    }

    /**
     *
     * @return
     * The fields
     */
    public FieldsSetupResponse getFields() {
        return fields;
    }

    /**
     *
     * @param fields
     * The fields
     */
    public void setFields(FieldsSetupResponse fields) {
        this.fields = fields;
    }

    /**
     *
     * @return
     * The tables
     */
    public List<TableResponse> getTables() {
        return tables;
    }

    /**
     *
     * @param tables
     * The tables
     */
    public void setTables(List<TableResponse> tables) {
        this.tables = tables;
    }

    /**
     *
     * @return
     * The printersetting
     */
    public Printersetting getPrintersetting() {
        return printersetting;
    }

    /**
     *
     * @param printersetting
     * The printersetting
     */
    public void setPrintersetting(Printersetting printersetting) {
        this.printersetting = printersetting;
    }

    /**
     *
     * @return
     * The coupon
     */
    public List<CouponItemsList> getCouponList() {
        return couponList;
    }

    /**
     *
     * @param couponList
     * The coupon
     */
    public void setCouponList(List<CouponItemsList> couponList) {
        this.couponList = couponList;
    }

    /**
     *
     * @return
     * The sandage
     */
    public SandageResponse getSandage() {
        return sandage;
    }

    /**
     *
     * @param sandage
     * The sandage
     */
    public void setSandage(SandageResponse sandage) {
        this.sandage = sandage;
    }

    /*********************************************************************************************************************/

    /**
     *
     * @return
     * The addon
     */
    public List<Addon> getAddon() {
        return addon;
    }

    /**
     *
     * @param addon
     * The addon
     */
    public void setAddon(List<Addon> addon) {
        this.addon = addon;
    }
    /**
     *
     * @return
     * The menu
     */
    public List<Menu> getMenu() {
        return menu;
    }

    /**
     *
     * @param menu
     * The menu
     */
    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
