package test.admin.eventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import test.admin.eventmanagement.activity.Participations;
import test.admin.eventmanagement.util.SessionManager;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Event> eventsList;
    EventAdapter eventAdapter;
    RecyclerView recyclerView;
    Button AddEvent;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    void init()
    {
        sessionManager = new SessionManager(getApplicationContext());
        eventsList=new ArrayList<>();
        AddEvent=(Button)findViewById(R.id.btnAdd) ;
        AddEvent.setOnClickListener(this);
        eventAdapter=new EventAdapter(HomeActivity.this,eventsList);
        recyclerView=findViewById(R.id.eventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(eventAdapter);

        eventsList.addAll(getEventFromDB());
        eventAdapter.notifyDataSetChanged();

        if (sessionManager.getUserType().equalsIgnoreCase(DBHelper.STUDENT))
            AddEvent.setVisibility(GONE);

    }

    public ArrayList<Event> getEventFromDB(){
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        return dbHelper.getAllEvents(sessionManager.getUserColg());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (sessionManager.getUserType().equalsIgnoreCase(DBHelper.STUDENT)){
            menu.removeItem(R.id.menu_create_event);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                new SessionManager(getApplicationContext()).clearSession();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.menu_create_event:
                startActivity(new Intent(HomeActivity.this,AddEvent.class));
                break;
            case R.id.menu_partc:
                startActivity(new Intent(HomeActivity.this, Participations.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
