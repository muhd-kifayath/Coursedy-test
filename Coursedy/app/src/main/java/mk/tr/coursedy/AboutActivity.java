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

public class AboutActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //Create/Add Course
    FloatingActionButton add_course;
    Dialog new_choice;
    Dialog new_course;
    Dialog new_activity;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;
    Dialog log_out;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        add_course = findViewById(R.id.add_course);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewChoice();
            }
        });

        new_choice = new Dialog(this);
        new_choice.setCanceledOnTouchOutside(false);

        new_course = new Dialog(this);
        new_course.setCanceledOnTouchOutside(false);

        new_activity = new Dialog(this);
        new_activity.setCanceledOnTouchOutside(false);


        log_out = new Dialog(this);
        log_out.setCanceledOnTouchOutside(false);

        bottomNavigationView.setSelectedItemId(R.id.nav_about);

        try {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
        }
        catch(NullPointerException e){
            Log.getStackTraceString(e);
        }

    }

    public void openNewChoice() {
        new_choice.setContentView(R.layout.courseactivity_option_dialog);
        new_choice.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = new_choice.findViewById(R.id.close);
        MaterialButton course_new = new_choice.findViewById(R.id.add_course_nav);
        MaterialButton activity_new = new_choice.findViewById(R.id.add_activity_nav);
        new_choice.show();

        course_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_choice.dismiss();
                openNewCourse();
            }
        });

        activity_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_choice.dismiss();
                openNewActivity();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_choice.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }
        });

    }

    public void openNewCourse() {
        new_course.setContentView(R.layout.create_course_dialog);
        new_course.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText course_code = new_course.findViewById(R.id.course_code);
        EditText course_name = new_course.findViewById(R.id.course_name);
        ImageView close = new_course.findViewById(R.id.close);
        MaterialButton add = new_course.findViewById(R.id.addbtn);
        new_course.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_course.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursecode = course_code.getText().toString();
                String coursename = course_name.getText().toString();
                if(coursecode == null || coursecode.isEmpty()){
                    return;
                }
                Course course = new Course();
                course.setCode(coursecode);
                course.setName(coursename);
                course.setTimestamp(Timestamp.now());
                addCourseToFirebase(course);

                new_course.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }

            private void addCourseToFirebase(Course course) {

            }

        });
    }

    public void openNewActivity() {
        new_activity.setContentView(R.layout.create_activity_dialog);
        new_activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText activity_name = new_activity.findViewById(R.id.course_code);
        ImageView close = new_activity.findViewById(R.id.close);
        MaterialButton add = new_activity.findViewById(R.id.addbtn);
        new_activity.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityname = activity_name.getText().toString();
                new_activity.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
            }

            private void addCourseToFirebase(Course course) {

            }

        });
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
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.nav_about:
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