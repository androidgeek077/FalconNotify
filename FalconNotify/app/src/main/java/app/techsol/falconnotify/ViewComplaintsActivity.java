package app.techsol.falconnotify;


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

public class ViewComplaintsActivity extends AppCompatActivity implements LocationListener {
    DatabaseReference ComplaintRef, updatereportRef, databaseReference;
    RecyclerView mComplaintRecycVw;
    int value;
    private LocationManager locationManager;
    FirebaseAuth auth;
    private String UserType="police";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints);
        auth=FirebaseAuth.getInstance();
        ComplaintRef = FirebaseDatabase.getInstance().getReference("Complaints");
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        mComplaintRecycVw = findViewById(R.id.recycler_vw_complaint);
        getSupportActionBar().hide();
        getUserType();

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

                if (model.getReportid().equalsIgnoreCase(auth.getCurrentUser().getUid())){
                    holder.ComplaintHeadlineTV.setText(model.getHeadline());
                    holder.ComplaintStatusTV.setText(model.getReportstatus());
                } else if (model.getPoliceStation().equalsIgnoreCase("Satellite Town")){
                    holder.ComplaintHeadlineTV.setText(model.getHeadline());
                    holder.ComplaintStatusTV.setText(model.getReportstatus());
                }
                holder.ComplaintStatusTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (UserType.equalsIgnoreCase("police") && model.getReportstatus().equalsIgnoreCase("initialized")){
                            ComplaintRef.child(model.getComplaintid()).child("reportstatus").setValue("Processing");
                        } else if (UserType.equalsIgnoreCase("police") && model.getReportstatus().equalsIgnoreCase("Processing")){
                            ComplaintRef.child(model.getComplaintid()).child("reportstatus").setValue("Resolved");
                        }else if (UserType.equalsIgnoreCase("user") && model.getReportstatus().equalsIgnoreCase("Resolved")){
                            ComplaintRef.child(model.getComplaintid()).setValue("Confirmed");
                        }
                    }
                });


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

    @Override
    public void onLocationChanged(Location location) {
//        distance(location.getLatitude(), location.getLongitude(), )
//        Toast.makeText(this, "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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

    void getUserType() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(MainActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();

                if (dataSnapshot.exists()) {
                    UserType = dataSnapshot.child("usertype").getValue(String.class);
                    Toast.makeText(ViewComplaintsActivity.this, UserType, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            void getSubmittedStation() {
                databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(MainActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();

                        if (dataSnapshot.exists()) {
                            UserType = dataSnapshot.child("usertype").getValue(String.class);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        StationModel post = postSnapshot.getValue(StationModel.class);
//                        String Name=post.getName();
//                        Toast.makeText(AddComplaintActivity.this, Name, Toast.LENGTH_SHORT).show();
//                    }
//                }


                });

            }

        });
    }

}
