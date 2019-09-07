package test.admin.eventmanagement.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.admin.eventmanagement.DBHelper;
import test.admin.eventmanagement.Participation;
import test.admin.eventmanagement.R;
import test.admin.eventmanagement.adapter.ParticipationsAdapter;
import test.admin.eventmanagement.util.SessionManager;

public class Participations extends AppCompatActivity {
    RecyclerView recyclerView;
    SessionManager sessionManager;
    DBHelper dbHelper;
    ParticipationsAdapter adapter;
    List<Participation> participationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        dbHelper = new DBHelper(getApplicationContext());
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(getApplicationContext());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        participationList = dbHelper.getAllParticipations(sessionManager.getUserColg());
        adapter = new ParticipationsAdapter(getApplicationContext(), participationList);
        recyclerView.setAdapter(adapter);


    }

}
