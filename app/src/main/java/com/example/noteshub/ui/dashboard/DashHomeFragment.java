package com.example.noteshub.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Constants;
import com.example.noteshub.R;
import com.example.noteshub.base.BaseFragment;
import com.example.noteshub.databinding.FragmentDashHomeBinding;
import com.example.noteshub.helpers.NotesAdapter;
import com.example.noteshub.managers.ActivitySwitchManager;
import com.example.noteshub.model.NotesModel;
import com.example.noteshub.repository.FirestoreRepository;
import com.example.noteshub.ui.curd.NotesDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class DashHomeFragment extends BaseFragment {

    private FragmentDashHomeBinding binding;
    private FirestoreRepository repository = new FirestoreRepository();
    private NotesAdapter notesAdapter;
    private List<NotesModel> notesModelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//
//        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashHomeBinding.inflate(getLayoutInflater());

        initComponents();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null)
            getNotes();
    }

    @Override
    public void initComponents() {

        activity = requireActivity();

        repository.getUserProfileCollection().document(Constants.UserAuthID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (getActivity() != null) {
                        String username = documentSnapshot.getString("USER_NAME");
                        if (username != null && username.length() > 20) {
                            username = username.substring(0, 20);
                        }
                        username = getActivity().getString(R.string.hi) + " " + username + ",";
                        int profileNumber = Objects.requireNonNull(documentSnapshot.getLong("PROFILE_NUMBER")).intValue();

                        binding.tvGreetUser.setText(username);
                        int imageRes;
                        switch (profileNumber) {
                            case 1:
                                imageRes = R.drawable.profile_1;
                                break;
                            case 2:
                                imageRes = R.drawable.profile_2;
                                break;
                            case 3:
                                imageRes = R.drawable.profile_3;
                                break;
                            case 4:
                                imageRes = R.drawable.profile_4;
                                break;
                            case 5:
                                imageRes = R.drawable.profile_5;
                                break;
                            default:
                                imageRes = R.drawable.profile_6;
                                break;

                        }
                        binding.profileImage.setImageResource(imageRes);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(activity, "Try again later.", Toast.LENGTH_SHORT).show());


        notesAdapter = new NotesAdapter(notesModelList);
        binding.rvNotes.setHasFixedSize(false);
        binding.rvNotes.setLayoutManager(new GridLayoutManager(activity, 2));
//        binding.rvNotes.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvNotes.setAdapter(notesAdapter);

        notesAdapter.setOnItemClickListener(position -> {
            if (position >= 0) {
                new ActivitySwitchManager(activity, NotesDetailsActivity.class)
                        .openActivityWithoutFinish("NOTES_DETAILS", notesModelList.get(position));
            }

        });

        getNotes();

    }

    private void getNotes() {

//        CollectionReference collectionReference = repository.getNotesCollection(Constants.UserAuthID);
//        Query query = collectionReference.whereEqualTo("isDeleted", false).orderBy("lastUpdatedDate", Query.Direction.DESCENDING);

        repository.getNotesCollection(Constants.UserAuthID).orderBy("lastUpdatedDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful() && task.getResult() != null) {

                            notesModelList.clear();

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                NotesModel notesModel = documentSnapshot.toObject(NotesModel.class);
                                if (!notesModel.isDeleted)
                                    notesModelList.add(notesModel);
                            }
                            notesAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(activity, "Try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Try again later", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}