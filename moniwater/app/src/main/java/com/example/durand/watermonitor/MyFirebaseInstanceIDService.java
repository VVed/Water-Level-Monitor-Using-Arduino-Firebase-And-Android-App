package com.example.durand.watermonitor;

        import android.util.Log;

        import com.example.durand.watermonitor.Database.Data;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.iid.FirebaseInstanceId;
        import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    Data data;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        data = new Data(this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("REG_ID", "Refreshed token: " + refreshedToken);
        data.setToken(refreshedToken);
    }
}
