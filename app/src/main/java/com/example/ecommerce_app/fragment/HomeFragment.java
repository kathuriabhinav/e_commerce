package com.example.ecommerce_app.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce_app.R;
import com.example.ecommerce_app.adapter.BestsellAdapter;
import com.example.ecommerce_app.adapter.CategoryAdapter;
import com.example.ecommerce_app.adapter.FeatureAdapter;
import com.example.ecommerce_app.domain.Bestsell;
import com.example.ecommerce_app.domain.Category;
import com.example.ecommerce_app.domain.Feature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore mStore;

    // category
    private List<Category> mcategoryList;
    private CategoryAdapter mcategoryAdapter;
    private RecyclerView mcategoryRecyclerView;

    // feature
    private List<Feature> mfeatureList;
    private FeatureAdapter mfeatureAdapter;
    private RecyclerView mfeatureRecyclerView;

    // feature
    private List<Bestsell> mBestsellList;
    private BestsellAdapter mBestsellAdapter;
    private RecyclerView mBestsellRecyclerView;

    public HomeFragment()
    {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mStore=FirebaseFirestore.getInstance();

        // category
        mcategoryList = new ArrayList<>();
        mcategoryAdapter = new CategoryAdapter(getContext(), mcategoryList);
        mcategoryRecyclerView = view.findViewById(R.id.category_recycler);
        mcategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mcategoryRecyclerView.setAdapter(mcategoryAdapter);

       // feature
        mfeatureList = new ArrayList<>();
        mfeatureAdapter = new FeatureAdapter(getContext(), mfeatureList);
        mfeatureRecyclerView = view.findViewById(R.id.feature_recycler);
        mfeatureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mfeatureRecyclerView.setAdapter(mfeatureAdapter);

        // Bestsell
        mBestsellList = new ArrayList<>();
        mBestsellAdapter = new BestsellAdapter(getContext(), mBestsellList);
        mBestsellRecyclerView = view.findViewById(R.id.bestsell_recycler);
        mBestsellRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        mBestsellRecyclerView.setAdapter(mBestsellAdapter);


        mStore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                // we are creating class category's object
                                Category category = document.toObject(Category.class);
                                mcategoryList.add(category);
                                mcategoryAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            // if task unsuccessful
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        mStore.collection("Feature")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                // we are creating class category's object
                                Feature feature = document.toObject(Feature.class);
                                mfeatureList.add(feature);
                                mfeatureAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            // if task unsuccessful
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        mStore.collection("BestSell")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                // we are creating class category's object
                                Bestsell bestsell = document.toObject(Bestsell.class);
                                mBestsellList.add(bestsell);
                                mBestsellAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            // if task unsuccessful
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });


        return view;
    }
}