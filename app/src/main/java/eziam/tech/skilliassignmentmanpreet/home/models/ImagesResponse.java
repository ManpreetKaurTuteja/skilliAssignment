package eziam.tech.skilliassignmentmanpreet.home.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import eziam.tech.skilliassignmentmanpreet.home.models.ImageDetails;

/**
 * Created by mac on 06/04/18.
 */

public class ImagesResponse {

    List<ImageDetails> dataList;

    public List<ImageDetails> getDataList() {
        return dataList;
    }

    public void setDataList(List<ImageDetails> dataList) {
        this.dataList = dataList;
    }
}
