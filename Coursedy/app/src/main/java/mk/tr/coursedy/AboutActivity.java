package mk.tr.coursedy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    FloatingActionButton add_course;
    Dialog new_course;
    BottomNavigationView bottomNavigationView;
    Dialog log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        add_course = findViewById(R.id.add_course);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewCourse();
            }
        });

        new_course = new Dialog(this);
        new_course.setCanceledOnTouchOutside(false);

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

    private void openNewCourse() {
        new_course.setContentView(R.layout.create_course_dialog);
        new_course.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                new_course.dismiss();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
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
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;

            case R.id.nav_profile:
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
}