package pl.marchuck.myagh.tabs.skos;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.marchuck.myagh.R;
import pl.marchuck.myagh.utils.JsoupProxy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Lukasz Marczak
 * @since 11.05.16.
 */
public class SkosPresenter {
    public static final String TAG = SkosPresenter.class.getSimpleName();


    @Bind(R.id.input_layout_password)
    TextInputLayout passwordLayout;

    @Bind(R.id.input_layout_username)
    TextInputLayout usernameLayout;

    @Bind(R.id.input_username)
    TextInputEditText editTextLogin;

    @Bind(R.id.input_password)
    TextInputEditText editTextPassword;

    @Bind(R.id.virtual_dean_login_button)
    Button loginButton;

    @OnClick(R.id.virtual_dean_login_button)
    public void login() {

        String username = editTextLogin.getText().toString();
        String pass = editTextPassword.getText().toString();
        Log.i(TAG, "login: " + username + ", " + pass);
        //if (username.isEmpty()) usernameLayout.setErrorEnabled(true);
        //if (pass.isEmpty()) passwordLayout.setErrorEnabled(true);

      //  if (validatedFields(usernameLayout, editTextLogin, "")
          //      || validatedFields(passwordLayout, editTextPassword, "Password"))
            invokeLoginRequest(username, pass);
    }

    private boolean validatedFields(TextInputLayout layout, TextInputEditText edittext, String name) {
        if (edittext.getText().toString().trim().isEmpty()) {
            layout.setError(name + " is empty");
            edittext.requestFocus();
            return false;
        } else {
            layout.setErrorEnabled(false);
        }
        return true;
    }

    interface SkosAPI {
        @GET("search/")
        Call<ResponseBody> login(@Query("nazwisko") String  nazwisko,
                                 @Query("imie") String  imie, @Query("tytul") String  tytul);
    }

    private void invokeLoginRequest(String username, String pass) {

        Snackbar.make(loginButton,"Under development",Snackbar.LENGTH_SHORT).show();

        Log.d(TAG, "invokeLoginRequest: " + username + "," + pass);
      //  Snackbar.make(loginButton, "User logged!", Snackbar.LENGTH_SHORT).show();

        String domain = "https://skos.agh.edu.pl/";

        new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SkosAPI.class)
                .login("Tutaj","","")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        Log.i(TAG, "onResponse: " + call.request().toString());
                        Log.e(TAG, "header: " + call.request().headers());
                        String s = call.request().url().toString();
                        String a = call.request().method();

                        Log.i(TAG, "request url: " + s);
                        Log.i(TAG, "request method: " + a);

