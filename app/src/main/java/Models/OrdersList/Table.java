package Models.OrdersList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/8/2016.
 */
public class Table {

    @SerializedName("tablename")
    @Expose
    private String tablename;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     *
     * @return
     * The tablename
     */
    public String getTablename() {
        return tablename;
    }

    /**
     *
     * @param tablename
     * The tablename
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
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

}