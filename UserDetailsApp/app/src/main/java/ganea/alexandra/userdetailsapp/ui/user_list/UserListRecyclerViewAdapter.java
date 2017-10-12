package ganea.alexandra.userdetailsapp.ui.user_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ganea.alexandra.userdetailsapp.R;
import ganea.alexandra.userdetailsapp.entities.ItemUser;
import ganea.alexandra.userdetailsapp.ui.user_detail.UserDetailsActivity;
import ganea.alexandra.userdetailsapp.utils.AppConstants;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class UserListRecyclerViewAdapter extends RecyclerView.Adapter<UserListRecyclerViewAdapter.ViewHolder> {

    private final List<ItemUser> mValues;
    private Context mContext;

    public UserListRecyclerViewAdapter(List<ItemUser> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mNameTextView.setText(capitalize(mValues.get(position).getName().getTitle())
                + " " + capitalize(mValues.get(position).getName().getFirst())
                + " " + capitalize(mValues.get(position).getName().getLast()));
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.US);
        try {
            Date dob = dateFormat.parse(holder.mItem.getDob());
            int years = getDiffYears(dob, currentDate);
            holder.mAgeNatTextView.setText(years + " years old from " + mValues.get(position).getNat());
        } catch (Exception e) {
            e.printStackTrace();
            holder.mAgeNatTextView.setText(" from " + mValues.get(position).getNat());
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserDetailsActivity.class);
                intent.putExtra(AppConstants.CURRENT_USER_POSITION, position);
                mContext.startActivity(intent);
            }
        });

        holder.mFaveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mItem.setFaved(!holder.mItem.getFaved());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        String path = holder.mItem.getPicture().getMedium();
        if (path == null || path.isEmpty()) {
            path = "error";
        }
        setProfileImage(path, holder.mUserImageView);
        setFavImage(holder.mItem.getFaved(), holder.mFaveImageView);
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mUserImageView;
        public final TextView mNameTextView;
        public final TextView mAgeNatTextView;
        public final ImageView mFaveImageView;
        public ItemUser mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserImageView = (ImageView) view.findViewById(R.id.user_image);
            mNameTextView = (TextView) view.findViewById(R.id.user_name);
            mAgeNatTextView = (TextView) view.findViewById(R.id.user_age_country);
            mFaveImageView = (ImageView) view.findViewById(R.id.user_fav);
        }

        @Override
        public String toString() {
            return super.toString() + " " + mNameTextView.getText() + ", " + mAgeNatTextView.getText();
        }
    }

    private void setProfileImage(String path, final ImageView imageView) {
        Glide.with(mContext.getApplicationContext())
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
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    private void setFavImage(boolean faved, final ImageView imageView) {
        Glide.with(mContext.getApplicationContext())
                .asBitmap()
                .load(faved ? R.drawable.yellow_star : R.drawable.black_star)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.black_star)
                        .error(R.drawable.black_star))
                .into(imageView);
    }
}
