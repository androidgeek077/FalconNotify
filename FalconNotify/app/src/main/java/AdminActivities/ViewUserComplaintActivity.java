package AdminActivities;




import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Models.ReportModel;
import app.techsol.falconnotify.R;

public class ViewUserComplaintActivity extends AppCompatActivity{
    DatabaseReference ComplaintRef, updatereportRef, databaseReference;
    RecyclerView mComplaintRecycVw;
    int value;
    private LocationManager locationManager;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_complaint);
        auth=FirebaseAuth.getInstance();
        ComplaintRef = FirebaseDatabase.getInstance().getReference("Complaints");
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        mComplaintRecycVw = findViewById(R.id.recycler_vw_complaint);
        getSupportActionBar().hide();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mComplaintRecycVw.setLayoutManager(mLayoutManager);

        loadData();

    }

    void loadData() {


        FirebaseRecyclerOptions<ReportModel> options = new FirebaseRecyclerOptions.Builder<ReportModel>()
                .setQuery(ComplaintRef, ReportModel.class)
                .build();

        FirebaseRecyclerAdapter<ReportModel, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<ReportModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final ReportModel model) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

                holder.ComplaintHeadlineTV.setText(model.getHeadline());
                holder.ComplaintStatusTV.setText(model.getReportstatus());


//                Toast.makeText(ViewComplaintsActivity.this, model.getLongitude(), Toast.LENGTH_SHORT).show();


            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_item_layout, viewGroup, false);
                return new ProductViewHolder(view);
            }
        };

        mComplaintRecycVw.setAdapter(adapter);
        adapter.startListening();

    }






    static class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView ComplaintHeadlineTV;
        TextView ComplaintStatusTV;


        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ComplaintHeadlineTV = itemView.findViewById(R.id.ComplaintHeadlineTV);
            ComplaintStatusTV = itemView.findViewById(R.id.ComplaintStatusTV);
//            mTextField = itemView.findViewById(R.id.mTextField);


        }
    }


}
