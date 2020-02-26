package app.techsol.falconnotify;


import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class ViewUserComplaintActivity extends AppCompatActivity implements LocationListener {
    DatabaseReference ComplaintRef;
    RecyclerView mComplaintRecycVw;
    int value;
    FirebaseAuth auth;
    private Dialog dialog;
    private DatabaseReference databaseReference, updatereportRef;

    private LocationManager locationManager;
    FirebaseRecyclerAdapter<ReportModel, ProductViewHolder> adapter;
    private String UserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_complaint);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        updatereportRef = FirebaseDatabase.getInstance().getReference("Complaints");
        ComplaintRef = FirebaseDatabase.getInstance().getReference("Complaints");
        mComplaintRecycVw = findViewById(R.id.recycler_vw_complaint);
        getSupportActionBar().hide();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mComplaintRecycVw.setLayoutManager(mLayoutManager);
        getUserType();

        loadData();


    }

    void loadData() {


        FirebaseRecyclerOptions<ReportModel> options = new FirebaseRecyclerOptions.Builder<ReportModel>()
                .setQuery(ComplaintRef, ReportModel.class)
                .build();

         adapter = new FirebaseRecyclerAdapter<ReportModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final ReportModel model) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width
                //if you need three fix imageview in width
                if (auth.getCurrentUser().getUid().equals(model.getReportid())) {
                    holder.ReportHeadLineTV.setText(model.getHeadline());
                    holder.ReportStatusTV.setText(model.getReportstatus());
                } else {
                    holder.ReportHeadLineTV.setVisibility(View.GONE);
                }

                holder.ReportStatusTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (UserType.equalsIgnoreCase("police") && model.getReportstatus().equalsIgnoreCase("initialized")){
                            updatereportRef.child(model.getComplaintid()).setValue("Processing");
                        } else if (UserType.equalsIgnoreCase("police") && model.getReportstatus().equalsIgnoreCase("Processing")){
                            updatereportRef.child(model.getComplaintid()).setValue("Resolved");
                        }else if (UserType.equalsIgnoreCase("user") && model.getReportstatus().equalsIgnoreCase("Resolved")){
                            updatereportRef.child(model.getComplaintid()).setValue("Confirmed");
                        }
                    }
                });

                holder.ListItemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog = new Dialog(ViewUserComplaintActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        dialog.setContentView(R.layout.dialog_box);
                        dialog.setTitle("Password");
                        dialog.getWindow().getAttributes().windowAnimations = R.style.Theme_AppCompat_DayNight_Dialog_Alert;
                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                        dialog.setCancelable(true);
                        TextView HeadlineTV = dialog.findViewById(R.id.reportHeadlines);
                        TextView detailsTV = dialog.findViewById(R.id.detailsTV);
                        HeadlineTV.setText(model.getHeadline());
                        detailsTV.setText(model.getImageurl());
                        dialog.show();
                    }
                });

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


        TextView ReportHeadLineTV, ReportStatusTV;
        CardView ListItemCard;


        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ReportHeadLineTV = itemView.findViewById(R.id.ComplaintHeadlineTV);
            ReportStatusTV = itemView.findViewById(R.id.ComplaintStatusTV);
            ListItemCard = itemView.findViewById(R.id.ListItemCard);
//            getView = itemView.findViewById(R.id.getView);
//            mTextField = itemView.findViewById(R.id.mTextField);


        }
    }

    void getUserType() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(MainActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();

                if (dataSnapshot.exists()){
                    UserType=dataSnapshot.child("usertype").getValue(String.class);
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


}
