package eziam.tech.skilliassignmentmanpreet.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import eziam.tech.skilliassignmentmanpreet.R;

public class SkilliAssignmentApp extends MultiDexApplication {

    AppCompatActivity activity;
    private static SkilliAssignmentApp sharedApplication;
    ProgressDialog progressDialog;
    String deviceID = "";


    @Override
    public void onCreate() {
        super.onCreate();
        sharedApplication = this;
        deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("Deviceid : ", deviceID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static SkilliAssignmentApp getSharedApplication() {
        return sharedApplication;
    }

    public AppCompatActivity getActivity() {
        return this.activity;
    }

    public String getDeviceID() {
        return deviceID;
    }


    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected)
            Toast.makeText(this, getActivity().getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        return isConnected;
    }
    public void showProgress(Context context, String... progressMessage) {
//    public void showProgress(Context context) {
        if (this.progressDialog == null || !this.progressDialog.isShowing() && isConnected()) {
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setCancelable(false);
            this.progressDialog.setMessage(progressMessage.length == 0 ? "" : progressMessage[0]);
            this.progressDialog.show();
        }
    }

    public void hideProgress() {
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
            this.progressDialog = null;
        }
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

   public static void setSharedApplication(SkilliAssignmentApp sharedApplication) {
        SkilliAssignmentApp.sharedApplication = sharedApplication;
    }
    public void startNewActivityAndCloseOld(Activity pPresentActivity, Class pNewActivityClass) {
        Intent intent = new Intent(pPresentActivity, pNewActivityClass);
        pPresentActivity.startActivity(intent);
        pPresentActivity.finish();
    }
}

