package ganea.alexandra.userdetailsapp.ui.user_detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import ganea.alexandra.userdetailsapp.R;
import ganea.alexandra.userdetailsapp.entities.ItemUser;
import ganea.alexandra.userdetailsapp.ui.MainActivity;
import ganea.alexandra.userdetailsapp.utils.AppConstants;

public class UserImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_image);


        int position = getIntent().getIntExtra(AppConstants.CURRENT_USER_POSITION, -1);
        if (position == -1) {
            finish();
        }

        ItemUser currentUser = MainActivity.USER_ITEMS.get(position);
        if (currentUser == null) {
            finish();
        }

        if (currentUser.getPicture() != null) {
            setActionImage(currentUser.getPicture().getLarge(), (ImageView) findViewById(R.id.detail_big_image));
        }
    }

    private void setActionImage(String path, final ImageView imageView) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(path)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.user_default_image)
                        .error(R.drawable.user_default_image))
                .into(imageView);
    }
}
