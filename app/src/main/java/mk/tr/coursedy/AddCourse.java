package mk.tr.coursedy;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class AddCourse {

    Dialog new_choice;
    Dialog new_course;
    Dialog new_activity;
    Context context;

    AddCourse(Dialog new_choice, Dialog new_course, Dialog new_activity, Context context){
        this.new_choice = new_choice;
        this.new_course = new_course;
        this.new_activity = new_activity;
        this.context = context;
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
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(0, 0);
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
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursecode = course_code.getText().toString();
                String coursename = course_name.getText().toString();
                if(coursecode == null || coursecode.isEmpty()){
                    course_code.setError("Course Code is required");
                    return;
                }
                //Add search for code in db and return name if found.
                //else{}
                Course course = new Course();
                course.setCode(coursecode);
                course.setName(coursename);
                course.setTimestamp(Timestamp.now());
                addCourseToFirebase(course);

                new_course.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(0, 0);
            }

            private void addCourseToFirebase(Course course) {

                DocumentReference documentReference = Utility.setCoursesForUser().document();
                DocumentReference documentReference1 = Utility.setCourses().document();

                documentReference.set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            documentReference1.set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Utility.showToast(context, "Course Added Successfully");
                                        ((Activity)context).finish();
                                    }
                                    else{
                                        Utility.showToast(context, "Failure while adding course");
                                    }
                                }
                            });
                        }
                        else{
                            Utility.showToast(context, "Failure while adding course");
                        }
                    }
                });
            }

        });
    }

    public void openNewActivity() {
        new_activity.setContentView(R.layout.create_activity_dialog);
        new_activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText activity_name = new_activity.findViewById(R.id.activity_name);
        ImageView close = new_activity.findViewById(R.id.close);
        MaterialButton add = new_activity.findViewById(R.id.addbtn);
        new_activity.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_activity.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(0, 0);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityname = activity_name.getText().toString();

                CActivity cActivity = new CActivity();
                cActivity.setName(activityname);
                cActivity.setTimestamp(Timestamp.now());
                addActivityToFirebase(cActivity);

                new_activity.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(0, 0);
            }

            private void addActivityToFirebase(CActivity cActivity) {

                DocumentReference documentReference = Utility.setActivitiesForUser().document();
                DocumentReference documentReference1 = Utility.setActivities().document();

                documentReference.set(cActivity).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            documentReference1.set(cActivity).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Utility.showToast(context, "Activity Added Successfully");
                                        ((Activity)context).finish();
                                    }
                                    else{
                                        Utility.showToast(context, "Failure while adding activity");
                                    }
                                }
                            });
                        }
                        else{
                            Utility.showToast(context, "Failure while adding activity");
                        }
                    }
                });
            }

        });
    }


}
