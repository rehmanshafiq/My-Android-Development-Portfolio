package contactsdiary.shafiq.com.fragments;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import contactsdiary.shafiq.com.MainActivity;
import contactsdiary.shafiq.com.R;
import contactsdiary.shafiq.com.db_helper.DatabaseHelper;
import contactsdiary.shafiq.com.models.Contact;
import contactsdiary.shafiq.com.utils.InitPermission;
import contactsdiary.shafiq.com.utils.UniversalImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditContactFragment extends Fragment implements ChangePhotoDialog.OnPhotoReceivedListener {

    // Constant
    private static final String TAG = "EditContactFragment";

    // variable
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;

    // Model variable
    private Contact mContact;

    // Widgets
    private EditText mPhoneNumber, mName, mEmail;
    private TextView mHeading;
    private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;

    // This will evade the nullPointer exception when addin to a new bundle from MainActivity
    public EditContactFragment() {
        super();
        setArguments(new Bundle());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        Log.d(TAG, "onCreateView: started.");

        toolbar = view.findViewById(R.id.editContactToolbar);
        mPhoneNumber = view.findViewById(R.id.etContactPhone);
        mName = view.findViewById(R.id.etContactName);
        mEmail = view.findViewById(R.id.etContactEmail);
        mContactImage = view.findViewById(R.id.contactImageView);
        mSelectDevice = view.findViewById(R.id.selectDevice);

        mSelectedImagePath = null;

        // required for the setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // set the heading for toolbar
        mHeading = view.findViewById(R.id.textContactToolbar);
        mHeading.setText(getString(R.string.edit_contact));

        // Get the contact from Bundle
        mContact = getContactFromBundle();
        
        if (mContact != null) {
            init();
        }

        ImageView mBackArrawImageView = view.findViewById(R.id.iv_backArraow);
        mBackArrawImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                // remove previous fragment from back stack {therefore naviating back}
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Save changes to the contact
        ImageView ivCheckMark = view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving the edited contact.");
                //execute the save method for the database.

                if (checkStringIfNull(mName.getText().toString())) {
                    Log.d(TAG, "onClick: saving changes to the contact " + mName.getText().toString());

                    // get database helper and save the contact
                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    Cursor cursor = databaseHelper.getContactID(mContact);

                    int contactID = -1;

                    while (cursor.moveToNext()) {
                        contactID = cursor.getInt(0);
                    }
                    if (contactID > -1) {
                        if (mSelectedImagePath != null) {
                            mContact.setProfileImage(mSelectedImagePath);
                        }
                        mContact.setName(mName.getText().toString());
                        mContact.setPhoneNumber(mPhoneNumber.getText().toString());
                        mContact.setDevice(mSelectDevice.getSelectedItem().toString());
                        mContact.setEmail(mEmail.getText().toString());

                        databaseHelper.updateContact(mContact, contactID);
                        Toast.makeText(getActivity(), "Contact Update", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // initiate the dialog box for choosing an image
        ImageView ivCamera = view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * MAke sure all permissions have been verified before opening the dialog
                 */

                for (int i = 0; i < InitPermission.PERMISSIONS.length; i++) {

                    String[] permission = {InitPermission.PERMISSIONS[i]};
                    if (((MainActivity)getActivity()).checkPermission(permission)) {

                        if (i == InitPermission.PERMISSIONS.length - 1) {

                            Log.d(TAG, "onClick: opening the 'image selection dialog box'.");

                            ChangePhotoDialog dialog = new ChangePhotoDialog();
                            dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(EditContactFragment.this, 0);
                        }

                    } else {

                        ((MainActivity)getActivity()).verifyPermissions(permission);
                    }
                }
            }
        });

        return view;
    }

    /**
     * check if string is null or not
     * @param text
     * @return
     */
    private boolean checkStringIfNull(String text) {
        if (text.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void init() {

        mPhoneNumber.setText(mContact.getPhoneNumber());
        mName.setText(mContact.getName());
        mEmail.setText(mContact.getEmail());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "");

        // Setting the selected device to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.device_options,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectDevice.setAdapter(adapter);

        int position = adapter.getPosition(mContact.getDevice());
        mSelectDevice.setSelection(position);
    }

    /**
     * Retrieve the selected contact from the Bundle (coming from MainActivity)
     * @return
     */
    private Contact getContactFromBundle() {
        Log.d(TAG, "getContactFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            return bundle.getParcelable(getString(R.string.contact));
        } else {
            return null;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contact.");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieves the selected image from the bundle (coming from the ChangePhotoDialog)
     * @param bitmap
     */
    @Override
    public void getBitmapImage(Bitmap bitmap) {
        Log.d(TAG, "getBitmapImage: get the bitmap: " + bitmap);

        // get the bitmap from the ChangePhotoDialog
        if (bitmap != null) {
            // compress the image if you like
            ((MainActivity)getActivity()).comressBitmap(bitmap, 70);
            mContactImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public void getImagePath(String imagePath) {
        Log.d(TAG, "getImagePath: get the image path: " + imagePath);

        if (!imagePath.equals("")) {
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, mContactImage, null, "");
        }
    }

    /**
     * Initialize the OnTextChangeListener for formatting the phone number
     */
    private void initOnTextChangedListener() {

        mPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                mPreviousKeyStroke = keyCode;
                return false;
            }
        });

        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String number = s.toString();

                Log.d(TAG, "afterTextChanged: " + number);

                if (number.length() == 3 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains("(")) {

                    number = String.format("(%s", s.toString().substring(0, 3));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());

                } else if (number.length() == 5 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains(")")){

                    number = String.format("(%s) %s",
                            s.toString().substring(1, 4),
                            s.toString().substring(4, 5));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());

                } else if (number.length() == 10 && mPreviousKeyStroke != KeyEvent.KEYCODE_DEL
                        && !number.contains("-")) {

                    number = String.format("(%s) %s-%s",
                            s.toString().substring(1, 4),
                            s.toString().substring(6, 9),
                            s.toString().substring(9, 10));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());
                }
            }
        });
    }
}
