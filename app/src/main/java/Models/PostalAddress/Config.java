package Models.PostalAddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/4/2016.
 */
public class Config {

    @SerializedName("maxradius")
    @Expose
    private String maxradius;
    @SerializedName("discalc")
    @Expose
    private String discalc;
    @SerializedName("phmin")
    @Expose
    private String phmin;
    @SerializedName("phmax")
    @Expose
    private String phmax;
    @SerializedName("olt")
    @Expose
    private String olt;
    @SerializedName("round")
    @Expose
    private String round;
    @SerializedName("rdc")
    @Expose
    private String rdc;
    @SerializedName("osuccess")
    @Expose
    private String osuccess;
    @SerializedName("ofailed")
    @Expose
    private String ofailed;
    @SerializedName("olwaiting")
    @Expose
    private String olwaiting;
    @SerializedName("waitinglimit")
    @Expose
    private String waitinglimit;
    @SerializedName("oprocess")
    @Expose
    private String oprocess;

    /**
     *
     * @return
     * The maxradius
     */
    public String getMaxradius() {
        return maxradius;
    }

    /**
     *
     * @param maxradius
     * The maxradius
     */
    public void setMaxradius(String maxradius) {
        this.maxradius = maxradius;
    }

    /**
     *
     * @return
     * The discalc
     */
    public String getDiscalc() {
        return discalc;
    }

    /**
     *
     * @param discalc
     * The discalc
     */
    public void setDiscalc(String discalc) {
        this.discalc = discalc;
    }

    /**
     *
     * @return
     * The phmin
     */
    public String getPhmin() {
        return phmin;
    }

    /**
     *
     * @param phmin
     * The phmin
     */
    public void setPhmin(String phmin) {
        this.phmin = phmin;
    }

    /**
     *
     * @return
     * The phmax
     */
    public String getPhmax() {
        return phmax;
    }

    /**
     *
     * @param phmax
     * The phmax
     */
    public void setPhmax(String phmax) {
        this.phmax = phmax;
    }

    /**
     *
     * @return
     * The olt
     */
    public String getOlt() {
        return olt;
    }

    /**
     *
     * @param olt
     * The olt
     */
    public void setOlt(String olt) {
        this.olt = olt;
    }

    /**
     *
     * @return
     * The round
     */
    public String getRound() {
        return round;
    }

    /**
     *
     * @param round
     * The round
     */
    public void setRound(String round) {
        this.round = round;
    }

    /**
     *
     * @return
     * The rdc
     */
    public String getRdc() {
        return rdc;
    }

    /**
     *
     * @param rdc
     * The rdc
     */
    public void setRdc(String rdc) {
        this.rdc = rdc;
    }

    /**
     *
     * @return
     * The osuccess
     */
    public String getOsuccess() {
        return osuccess;
    }

    /**
     *
     * @param osuccess
     * The osuccess
     */
    public void setOsuccess(String osuccess) {
        this.osuccess = osuccess;
    }

    /**
     *
     * @return
     * The ofailed
     */
    public String getOfailed() {
        return ofailed;
    }

    /**
     *
     * @param ofailed
     * The ofailed
     */
    public void setOfailed(String ofailed) {
        this.ofailed = ofailed;
    }

    /**
     *
     * @return
     * The olwaiting
     */
    public String getOlwaiting() {
        return olwaiting;
    }

    /**
     *
     * @param olwaiting
     * The olwaiting
     */
    public void setOlwaiting(String olwaiting) {
        this.olwaiting = olwaiting;
    }

    /**
     *
     * @return
     * The waitinglimit
     */
    public String getWaitinglimit() {
        return waitinglimit;
    }

    /**
     *
     * @param waitinglimit
     * The waitinglimit
     */
    public void setWaitinglimit(String waitinglimit) {
        this.waitinglimit = waitinglimit;
    }

    /**
     *
     * @return
     * The oprocess
     */
    public String getOprocess() {
        return oprocess;
    }

    /**
     *
     * @param oprocess
     * The oprocess
     */
    public void setOprocess(String oprocess) {
        this.oprocess = oprocess;
    }

}