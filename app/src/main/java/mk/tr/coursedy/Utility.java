package mk.tr.coursedy;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }



    static CollectionReference setCourses(){
        return FirebaseFirestore
                .getInstance()
                .collection("all_courses");
    }

    static CollectionReference setActivities(){
        return FirebaseFirestore
                .getInstance()
                .collection("all_activites");
    }

    static CollectionReference setCoursesForUser(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore
                .getInstance()
                .collection("courses")
                .document(currentUser.getUid())
                .collection("my_courses");
    }

    static CollectionReference setActivitiesForUser(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore
                .getInstance()
                .collection("activities")
                .document(currentUser.getUid())
                .collection("my_activities");
    }

}
