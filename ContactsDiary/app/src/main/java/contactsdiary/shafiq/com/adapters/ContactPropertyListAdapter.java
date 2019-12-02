package contactsdiary.shafiq.com.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import contactsdiary.shafiq.com.MainActivity;
import contactsdiary.shafiq.com.R;
import contactsdiary.shafiq.com.models.Contact;
import contactsdiary.shafiq.com.utils.InitPermission;

public class ContactPropertyListAdapter extends ArrayAdapter<String> {

    private static final String TAG = "ContctProprtyListAdptr";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;

    public ContactPropertyListAdapter(@NonNull Context context, int resource, @NonNull List<String> properties) {
        super(context, resource, properties);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        mContext = context;
        mProperties = properties;
    }

    // ViewHolder to cache views
    private static class ViewHolder {
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;
    }
    // *******************************************************

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /**
         ******************* ViewHolder Build Pattern **************
         **/
        final ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();


            // ****************** Stuff to change ********************
            holder.property = convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = convertView.findViewById(R.id.iconLeftCardView);
            // *******************************************************

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        // ****************** Stuff to change ********************
        final String property = getItem(position);
        holder.property.setText(property);

        // check if an email or phone number
        if (property.contains("@") ) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email",
                    null, mContext.getPackageName()));

            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: opening email.");

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {property});
                    mContext.startActivity(emailIntent);
                }
            });

        } else if ((property.length() != 0)) {
            // Phone call
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_phone",
                    null, mContext.getPackageName()));

            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MainActivity)mContext).checkPermission(InitPermission.PHONE_PERMISSIONS)) {
                        Log.d(TAG, "onClick: initiating phone call...");

                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        mContext.startActivity(callIntent);

                    } else {

                        ((MainActivity)mContext).verifyPermissions(InitPermission.PHONE_PERMISSIONS);
                    }
                }
            });

            // Setup the icon for sending text messages
            holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_message",
                    null, mContext.getPackageName()));
            holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: initiating text message.....");

                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", property, null));
                    mContext.startActivity(smsIntent);
                }
            });
        }
        // *******************************************************

        return convertView;
    }
}
