package Models.allDataAtOne;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin768 on 11/7/2016.
 */

public class Driver {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("empid")
    @Expose
    private String empid;
    @SerializedName("vtype")
    @Expose
    private String vtype;
    @SerializedName("vno")
    @Expose
    private String vno;
    @SerializedName("cno")
    @Expose
    private String cno;
    @SerializedName("ano")
    @Expose
    private String ano;
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
     * The empid
     */
    public String getEmpid() {
        return empid;
    }

    /**
     *
     * @param empid
     * The empid
     */
    public void setEmpid(String empid) {
        this.empid = empid;
    }

    /**
     *
     * @return
     * The vtype
     */
    public String getVtype() {
        return vtype;
    }

    /**
     *
     * @param vtype
     * The vtype
     */
    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    /**
     *
     * @return
     * The vno
     */
    public String getVno() {
        return vno;
    }

    /**
     *
     * @param vno
     * The vno
     */
    public void setVno(String vno) {
        this.vno = vno;
    }

    /**
     *
     * @return
     * The cno
     */
    public String getCno() {
        return cno;
    }

    /**
     *
     * @param cno
     * The cno
     */
    public void setCno(String cno) {
        this.cno = cno;
    }

    /**
     *
     * @return
     * The ano
     */
    public String getAno() {
        return ano;
    }

    /**
     *
     * @param ano
     * The ano
     */
    public void setAno(String ano) {
        this.ano = ano;
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
