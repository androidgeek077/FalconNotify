package app.techsol.falconnotify;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import Models.ReportModel;


public class AddComplaintActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 1;
    KProgressHUD progressDialog;
    Button mSelectedImgBtn;
    ImageView profileImageView;
    String SelectedSpinner;
    String []StationNames;
    String downloadUri;
    LinearLayout view;
    DatabaseReference databaseReference, StationRef;
    FirebaseAuth auth;
    StorageReference profilePicRef;
    ReportModel reportModel;
    DatabaseReference registerStudent;
    FirebaseAuth mAuth;
    EditText edHeadLine, edReport, edPassword, edPhone, edLong;
    ImageView mProfilePic;
    Double StdLatDouble = 0.0;
    Double StdLongDouble = 0.0;
    String name, email, ImgUrl;
    ReportModel Model;
    private StorageReference mProfilePicStorageReference;
    private Uri selectedProfileImageUri;
    private Button btnSignUp;
    private Button mSelectImgBtn;
    Spinner PoliceStationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        StationRef = FirebaseDatabase.getInstance().getReference("PoliceStations");
        mProfilePicStorageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        PoliceStationSpinner = (Spinner) findViewById(R.id.spinner);


        edHeadLine = findViewById(R.id.edt_txt_headline);
        view = findViewById(R.id.ll);
        edReport = findViewById(R.id.ed_report_in_detail);

        mProfilePic = findViewById(R.id.selectedImg);
        mSelectImgBtn = findViewById(R.id.btn_selectimg);
        mSelectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfilePicture();
            }
        });
        getStations();
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                name = edHeadLine.getText().toString();
                email = edReport.getText().toString();


                if (name.isEmpty()) {
                    edHeadLine.setError("Please Enter Headline First");
                } else if (email.isEmpty()) {
                    edReport.setError("please fill email");
                } else {
                    progressDialog = KProgressHUD.create(AddComplaintActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setAnimationSpeed(2)
                            .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                            .setLabel("Sunmit")
                            .setDetailsLabel("Please Wait...")
                            .setDimAmount(0.3f)
                            .show();

                    // images/mypics/personalpics/abc.jpg

                    profilePicRef = mProfilePicStorageReference.child(selectedProfileImageUri.getLastPathSegment());
                    profilePicRef.putFile(selectedProfileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUri = uri.toString();
                                    updateReport(downloadUri);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddComplaintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });


    }

    public void getProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedProfileImageUri = selectedImageUri;
            mProfilePic.setImageURI(selectedImageUri);
            mProfilePic.setVisibility(View.VISIBLE);
        }

    }

    public void updateReport(String ImageUrl) {
        String ComplaintId=databaseReference.push().getKey();
        SelectedSpinner = PoliceStationSpinner.getSelectedItem().toString();
        reportModel = new ReportModel(ComplaintId,FirebaseAuth.getInstance().getUid(),  "initialized", name, email, ImageUrl, SelectedSpinner);
        databaseReference.child(ComplaintId).setValue(reportModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                mProfilePic.setVisibility(View.GONE);
                final Snackbar snackbar = Snackbar.make(view, "Complaint Added Successfully", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(AddComplaintActivity.this, MainActivity.class));
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    void getStations() {
        StationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(AddComplaintActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();

                    // Is better to use a List, because you don't know the size
                    // of the iterator returned by dataSnapshot.getChildren() to
                    // initialize the array
                    final List<String> PoliceStations = new ArrayList<String>();

                    for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                        String areaName = areaSnapshot.child("name").getValue().toString();
                        Toast.makeText(AddComplaintActivity.this, ""+areaName, Toast.LENGTH_SHORT).show();
                        PoliceStations.add(areaName);
                    }

                    ArrayAdapter<String> PoliceStationAdapter = new ArrayAdapter<String>(AddComplaintActivity.this, android.R.layout.simple_spinner_item, PoliceStations);
                    PoliceStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    PoliceStationSpinner.setAdapter(PoliceStationAdapter);
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
