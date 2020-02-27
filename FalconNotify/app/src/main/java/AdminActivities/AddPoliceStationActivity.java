package AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Models.StationModel;
import app.techsol.falconnotify.R;

public class AddPoliceStationActivity extends AppCompatActivity {

    EditText edt_txt_station, ed_reg_no, ed_address, ed_helpline;
    String StationStr, RegNoStr, AddressStr, HelplineStr, StationEmailStr, StationPasswordStr;
    Button btn_submit_station;
    DatabaseReference PoliceStationRef;
    LinearLayout getView;
    private FirebaseAuth auth;
    private EditText edt_txt_station_password, edt_txt_station_email;
    private String userIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_police_station);
        auth=FirebaseAuth.getInstance();
        PoliceStationRef= FirebaseDatabase.getInstance().getReference("PoliceStations");
        getView=findViewById(R.id.getView);
        edt_txt_station=findViewById(R.id.edt_txt_station);
        ed_reg_no=findViewById(R.id.ed_reg_no);
        ed_address=findViewById(R.id.ed_address);
        ed_helpline=findViewById(R.id.ed_helpline);
        btn_submit_station=findViewById(R.id.btn_submit_station);
        edt_txt_station_password=findViewById(R.id.edt_txt_station_password);
        edt_txt_station_email=findViewById(R.id.edt_txt_station_email);
        btn_submit_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStings();
                if (StationStr.equals("")){
                    edt_txt_station.setText("Please fill station");
                } else if (RegNoStr.equals("")){
                    ed_reg_no.setText("Please fill Regirtation No.");
                }else if (AddressStr.equals("")){
                    ed_address.setText("Please fill Address");
                }else if (HelplineStr.equals("")){
                    ed_helpline.setText("Please fill Helpline");
                } else {
                    auth.createUserWithEmailAndPassword(StationEmailStr, StationPasswordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                userIdStr=auth.getCurrentUser().getUid();
                                StationModel model=new StationModel(StationStr, RegNoStr, AddressStr, userIdStr, StationEmailStr,"police", StationPasswordStr, HelplineStr );
                                PoliceStationRef.push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Snackbar.make(getView, "Station added successfully", Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddPoliceStationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPoliceStationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    void getStings(){
        StationStr=edt_txt_station.getText().toString();
        RegNoStr=ed_reg_no.getText().toString();
        AddressStr=ed_address.getText().toString();
        HelplineStr=ed_helpline.getText().toString();
        StationEmailStr=edt_txt_station_email.getText().toString();
        StationPasswordStr=edt_txt_station_password.getText().toString();
    }
}
