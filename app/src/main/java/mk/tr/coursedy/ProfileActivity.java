package mk.tr.coursedy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //Create/Add Course
    FloatingActionButton add_course;
    Dialog new_choice;
    Dialog new_course;
    Dialog new_activity;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;
    Dialog log_out;
    boolean doubleBackToExitPressedOnce = false;

    AddCourse addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        add_course = findViewById(R.id.add_course);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        new_choice = new Dialog(this);
        new_choice.setCanceledOnTouchOutside(false);

        new_course = new Dialog(this);
        new_course.setCanceledOnTouchOutside(false);

        new_activity = new Dialog(this);
        new_activity.setCanceledOnTouchOutside(false);

        addCourse = new AddCourse(new_choice, new_course, new_activity, ProfileActivity.this);

        add_course.setOnClickListener(view -> addCourse.openNewChoice());

        log_out = new Dialog(this);
        log_out.setCanceledOnTouchOutside(false);

        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        try {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
        }
        catch(NullPointerException e){
            Log.getStackTraceString(e);
        }

    }

    private void openlogoutdialog(){

        log_out.setContentView(R.layout.logout_dialog);
        log_out.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = log_out.findViewById(R.id.close);
        MaterialButton logout = log_out.findViewById(R.id.logout_btn);
        log_out.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_out.dismiss();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_out.dismiss();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.nav_profile:
                return true;

            case R.id.nav_about:
                finish();
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.nav_sign_out:
                openlogoutdialog();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}