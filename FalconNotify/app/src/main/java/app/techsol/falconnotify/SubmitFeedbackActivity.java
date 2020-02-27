package app.techsol.falconnotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitFeedbackActivity extends AppCompatActivity {
    EditText ed_signup_feedback, edt_txt_username;
    String feedbackStr, UsernameStr;
    Button mSubmitBtn;
    DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_feedback);
        feedbackRef= FirebaseDatabase.getInstance().getReference("Feedback");
        ed_signup_feedback=findViewById(R.id.ed_signup_feedback);
        edt_txt_username=findViewById(R.id.edt_txt_username);
        mSubmitBtn=findViewById(R.id.btn_submitfeedback);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackStr= ed_signup_feedback.getText().toString();
                UsernameStr= edt_txt_username.getText().toString();
                FeedbackModel model=new FeedbackModel(feedbackStr, UsernameStr);
                feedbackRef.push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SubmitFeedbackActivity.this, "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
