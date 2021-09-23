package eziam.tech.skilliassignmentmanpreet.home.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac
 */

public class ImageDetails {
    @SerializedName("comment")
    @Expose
    String description;
    @SerializedName("picture")
    @Expose
    String imageUrl;
    @SerializedName("bitmap")
    @Expose
    Bitmap imageBitmap;
    @SerializedName("_id")
    @Expose
    String id;
    @SerializedName("publishedAt")
    @Expose
    String publishedDateTime;
    @SerializedName("title")
    @Expose
    String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishedDateTime() {
        return publishedDateTime;
    }

    public void setPublishedDateTime(String publishedDateTime) {
        this.publishedDateTime = publishedDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
