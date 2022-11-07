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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    RecyclerView home_recycler;
    TextView welcome_message;

    //Create/Add Course
    FloatingActionButton add_course;
    Dialog new_choice;
    Dialog new_course;
    Dialog new_activity;

    FirebaseAuth mAuth;
    FirebaseUser user;

    //Bottom Navigation
    BottomNavigationView bottomNavigationView;
    Dialog log_out;
    boolean doubleBackToExitPressedOnce = false;

    AddCourse addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        add_course = findViewById(R.id.add_course);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        home_recycler = findViewById(R.id.home_recycler);
        welcome_message = findViewById(R.id.welcome_message);

        new_choice = new Dialog(this);
        new_choice.setCanceledOnTouchOutside(false);

        new_course = new Dialog(this);
        new_course.setCanceledOnTouchOutside(false);

        new_activity = new Dialog(this);
        new_activity.setCanceledOnTouchOutside(false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user != null) {
            String[] name = user.getDisplayName().split(" [0-9]{2}[A-Z]{3}[0-9]{4}");
            String hello_message = "Hello, " + name[0];
            welcome_message.setText(hello_message);
        }

        addCourse = new AddCourse(new_choice, new_course, new_activity, HomeActivity.this);

        add_course.setOnClickListener(view -> addCourse.openNewChoice());

        log_out = new Dialog(this);
        log_out.setCanceledOnTouchOutside(false);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

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
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
                return true;

            case R.id.nav_profile:
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
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