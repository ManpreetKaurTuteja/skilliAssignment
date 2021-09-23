package eziam.tech.skilliassignmentmanpreet;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import eziam.tech.skilliassignmentmanpreet.app.SkilliAssignmentApp;
import eziam.tech.skilliassignmentmanpreet.home.activities.MainActivity;


public class SpalshActivity extends AppCompatActivity {
    SkilliAssignmentApp sApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        sApp = new SkilliAssignmentApp();
        sApp.setActivity(this);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                sApp.startNewActivityAndCloseOld(SpalshActivity.this, MainActivity.class);
            }
        }.start();
    }
}
