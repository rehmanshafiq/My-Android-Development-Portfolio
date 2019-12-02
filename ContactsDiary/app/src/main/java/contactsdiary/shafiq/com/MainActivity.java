package contactsdiary.shafiq.com;



import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;

import contactsdiary.shafiq.com.fragments.AddContactFragment;
import contactsdiary.shafiq.com.fragments.ContactFragment;
import contactsdiary.shafiq.com.fragments.EditContactFragment;
import contactsdiary.shafiq.com.fragments.ViewContactsFragment;
import contactsdiary.shafiq.com.models.Contact;
import contactsdiary.shafiq.com.utils.UniversalImageLoader;

public class MainActivity extends AppCompatActivity implements 
        ViewContactsFragment.OnContactSelectedListener,
        ContactFragment.OnEditContactListener,
        ViewContactsFragment.OnAddContactListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageLoader();

        initFragment();
    }

    /**
     * initialize the first fragment (ViewContactFragment)
    * */

    private void initFragment() {

        ViewContactsFragment fragment = new ViewContactsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // replace whatever is in fragment_container view with this fragment
        // and add the transaction back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void initImageLoader() {

        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * Compress a bitmap by the "quality"
     * Quality can be anywhere from 1-100 : 100 being the highest quality.
     * @param bitmap
     * @param quality
     * @return bitmap
     */
    public Bitmap comressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);

        return bitmap;
    }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     * @param permissions
     */
    public void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: asking user for permissions.");
        ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE);
    }

    /**
     * Check to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAY BE CHECKED AT A TIME
     * @param permissions
     * @return true or false
     */
    public boolean checkPermission(String[] permissions) {
        Log.d(TAG, "checkPermission: checking permission for: " + permissions[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(MainActivity.this, permissions[0]);

        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "checkPermission: \n Permissions was not granted for: " + permissions[0]);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: requestCode: " + requestCode);

        switch (requestCode) {
            case REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access: "
                                + permissions[i]);
                    } else {
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void onContactSelected(Contact contact) {
        Log.d(TAG, "onContactSelected: contact selected from "
                + getString(R.string.view_contacts_fragment) + " " + contact.getName());

        ContactFragment fragment = new ContactFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(getString(R.string.contact_fragment));
        transaction.commit();
    }

    @Override
    public void onEditContactSelected(Contact contact) {
        Log.d(TAG, "onEditContactSelected: contact selected from "
                + getString(R.string.edit_contact_fragment) + " " + contact.getName());

        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact), contact);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(getString(R.string.edit_contact_fragment));
        transaction.commit();
    }

    @Override
    public void onAddContact() {
        Log.d(TAG, "onAddContact: navigating to " + getString(R.string.add_contact_fragment));

        AddContactFragment fragment = new AddContactFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(getString(R.string.add_contact_fragment));
        transaction.commit();
    }
}
