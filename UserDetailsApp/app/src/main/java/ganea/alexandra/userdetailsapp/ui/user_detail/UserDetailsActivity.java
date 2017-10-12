package ganea.alexandra.userdetailsapp.ui.user_detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import ganea.alexandra.userdetailsapp.R;
import ganea.alexandra.userdetailsapp.entities.ItemUser;
import ganea.alexandra.userdetailsapp.ui.MainActivity;
import ganea.alexandra.userdetailsapp.utils.AppConstants;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView faveIcon;
    private ImageView userImage;
    private TextView nameText;

    private LinearLayout phoneContainer;
    private LinearLayout emailContainer;
    private LinearLayout addressContainer;
    private TextView idText;

    private ItemUser currentUser;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        position = getIntent().getIntExtra(AppConstants.CURRENT_USER_POSITION, -1);
        if (position == -1) {
            finish();
        }

        currentUser = MainActivity.USER_ITEMS.get(position);
        if (currentUser == null) {
            finish();
        }

        faveIcon = (ImageView) findViewById(R.id.details_fave);
        userImage = (ImageView) findViewById(R.id.details_user_image);
        nameText = (TextView) findViewById(R.id.details_user_name);
        phoneContainer = (LinearLayout) findViewById(R.id.details_user_phone_number);
        emailContainer = (LinearLayout) findViewById(R.id.details_user_email);
        addressContainer = (LinearLayout) findViewById(R.id.details_user_address);
        idText = (TextView) findViewById(R.id.details_user_id);

        boolean isFaved = currentUser.getFaved();
        setFavImage(isFaved, faveIcon);
        faveIcon.setOnClickListener(this);
        userImage.setOnClickListener(this);

        setProfileImage(currentUser.getPicture().getMedium(), userImage);

        nameText.setText(capitalize(currentUser.getName().getTitle()) + " "
                + capitalize(currentUser.getName().getFirst()) + " "
                + capitalize(currentUser.getName().getLast()));

        setActionImage(R.drawable.ic_phone_black_24dp, (ImageView) phoneContainer.findViewById(R.id.detail_left_image));
        ((TextView) phoneContainer.findViewById(R.id.detail_text_1)).setText(currentUser.getPhone());
        ((TextView) phoneContainer.findViewById(R.id.detail_text_2)).setText("Phone");

        setActionImage(R.drawable.ic_email_black_24dp, (ImageView) emailContainer.findViewById(R.id.detail_left_image));
        setActionImage(R.drawable.ic_border_color_black_24dp, (ImageView) emailContainer.findViewById(R.id.detail_right_image));
        ((TextView) emailContainer.findViewById(R.id.detail_text_1)).setText(currentUser.getEmail());
        ((TextView) emailContainer.findViewById(R.id.detail_text_2)).setText("Email");

        setActionImage(R.drawable.ic_place_black_24dp, (ImageView) addressContainer.findViewById(R.id.detail_left_image));
        setActionImage(R.drawable.ic_directions_black_24dp, (ImageView) addressContainer.findViewById(R.id.detail_right_image));
        String address = currentUser.getLocation().getStreet() + ", " +
                currentUser.getLocation().getCity() + ", " +
                currentUser.getLocation().getState() + ", " +
                currentUser.getLocation().getPostcode();
        ((TextView) addressContainer.findViewById(R.id.detail_text_1)).setText(address);
        ((TextView) addressContainer.findViewById(R.id.detail_text_2)).setText("Address");

        idText.setText("ID: " + currentUser.getId().getName() +
                " " + currentUser.getId().getValue());

        phoneContainer.setOnClickListener(this);
        emailContainer.setOnClickListener(this);
        addressContainer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.details_fave) {
            currentUser.setFaved(!currentUser.getFaved());
            boolean isFaved = currentUser.getFaved();
            setFavImage(isFaved, faveIcon);
        } else if (id == R.id.details_user_image) {
            Intent intent = new Intent(this, UserImageActivity.class);
            intent.putExtra(AppConstants.CURRENT_USER_POSITION, position);
            startActivity(intent);
        } else if (id == R.id.details_user_phone_number) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String phone = currentUser.getPhone();
            String digits = phone.replaceAll("[^0-9.]", "");
            callIntent.setData(Uri.parse("tel:" + digits));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        } else if (id == R.id.details_user_email) {
            sendEmail();
        } else if (id == R.id.details_user_address) {
            openMaps();
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private void openMaps() {
        String address = currentUser.getLocation().getStreet() + ", " +
                currentUser.getLocation().getCity() + ", " +
                currentUser.getLocation().getState() + ", " +
                currentUser.getLocation().getPostcode();
        String param = "geo:0,0?q=" + address;
        Uri gmmIntentUri = Uri.parse(param);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void sendEmail() {
        String emailDest = currentUser.getEmail();
        String emailSubject = "No Subject";

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailDest});
        i.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        try {
            startActivity(Intent.createChooser(i, "Send mail...")); /*send_feedback_button*/
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show(); /*profile_screen_error_noemail_subtitle*/
        }
    }

    private void setProfileImage(String path, final ImageView imageView) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(path == null || path.isEmpty() ? "error" : path)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.user_default_image)
                        .error(R.drawable.user_default_image))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    private void setFavImage(boolean faved, final ImageView imageView) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(faved ? R.drawable.yellow_star : R.drawable.black_star)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.black_star)
                        .error(R.drawable.black_star))
                .into(imageView);
    }

    private void setActionImage(int resId, final ImageView imageView) {
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(resId)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.black_star)
                        .error(R.drawable.black_star))
                .into(imageView);
    }

}