                        Log.d(TAG, "isSuccessFull: " + response.isSuccessful());
                        Log.d(TAG, "response: " + response.code());
                        try {
                            String str = response.body().string();
                            Document papaDoc = Jsoup.parse(str);
                            Elements all = papaDoc.getAllElements();
                            for (Element aa : all) {
                                Log.i(TAG, "\n\nonNext element:");
                                JsoupProxy.printElement(TAG, aa);
                            }
                        } catch (Exception c) {
                            Log.e(TAG, "error: " + c.getMessage());
                        }
                        moveToWebView(response);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
    }

    private void moveToWebView(Response<ResponseBody> response) {

    }

    private String getStringBody(String login, String pass, boolean isStudent) {
        String who = isStudent ? "student" : "dydaktyk";
        return "ctl00_ctl00_ScriptManager1_HiddenField: \"\"\n" +
                "__EVENTTARGET: \"\"\n" +
                "__EVENTARGUMENT: \"\"\n" +
                "__VIEWSTATE: \"/wEPDwUKMTc3NTQ1OTc2NA8WAh4DaGFzZRYCZg9kFgJmD2QWAgIBD2QWBAICD2QWAgIBD2QWAgIBD2QWAgICDxQrAAIUKwACDxYEHgtfIURhdGFCb3VuZGceF0VuYWJsZUFqYXhTa2luUmVuZGVyaW5naGQPFCsAARQrAAIPFggeBFRleHQFHVd5c3p1a2l3YXJrYSBwb2R6aWHFgnUgZ29kemluHgtOYXZpZ2F0ZVVybAUTL1BvZHpHb2R6aW5Ub2suYXNweB4FVmFsdWUFHVd5c3p1a2l3YXJrYSBwb2R6aWHFgnUgZ29kemluHgdUb29sVGlwBR1XeXN6dWtpd2Fya2EgcG9kemlhxYJ1IGdvZHppbmRkDxQrAQFmFgEFdFRlbGVyaWsuV2ViLlVJLlJhZE1lbnVJdGVtLCBUZWxlcmlrLldlYi5VSSwgVmVyc2lvbj0yMDEyLjMuMTIwNS4zNSwgQ3VsdHVyZT1uZXV0cmFsLCBQdWJsaWNLZXlUb2tlbj0xMjFmYWU3ODE2NWJhM2Q0ZBYCZg8PFggfAwUdV3lzenVraXdhcmthIHBvZHppYcWCdSBnb2R6aW4fBAUTL1BvZHpHb2R6aW5Ub2suYXNweB8FBR1XeXN6dWtpd2Fya2EgcG9kemlhxYJ1IGdvZHppbh8GBR1XeXN6dWtpd2Fya2EgcG9kemlhxYJ1IGdvZHppbmRkAgQPZBYCAgMPZBYSAgEPFgIeCWlubmVyaHRtbAUtV2lydHVhbG5hIFVjemVsbmlhPCEtLSBzdGF0dXM6IDI3Njg0OTAxNiAtLT4gZAINDw8WAh4ETW9kZQsqJVN5c3RlbS5XZWIuVUkuV2ViQ29udHJvbHMuVGV4dEJveE1vZGUCZGQCFQ8PFgIfAwUZT2R6eXNraXdhbmllIGhhc8WCYTxiciAvPmRkAhcPZBYCAgMPEGQPFgJmAgEWAgUHc3R1ZGVudAUIZHlkYWt0eWsWAWZkAhkPZBYEAgEPDxYCHwMFNDxiciAvPkx1YiB6YWxvZ3VqIHNpxJkgamFrbyBzdHVkZW50IHByemV6IE9mZmljZTM2NTpkZAIDDw8WAh8DBQhQcnplamTFumRkAhsPDxYEHwMFGFNlcndpcyBBYnNvbHdlbnTDs3c8YnIvPh4HVmlzaWJsZWhkZAIfDw8WAh8JaGRkAiEPDxYCHwloZBYGAgEPDxYCHwNkZGQCAw8PFgIfAwUGQW51bHVqZGQCBQ8PFgIfAwUHUG9iaWVyemRkAiMPDxYCHwMFI1fFgsSFY3ogcmVrbGFtxJkgYXBsaWthY2ppIG1vYmlsbmVqZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFSmN0bDAwJGN0bDAwJFRvcE1lbnVQbGFjZUhvbGRlciRUb3BNZW51Q29udGVudFBsYWNlSG9sZGVyJE1lbnVUb3AzJG1lbnVUb3AzBbyedxiXDCVLknKokrK5HcBi2aY=\"\n" +
                "__VIEWSTATEGENERATOR: \"BBDE9B47\"\n" +
                "ctl00_ctl00_TopMenuPlaceHolder_TopMenuContentPlaceHolder_MenuTop3_menuTop3_ClientState: \"\"\n" +
                "ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtIdent: \"" + login + "\"\n" +
                "ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$txtHaslo: \"" + pass + "\"\n" +
                "ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$butLoguj: \"Zaloguj\"\n" +
                "ctl00$ctl00$ContentPlaceHolder$MiddleContentPlaceHolder$rbKto: \"" + who + "\"";
    }


    public SkosPresenter(View view, Context ctx) {
        ButterKnife.bind(this, view);

    }

    public RequestBody getRequestBody(String strRequestBody) {
        return RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
    }
}
