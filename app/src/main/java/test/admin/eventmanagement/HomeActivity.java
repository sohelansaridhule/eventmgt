package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Event> eventsList;
    EventAdapter eventAdapter;
    RecyclerView recyclerView;
    Button AddEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    void init()
    {
        eventsList=new ArrayList<>();
        AddEvent=(Button)findViewById(R.id.btnAdd) ;
        AddEvent.setOnClickListener(this);
        eventAdapter=new EventAdapter(HomeActivity.this,eventsList);
        recyclerView=findViewById(R.id.eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(eventAdapter);

        eventsList.addAll(getEventFromDB());
        eventAdapter.notifyDataSetChanged();

    }

    public ArrayList<Event> getEventFromDB(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        return dbHelper.getAllEvents();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnAdd:
                startActivity(new Intent(HomeActivity.this,AddEvent.class));
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        eventsList.clear();
        eventsList.addAll(getEventFromDB());
        eventAdapter.notifyDataSetChanged();
    }
}
