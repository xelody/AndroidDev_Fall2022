package edu.northeastern.numad22fa_peiyaoxin;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment implements View.OnClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "inputName";
    private static final String ARG_PARAM2 = "inputURL";

    private String name;
    private String URL;
    private EditText etName;
    private EditText etURL;
    private Button btnSubmit;
    RecyclerView linkRecyclerView;

    public InputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InputFragment.
     */
    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fragment.name);
        args.putString(ARG_PARAM2, fragment.URL);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            name = getArguments().getString(ARG_PARAM1);
//            URL = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etName = getView().findViewById(R.id.input_name);
        etURL = getView().findViewById(R.id.input_URL);

        btnSubmit = getView().findViewById(R.id.submit_btn);
        btnSubmit.setOnClickListener(this);
        linkRecyclerView = getView().findViewById(R.id.link_recycler_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.submit_btn) {
            name = etName.getText().toString();
            URL = etURL.getText().toString();
            if (name.isEmpty() || URL.isEmpty()) {
                if (name.isEmpty()) {
                    etName.setError("Name must not be empty");
                } else if (URL.isEmpty()) {
                    etURL.setError("URL must not be empty");
                }
            } else {
//                Toast.makeText(getActivity(), name + " " + URL, Toast.LENGTH_LONG).show();
                Link newLink = new Link(name, URL);

                etName.setText("");
                etURL.setText("");
                hideFragment();
            }
        }
    }

    public void hideFragment() {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.hide(this);
        ft.commit();
    }
}