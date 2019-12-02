package contactsdiary.shafiq.com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import contactsdiary.shafiq.com.R;
import contactsdiary.shafiq.com.adapters.ContactListAdapter;
import contactsdiary.shafiq.com.models.Contact;

public class ViewContactsFragment extends Fragment {

    private static final String TAG = "ViewContactsFragment";
    private String textImageUrl = "encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRtuh-wxdIdNxgwHG4ojejJUSp4tuVZXDOpK4GK00uJM4ZBJ7W7";


    // Communication Listener { Inter Fragments / Activities }
    public interface OnContactSelectedListener {
        public void onContactSelected(Contact contact);
    }

    // Reference variable
    OnContactSelectedListener mOnContactListener;

    public interface OnAddContactListener {
        public void onAddContact();
    }

    OnAddContactListener mOnAddContact;

    // constants & variable
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;

    // widgets
    private FloatingActionButton mFloatingActionButton;
    private ImageView mSearchImageView;
    private ImageView mBackArrowImageView;
    private AppBarLayout mViewContactsAppBar;
    private AppBarLayout mSearchAppBar;
    private ListView contactsList;
    private EditText mSearchContacts;

    private ContactListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.fragment_viewcontacts, container, false);

        mViewContactsAppBar = v.findViewById(R.id.viewContactsToolbar);
        mSearchAppBar = v.findViewById(R.id.searchToolbar);
        contactsList = v.findViewById(R.id.lv_contactsList);
        mSearchContacts = v.findViewById(R.id.et_search_contacts);
        Log.d(TAG, "onCreateView: started.");

        setAppBarState(STANDARD_APPBAR);

        setupContactsList();

        mFloatingActionButton = v.findViewById(R.id.fab_addContact);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked floating action button.");
                mOnAddContact.onAddContact();
            }
        });

        mSearchImageView = v.findViewById(R.id.iv_searchIcon);
        mSearchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked search icon.");
                toggleToolBarState();
            }
        });

        mBackArrowImageView = v.findViewById(R.id.iv_backArraow);
        mBackArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                toggleToolBarState();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        try{
            mOnContactListener = (OnContactSelectedListener) getActivity();
            mOnAddContact = (OnAddContactListener) getActivity();

        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    private void setupContactsList() {

        final ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Wayn Baloch", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Rodrygo Goes", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Gareth Bale", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Marco Asensio", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Vinicius Jr", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Lucas Vazquez", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Toni Kroos", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Fede Valverde", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Luka Modric", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Casemiro Enrique", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Sergio Ramos", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Raphael Varane", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Dani Carvajal", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Marcelo Veira", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Karim Benzema", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Eden Hazard", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));
        contacts.add(new Contact("Isco Alarcon", "0322-8583173", "Mobile",
                "wayn@baloch.com", textImageUrl));

        mAdapter = new ContactListAdapter(getActivity(), R.layout.layout_contactslistitem, contacts, "https://");

        mSearchContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = mSearchContacts.getText().toString().toLowerCase(Locale.getDefault());
                mAdapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactsList.setAdapter(mAdapter);

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: " + getString(R.string.contact_fragment));

                // pass the contact to the interface and send it to MainActivity
                mOnContactListener.onContactSelected(contacts.get(position));
            }
        });

    }

    /*
    *** Indicates the appbar state toggle
    * */
    private void toggleToolBarState() {

        Log.d(TAG, "toggleToolBarState: toggling AppBarState.");

        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {

        Log.d(TAG, "setAppBarState: changing app bar state to: " + state);

        mAppBarState = state;

        if (mAppBarState == STANDARD_APPBAR) {

            mSearchAppBar.setVisibility(View.GONE);
            mViewContactsAppBar.setVisibility(View.VISIBLE);

            // hide the keyboard
            View view = getView();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (NullPointerException e) {
                Log.d(TAG, "setAppBarState: NullPointerException: " + e.getMessage());
            }

        } else if (mAppBarState == SEARCH_APPBAR) {
            mViewContactsAppBar.setVisibility(View.GONE);
            mSearchAppBar.setVisibility(View.VISIBLE);

            // open the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}
