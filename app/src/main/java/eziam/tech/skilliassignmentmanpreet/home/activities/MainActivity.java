package eziam.tech.skilliassignmentmanpreet.home.activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eziam.tech.skilliassignmentmanpreet.R;
import eziam.tech.skilliassignmentmanpreet.app.SkilliAssignmentApp;
import eziam.tech.skilliassignmentmanpreet.home.adapters.ImageViewAdaptar;
import eziam.tech.skilliassignmentmanpreet.home.models.ImageDetails;
import eziam.tech.skilliassignmentmanpreet.home.models.ImagesResponse;
import eziam.tech.skilliassignmentmanpreet.utils.JsonUtils;
import eziam.tech.skilliassignmentmanpreet.utils.PermissionUtils;
import eziam.tech.skilliassignmentmanpreet.volley.VolleyMultipartRequest;
import eziam.tech.skilliassignmentmanpreet.volley.VolleySingleton;

public class MainActivity extends AppCompatActivity {
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.CAMERA };
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public SkilliAssignmentApp sApp;
    ImageViewAdaptar adaptar;
    RecyclerView content_listview;
    private RecyclerView.LayoutManager layoutManager;
    ImageButton cameraButton;
    List<ImageDetails> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sApp = new SkilliAssignmentApp();
        sApp.setActivity(this);
        imagesList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        cameraButton = (ImageButton) findViewById(R.id.camera_icon);
        content_listview = (RecyclerView) findViewById(R.id.pages_content_listview);
        content_listview.setLayoutManager(layoutManager);
        fetchImageList();
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
   void startCamera(){
       Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       try {
           startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
       } catch (ActivityNotFoundException e) {
           // display error state to the user
       }
    }
    public void openCamera(View view) {
        if(PermissionUtils.requestPermission(this,REQUEST_CODE_ASK_PERMISSIONS,REQUIRED_SDK_PERMISSIONS)) {
           startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void fetchImageList() {
        if (sApp.isConnected()) {
            sApp.showProgress(MainActivity.this, "Loading...");
            String url = "https://jsonkeeper.com/b/VGOL";
            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.GET, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    String resultResponse = new String(response.data);
                    sApp.hideProgress();
                    try {
                        JSONArray jsonArr = new JSONArray(resultResponse);
//                        List<ImageDetails> dataList = new ArrayList<>();
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            ImageDetails imageDetails = new ImageDetails();
                            imageDetails.setId(jsonObj.getString("_id"));
                            imageDetails.setTitle(jsonObj.getString("title"));
                            imageDetails.setDescription(jsonObj.getString("comment"));
                            imageDetails.setImageUrl(jsonObj.getString("picture"));
                            imageDetails.setPublishedDateTime(jsonObj.getString("publishedAt"));
                            imagesList.add(imageDetails);
                        }
                        setImageAdapter(imagesList);
                    } catch (Exception e) {
                        sApp.showToast(MainActivity.this, e.getMessage());
//                                    sApp.showToast(MainActivity.this, "Server is down. We are working on it. Kindly try after some time.");
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    sApp.hideProgress();
                    NetworkResponse response = error.networkResponse;
                    String json = "Try again";
                    if (response != null) {
                        json = new String(response.data);
                    }
                    sApp.showToast(MainActivity.this, json);
                }
            });
            VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(multipartRequest);
        }
    }

    void setImageAdapter(List<ImageDetails> dataList) {
        if (adaptar == null) {
            adaptar = new ImageViewAdaptar(dataList, this);
            content_listview.setAdapter(adaptar);
        } else {
            adaptar.updateAdaptar(dataList, content_listview);
        }
        adaptar.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageDetails imageDetails = new ImageDetails();
            imageDetails.setImageBitmap(imageBitmap);
            imageDetails.setImageUrl("");
            imageDetails.setDescription("Published by Manpreet from camera.");
            imagesList.add(imageDetails);
            adaptar.updateAdaptar(imagesList, content_listview);
            adaptar.notifyDataSetChanged();
//            imageView.setImageBitmap(imageBitmap);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                startCamera();
                break;
        }
    }
}