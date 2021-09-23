package eziam.tech.skilliassignmentmanpreet.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import eziam.tech.skilliassignmentmanpreet.R;
import eziam.tech.skilliassignmentmanpreet.app.SkilliAssignmentApp;
import eziam.tech.skilliassignmentmanpreet.home.activities.ImageDetailActivity;
import eziam.tech.skilliassignmentmanpreet.home.activities.MainActivity;
import eziam.tech.skilliassignmentmanpreet.home.models.ImageDetails;
import eziam.tech.skilliassignmentmanpreet.utils.Constants;

/**
 * Created by mac
 */

public class ImageViewAdaptar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    View view;
    List<ImageDetails> imageDetailsList;
    MainActivity context;
    int height;
    SkilliAssignmentApp sApp;

    public ImageViewAdaptar(List<ImageDetails> imageDetailses, MainActivity activity) {
        this.imageDetailsList = imageDetailses;
        this.context = activity;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        height = display.getHeight();
        sApp = context.sApp;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_images_items, parent, false);
        return new ImageViewAdaptar.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        sApp.hideProgress();
        int pos = position;
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        imageViewHolder.imageView.getLayoutParams().height = (int) (height / 2.5);
        final ImageDetails[] imageDetails = {imageDetailsList.get(position)};
        String imageThumbnail = imageDetails[0].getImageUrl();
        if(imageThumbnail.equals(null) || imageThumbnail.equals("")){
            imageViewHolder.imageView.setImageBitmap(imageDetails[0].getImageBitmap());
        }else {
            Picasso.get()
                    .load(imageThumbnail)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder_error)
                    .into(imageViewHolder.imageView);
        }
        imageViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageDetails[0] = imageDetailsList.get(pos);
                Intent intent = new Intent(context, ImageDetailActivity.class);
                intent.putExtra(Constants.kKeyForTitle, imageDetails[0].getTitle());
                intent.putExtra(Constants.kKeyForPictureUrl, imageDetails[0].getImageUrl());
                intent.putExtra(Constants.kKeyForPictureBitmap, imageDetails[0].getImageBitmap());
                intent.putExtra(Constants.kKeyForDescription, imageDetails[0].getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imageDetailsList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public void updateAdaptar(List<ImageDetails> imageDetailses, RecyclerView recyclerView) {
        this.imageDetailsList = imageDetailses;
        this.notifyDataSetChanged();
        recyclerView.getLayoutManager().scrollToPosition(this.imageDetailsList.size() - 1);
    }
}
