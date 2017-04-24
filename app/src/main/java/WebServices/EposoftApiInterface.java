package WebServices;

import java.util.List;

import Models.Addons.Addon;
import Models.Addons.AddonResponse;
import Models.AutoLoginResponse;
import Models.CategoryResponse;
import Models.CloseTableResponse;
import Models.CouponDiscountResponse;
import Models.CouponItemsList;
import Models.DiscountResponse;
import Models.DriverSndResponse;
import Models.FieldsSetupResponse;
import Models.HiddenResponse;
import Models.ItemResponse;
import Models.KeyActivationResponse;
import Models.KeyGenerationResponse;
import Models.MinorderResponse;
import Models.ModeResponse;
import Models.OrdersList.GetOrderResponse;
import Models.PlaceOrder.MakeOrder;
import Models.PlaceOrder.OrderResponse;
import Models.PostalAddress.PostalResponse;
import Models.PrinterSettingsResponse;
import Models.SandageResponse;
import Models.SubcategoryResponse;
import Models.TableResponse;
import Models.allDataAtOne.AllDataResponse;
import Models.staff.StaffResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Mobo on 7/25/2016.
 */
public interface EposoftApiInterface {

    @GET("/index.php")
    Call<List<CategoryResponse>> getCategory(@Query("app_id") String appid,
                                             @Query("app_key") String appkey,
                                             @Query("request") String request);

    @GET("/index.php")
    Call<List<SubcategoryResponse>> getSubCategory(@Query("app_id") String appid,
                                                   @Query("app_key") String appkey,
                                                   @Query("request") String request,
                                                   @Query("cid") String cid);

    @GET("/index.php")
    Call<List<ItemResponse>> getItem(@Query("app_id") String appid,
                                     @Query("app_key") String appkey,
                                     @Query("request") String request,
                                     @Query("sid") String cid);

    @GET("/index.php")
    Call<AddonResponse> getAddon(@Query("app_id") String appid,
                                 @Query("app_key") String appkey,
                                 @Query("request") String request,
                                 @Query("aid") String cid);

    @GET("/index.php")
    Call<List<Addon>> getAllAddon(@Query("app_id") String appid,
                                  @Query("app_key") String appkey,
                                  @Query("request") String request);


    @GET("/index.php")
    Call<List<ItemResponse>> getSearchedItem(@Query("app_id") String appid,
                                             @Query("app_key") String appkey,
                                             @Query("request") String request,
                                             @Query("key") String cid);

    @GET("/index.php")
    Call<SandageResponse> getSandage(@Query("app_id") String appid,
                                     @Query("app_key") String appkey,
                                     @Query("request") String request);

    @GET("/index.php")
    Call<DiscountResponse> getDiscount(@Query("app_id") String appid,
                                       @Query("app_key") String appkey,
                                       @Query("request") String request);

    @GET("/index.php")
    Call<StaffResponse> getStaff(@Query("app_id") String appid,
                                 @Query("app_key") String appkey,
                                 @Query("request") String request);


    @GET("/index.php")
    Call<PostalResponse> checkPostal(@Query("app_id") String id,
                                     @Query("app_key") String amount,
                                     @Query("request") String mount,
                                     @Query("postcode") String test);

    @POST("/index.php")
    Call<OrderResponse> postOrder(@Body MakeOrder postData);

    @GET("/index.php")
    Call<FieldsSetupResponse> checkFields(@Query("app_id") String id,
                                          @Query("app_key") String amount,
                                          @Query("request") String mount);

    @GET("/index.php")
    Call<List<GetOrderResponse>> getOrderList(@Query("app_id") String id,
                                              @Query("app_key") String amount,
                                              @Query("request") String mount);

    @GET("/index.php")
    Call<MinorderResponse> minOrder(@Query("app_id") String id,
                                    @Query("app_key") String amount,
                                    @Query("request") String mount);

    @GET("/index.php")
    Call<List<TableResponse>> getTable(@Query("app_id") String id,
                                       @Query("app_key") String amount,
                                       @Query("request") String mount);

    @GET("/index.php")
    Call<PrinterSettingsResponse> getPrinterSettings(@Query("app_id") String id,
                                                     @Query("app_key") String amount,
                                                     @Query("request") String mount);

    @GET("/index.php")
    Call<ModeResponse> getMode(@Query("app_id") String id,
                               @Query("app_key") String amount,
                               @Query("request") String mount);

    @GET("/index.php")
    Call<CouponDiscountResponse> getCouponDiscount(@Query("app_id") String appid,
                                                   @Query("app_key") String appkey,
                                                   @Query("request") String request,
                                                   @Query("code") String key);

    @GET("/index.php")
    Call<List<CouponItemsList>> getCouponList(@Query("app_id") String id,
                                              @Query("app_key") String amount,
                                              @Query("request") String mount);

    @GET("/index.php")
    Call<HiddenResponse> changeStatus(@Query("app_id") String id,
                                      @Query("app_key") String key,
                                      @Query("request") String req,
                                      @Query("oid") String oid,
                                      @Query("status") String status);


    @GET("/index.php")
    Call<HiddenResponse> hideOrder(@Query("app_id") String id,
                                   @Query("app_key") String key,
                                   @Query("request") String req,
                                   @Query("oid") String oid);

    @GET("/index.php")
    Call<AutoLoginResponse> autoLoginUrl(@Query("app_id") String id,
                                         @Query("app_key") String key,
                                         @Query("request") String req);

    @GET("/index.php")
    Call<KeyActivationResponse> keyActivation(@Query("app_id") String id,
                                              @Query("app_key") String key,
                                              @Query("request") String req);

    @GET("/index.php")
    Call<KeyGenerationResponse> keyGeneration(@Query("request") String req);


    @GET("/index.php")
    Call<AllDataResponse> getAllSynced(@Query("app_id") String app, @Query("app_key") String key, @Query("request") String req);

    @GET("/index.php")
    Call<DriverSndResponse> sendDriver(@Query("app_id")String id,
                                       @Query("app_key") String key,
                                       @Query("request") String req,
                                       @Query("oid") String oid,
                                       @Query("did") String did);

    @GET("/index.php")
    Call<CloseTableResponse> closeTable(@Query("app_id")String id,
                                        @Query("app_key") String key,
                                        @Query("request") String req,
                                        @Query("tablename")String tab,
                                        @Query("pmode") String pmode);

}
