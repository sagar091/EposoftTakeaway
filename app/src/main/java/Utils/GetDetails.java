package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import Models.FieldsSetupResponse;
import Models.MinorderResponse;
import Models.ModeResponse;
import Models.SandageResponse;
import WebServices.EposoftApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mobo on 8/3/2016.
 */
public class GetDetails {

    private static final String TAG = GetDetails.class.getSimpleName();
    public static SandageResponse sandageResponse;
    public static FieldsSetupResponse setupResponse;
    public static MinorderResponse minorderResponse;
    public static ModeResponse modeResponse;
    public Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public GetDetails(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public SandageResponse getSandage() {

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface responseCall = retrofit.create(EposoftApiInterface.class);
            final Call<SandageResponse> sandageResponseCall = responseCall.getSandage(id, key, "sandage");
            sandageResponseCall.enqueue(new Callback<SandageResponse>() {
                @Override
                public void onResponse(Call<SandageResponse>s,Response<SandageResponse> response) {
                    Log.e(TAG, "sandage success: " + response.isSuccessful());
                    sandageResponse = response.body();
                    if (response.isSuccessful()) {
                        editor = sharedPreferences.edit();
                        editor.putString("bagprice", sandageResponse.getBag());
                        editor.putString("bankprice", sandageResponse.getBank());
                        editor.commit();
                    }
                }

                @Override
                public void onFailure(Call<SandageResponse>s,Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sandageResponse;
    }


    public FieldsSetupResponse getFieldButtons() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");
            Log.e(TAG, "getFieldButtons: " + id + "  " + key);
            EposoftApiInterface eposoftApiInterface = retrofit.create(EposoftApiInterface.class);
            Call<FieldsSetupResponse> fieldsSetupResponseCall = eposoftApiInterface.checkFields(id, key, "fields");
            fieldsSetupResponseCall.enqueue(new Callback<FieldsSetupResponse>() {
                @Override
                public void onResponse(Call<FieldsSetupResponse>s,Response<FieldsSetupResponse> response) {
                    Log.e(TAG, "button success: " + response.isSuccessful());
                    setupResponse = response.body();


                    if (response.isSuccessful()) {
                        editor = sharedPreferences.edit();
                        editor.putString("cph", response.body().getCph());
                        editor.putString("cfname", response.body().getCfname());
                        editor.putString("clname", response.body().getClname());
                        editor.putString("cemail", response.body().getCemail());

                        editor.putString("dfname", response.body().getDfname());
                        editor.putString("dlname", response.body().getDlname());
                        editor.putString("dho", response.body().getDho());
                        editor.putString("dstreet", response.body().getDstreet());
                        editor.putString("dtown", response.body().getDtown());
                        editor.putString("dpostcode", response.body().getDpostcode());
                        editor.putString("demail", response.body().getDemail());
                        editor.putString("dph", response.body().getDph());

                        editor.putString("oallergy", response.body().getOallergy());
                        editor.putString("eallergy", response.body().getEallergy());

                        editor.commit();
                    }
                }

                @Override
                public void onFailure(Call<FieldsSetupResponse>s,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getFieldButtons: " + e.getLocalizedMessage());
        }
        return setupResponse;
    }

    public MinorderResponse getMinOrder() {
        try {

            Retrofit retrofitget = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofitget.create(EposoftApiInterface.class);
            Call<MinorderResponse> postalResponseCall = apiInterface.minOrder(id, key, "minorder");
            postalResponseCall.enqueue(new Callback<MinorderResponse>() {
                @Override
                public void onResponse(Call<MinorderResponse>s,Response<MinorderResponse> response) {
                    Log.e(TAG, "min order success: " + response.isSuccessful()+" "+response.body().getCollection());
                    minorderResponse = response.body();
                    if (response.isSuccessful()) {
                        editor = sharedPreferences.edit();
                        editor.putString("mincollection", response.body().getCollection());
                        editor.putString("mindelivery", response.body().getDelivery());
                        editor.commit();
                    }
                }

                @Override
                public void onFailure(Call<MinorderResponse>s,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });


        } catch (Exception e) {
            Log.e(TAG, "Exception" + e.getMessage());
        }
        return minorderResponse;
    }

    public ModeResponse getModeDetails() {
        try {

            Retrofit retrofitget = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            String id = sharedPreferences.getString(Constants.SHARE_APPID, "");
            String key = sharedPreferences.getString(Constants.SHARE_APPKEY, "");

            EposoftApiInterface apiInterface = retrofitget.create(EposoftApiInterface.class);
            Call<ModeResponse> postalResponseCall = apiInterface.getMode(id, key, "mode");
            postalResponseCall.enqueue(new Callback<ModeResponse>() {
                @Override
                public void onResponse(Call<ModeResponse>s,Response<ModeResponse> response) {
                    Log.e(TAG, "mode success: " + response.isSuccessful());
                    modeResponse = response.body();
                    if (response.isSuccessful()) {
                        editor = sharedPreferences.edit();
                        editor.putBoolean("mcollection", modeResponse.getCollection());
                        editor.putBoolean("mdelivery", modeResponse.getDelivery());
                        editor.putBoolean("minside", modeResponse.getInside());
                        editor.putBoolean("mtable", modeResponse.getTable());
                        editor.putBoolean("mwholesale", modeResponse.getWholesale());
                        editor.putBoolean("mcash", modeResponse.getCash());
                        editor.putBoolean("mcard", modeResponse.getCard());
                        editor.putBoolean("mcardatshop", modeResponse.getCardatshop());
                        editor.commit();
                    }
                }

                @Override
                public void onFailure(Call<ModeResponse>s,Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });


        } catch (Exception e) {
            Log.e(TAG, "Exception" + e.getMessage());
        }
        return modeResponse;
    }

}
