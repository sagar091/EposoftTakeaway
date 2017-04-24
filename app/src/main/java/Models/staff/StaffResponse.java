package Models.staff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aman Singh on 9/15/2016.
 */

public class StaffResponse {
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("list")
    @Expose
    private java.util.List<StaffList> list = new ArrayList<StaffList>();

    /**
     *
     * @return
     * The action
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The list
     */
    public java.util.List<StaffList> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(java.util.List<StaffList> list) {
        this.list = list;
    }
}
