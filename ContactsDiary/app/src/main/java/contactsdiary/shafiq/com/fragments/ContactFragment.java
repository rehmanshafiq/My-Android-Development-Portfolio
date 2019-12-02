package contactsdiary.shafiq.com.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import contactsdiary.shafiq.com.R;
import contactsdiary.shafiq.com.adapters.ContactPropertyListAdapter;
import contactsdiary.shafiq.com.models.Contact;
import contactsdiary.shafiq.com.utils.UniversalImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    // Constant
    private static final String TAG = "ContactFragment";

    // Listener
    public interface OnEditContactListener {
        public void onEditContactSelected(Contact contact);
    }

    OnEditContactListener mOnEditContactListener;

    // model
    private Contact mContact;

    //widgets
    private Toolbar toolbar;
    private ImageView mBackArrawImageView;
    private ImageView mEditImageView;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView mListView;

    // This will evade null-pointer exception when adding to a new bundle from MainActivity
    public ContactFragment() {
        super();
        setArguments(new Bundle());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        Log.d(TAG, "onCreateView: started.");

        mContactName = view.findViewById(R.id.tv_name);
        mContactImage = view.findViewById(R.id.contactImageView);
        mListView = view.findViewById(R.id.lv_contact_properties);

        mContact = getContactFromBundle();

        toolbar = view.findViewById(R.id.contactToolbar);
        // required for the setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();

        // Navigating for the back arrow
        mBackArrawImageView = view.findViewById(R.id.iv_backArraow);
        mBackArrawImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                // remove previous fragment from back stack {therefore navigating back}
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // navigating to the edit contact fragment to edit contact selected.
        mEditImageView = view.findViewById(R.id.iv_edit);
        mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked edit icon.");
                mOnEditContactListener.onEditContactSelected(mContact);
            }
        });

        return view;
    }

    private void init() {
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "https://");

        ArrayList<String> properties = new ArrayList<>();
        properties.add(mContact.getPhoneNumber());
        properties.add(mContact.getEmail());

        ContactPropertyListAdapter adapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview, properties);
        mListView.setAdapter(adapter);
        mListView.setDivider(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleting contact.");
        }
        return super.onOptionsItemSelected(item);
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
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnEditContactListener = (OnEditContactListener) getActivity();

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
