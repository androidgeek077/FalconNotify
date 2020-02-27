package AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import app.techsol.falconnotify.MainActivity;
import app.techsol.falconnotify.R;

public class DashboardActivity extends AppCompatActivity {
    CardView addPoliceStationCV, ViewStationsCV;
    private FirebaseAuth auth;
    private CardView getComplaint;
    private CardView ViewUserCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth=FirebaseAuth.getInstance();
        addPoliceStationCV=findViewById(R.id.addPoliceStationCV);
        addPoliceStationCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddPoliceStationActivity.class));
            }
        });
        ViewStationsCV = findViewById(R.id.ViewStationsCV);
        ViewStationsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ViewPoliceStationsActivity.class));
            }
        });
        getComplaint = findViewById(R.id.getComplaint);
        getComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ViewUserComplaintActivity.class));
            }
        });
        ViewUserCV = findViewById(R.id.ViewUserCV);
        ViewUserCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ViewUserActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
