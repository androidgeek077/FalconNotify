package app.techsol.falconnotify;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminComplaintActivity extends AppCompatActivity implements LocationListener {
    DatabaseReference ComplaintRef;
    RecyclerView mComplaintRecycVw;
    int value;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);
        ComplaintRef = FirebaseDatabase.getInstance().getReference("Complaints");
        mComplaintRecycVw = findViewById(R.id.recycler_vw_complaint);
        getSupportActionBar().hide();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mComplaintRecycVw.setLayoutManager(mLayoutManager);

        loadData();

    }

    void loadData() {


        FirebaseRecyclerOptions<StationModel> options = new FirebaseRecyclerOptions.Builder<StationModel>()
                .setQuery(ComplaintRef, StationModel.class)
                .build();

        FirebaseRecyclerAdapter<StationModel, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<StationModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final StationModel model) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                //if you need three fix imageview in width
                holder.HotelName.setText(model.getName());


//                Toast.makeText(AdminComplaintActivity.this, model.getLongitude(), Toast.LENGTH_SHORT).show();


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


        TextView HotelName, mTextField, PasswordVersionsTV, StageStartTime;

        LinearLayout StartTimeLL;
        FrameLayout ChnageStageStatusFL;
        ImageView stageUnloacked, stageLocked;
        LinearLayout getView;


        ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            HotelName = itemView.findViewById(R.id.ComplaintHeadlineTV);
//            getView = itemView.findViewById(R.id.getView);
//            mTextField = itemView.findViewById(R.id.mTextField);


        }
    }

}
