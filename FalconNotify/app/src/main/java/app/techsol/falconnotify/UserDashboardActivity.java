package app.techsol.falconnotify;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import AdminActivities.ViewUserComplaintActivity;

public class UserDashboardActivity extends AppCompatActivity {
    CardView SubmitReportCV, ViewUserCompainltCV, SubmitFeedbackCV;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        auth=FirebaseAuth.getInstance();
        SubmitReportCV = findViewById(R.id.SubmitReportCV);
        SubmitReportCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddComplaintActivity.class));
            }
        });
        ViewUserCompainltCV = findViewById(R.id.ViewUserCompainltCV);
        ViewUserCompainltCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ViewUserComplaintActivity.class));
            }
        });
        SubmitFeedbackCV = findViewById(R.id.SubmitFeedbackCV);
        SubmitFeedbackCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SubmitFeedbackActivity.class));
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
