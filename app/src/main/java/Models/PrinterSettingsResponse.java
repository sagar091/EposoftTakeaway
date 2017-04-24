package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mobo on 8/25/2016.
 */

public class PrinterSettingsResponse {

    @SerializedName("confirm")
    @Expose
    private String confirm;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("collection")
    @Expose
    private String collection;
    @SerializedName("waiting")
    @Expose
    private String waiting;
    @SerializedName("font")
    @Expose
    private String font;
    @SerializedName("takeway_logo")
    @Expose
    private String takewayLogo;
    @SerializedName("paid_logo")
    @Expose
    private String paidLogo;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("autoprint")
    @Expose
    private String autoprint;
    @SerializedName("pmode")
    @Expose
    private String pmode;
    @SerializedName("printcount")
    @Expose
    private String printcount;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("fonto")
    @Expose
    private String fonto;
    @SerializedName("fontd")
    @Expose
    private String fontd;
    @SerializedName("onumber")
    @Expose
    private String onumber;

    /**
     *
     * @return
     * The confirm
     */
    public String getConfirm() {
        return confirm;
    }

    /**
     *
     * @param confirm
     * The confirm
     */
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     *
     * @return
     * The option
     */
    public String getOption() {
        return option;
    }

    /**
     *
     * @param option
     * The option
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     *
     * @return
     * The delivery
     */
    public String getDelivery() {
        return delivery;
    }

    /**
     *
     * @param delivery
     * The delivery
     */
    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    /**
     *
     * @return
     * The collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     * The collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     *
     * @return
     * The waiting
     */
    public String getWaiting() {
        return waiting;
    }

    /**
     *
     * @param waiting
     * The waiting
     */
    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    /**
     *
     * @return
     * The font
     */
    public String getFont() {
        return font;
    }

    /**
     *
     * @param font
     * The font
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     *
     * @return
     * The takewayLogo
     */
    public String getTakewayLogo() {
        return takewayLogo;
    }

    /**
     *
     * @param takewayLogo
     * The takeway_logo
     */
    public void setTakewayLogo(String takewayLogo) {
        this.takewayLogo = takewayLogo;
    }

    /**
     *
     * @return
     * The paidLogo
     */
    public String getPaidLogo() {
        return paidLogo;
    }

    /**
     *
     * @param paidLogo
     * The paid_logo
     */
    public void setPaidLogo(String paidLogo) {
        this.paidLogo = paidLogo;
    }

    /**
     *
     * @return
     * The number
     */
    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The autoprint
     */
    public String getAutoprint() {
        return autoprint;
    }

    /**
     *
     * @param autoprint
     * The autoprint
     */
    public void setAutoprint(String autoprint) {
        this.autoprint = autoprint;
    }

    /**
     *
     * @return
     * The pmode
     */
    public String getPmode() {
        return pmode;
    }

    /**
     *
     * @param pmode
     * The pmode
     */
    public void setPmode(String pmode) {
        this.pmode = pmode;
    }

    /**
     *
     * @return
     * The printcount
     */
    public String getPrintcount() {
        return printcount;
    }

    /**
     *
     * @param printcount
     * The printcount
     */
    public void setPrintcount(String printcount) {
        this.printcount = printcount;
    }

    /**
     *
     * @return
     * The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang
     * The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return
     * The fonto
     */
    public String getFonto() {
        return fonto;
    }

    /**
     *
     * @param fonto
     * The fonto
     */
    public void setFonto(String fonto) {
        this.fonto = fonto;
    }

    /**
     *
     * @return
     * The fontd
     */
    public String getFontd() {
        return fontd;
    }

    /**
     *
     * @param fontd
     * The fontd
     */
    public void setFontd(String fontd) {
        this.fontd = fontd;
    }

    /**
     *
     * @return
     * The onumber
     */
    public String getOnumber() {
        return onumber;
    }

    /**
     *
     * @param onumber
     * The onumber
     */
    public void setOnumber(String onumber) {
        this.onumber = onumber;
    }

}
