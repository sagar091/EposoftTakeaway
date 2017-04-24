package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/18/2016.
 */

public class TableResponse {


    @SerializedName("tablename")
    @Expose
    private String tablename;

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

}
