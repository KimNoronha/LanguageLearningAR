package com.example.arimagerecognizer;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arimagerecognizer.adapter.LabelAdapter;
import com.google.ar.core.examples.java.ml.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudyActivity extends AppCompatActivity {

    LabelAdapter adapter;
    private static final String TAG = "StudyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        RecyclerView lists = findViewById(R.id.recyclerViewVocabularySets);
        lists.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LabelAdapter(this);
        lists.setAdapter(adapter);

        loadDataFromFirestore();
    }

    private void loadDataFromFirestore() {
        Log.d(TAG, "loadDataFromFirestore: Flag 1");


        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the collection
        CollectionReference collectionRef = db.collection("labels/" + email + "/recognisedLabels");

        List<Pair<String, Date>> recognisedLabels = new ArrayList<>();
        Log.d(TAG, "loadDataFromFirestore: Flag 2");

        collectionRef.get().addOnSuccessListener(querySnapshot -> {
            try {
                Log.d(TAG, "loadDataFromFirestore: " + querySnapshot.size());

                // Iterate over documents and extract data
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    // Extract label and timestamp from each document
                    String label = (String) document.get("label");
                    Date timestamp = document.getTimestamp("timestamp").toDate();

                    // Add the label and timestamp to the list
                    recognisedLabels.add(new Pair<>(label, timestamp));
                }
                adapter.updateData(recognisedLabels);
            } catch (Exception e) {
                // Handle any errors
                Log.d(TAG, "loadDataFromFirestore: " + e.getMessage());
                System.out.println("Error fetching recognised labels: " + e.getMessage());
            }
        }).addOnFailureListener(e -> {
            // Handle failures
            Log.d(TAG, "Error fetching recognised labels: " + e.getMessage());
        });

    }
}