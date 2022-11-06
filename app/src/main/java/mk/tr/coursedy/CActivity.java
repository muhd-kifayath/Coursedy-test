package mk.tr.coursedy;

import com.google.firebase.Timestamp;

public class CActivity {
    String name;
    Timestamp timestamp;

    public CActivity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
