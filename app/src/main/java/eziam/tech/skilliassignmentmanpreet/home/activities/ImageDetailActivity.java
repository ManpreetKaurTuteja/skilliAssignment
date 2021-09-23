package eziam.tech.skilliassignmentmanpreet.home.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import eziam.tech.skilliassignmentmanpreet.R;
import eziam.tech.skilliassignmentmanpreet.app.SkilliAssignmentApp;
import eziam.tech.skilliassignmentmanpreet.utils.Constants;


public class ImageDetailActivity extends AppCompatActivity {
    SkilliAssignmentApp sApp;
    TextView titleTextview;
    ImageView imageView;
    TextView descriptionTextview;
    ImageButton shareIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        sApp = new SkilliAssignmentApp();
        sApp.setActivity(this);
        titleTextview = (TextView)findViewById(R.id.title_textview);
        descriptionTextview = (TextView)findViewById(R.id.description_textview);
        imageView = (ImageView)findViewById(R.id.imageView);
        shareIcon = (ImageButton)findViewById(R.id.share_icon);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleTextview.setText(bundle.getString(Constants.kKeyForTitle));
            Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra(Constants.kKeyForPictureBitmap);
            String imageUrl = bundle.getString(Constants.kKeyForPictureUrl);
            if (imageUrl.equals(null) || imageUrl.equals("")) {
                imageView.setImageBitmap(bitmap);

            } else {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder_error)
                        .into(imageView);
            }
            descriptionTextview.setText(bundle.getString(Constants.kKeyForDescription));

            shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareText(imageUrl);
                }
            });
        }
    }

    void shareText(String imageUrl) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"Skilli Assigment Image shared by Mnapreet Kaur. | "+ imageUrl);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }
    // Share image
    private void shareImage(Uri imagePath) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }
}
