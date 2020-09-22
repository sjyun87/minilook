package com.minilook.minilook.data.firebase;

import androidx.annotation.NonNull;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.minilook.minilook.App;
import timber.log.Timber;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Timber.e("Firebase Token :: %s", token);
        App.getInstance().setPushToken(token);
    }


    //private void checkToken() {
    //    String token = App.getInstance().getPushToken();
    //    if (token.isEmpty()) {
    //        getCurrentToken();
    //    } else {
    //        isTokenChecked = true;
    //        checkToDo();
    //    }
    //}
    //
    //private void getCurrentToken() {
    //    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
    //        if (task.isSuccessful() && task.getResult() != null) {
    //            String pushToken = task.getResult().getToken();
    //            if (!pushToken.isEmpty()) {
    //                App.getInstance().setPushToken(pushToken);
    //                isTokenChecked = true;
    //                checkToDo();
    //            }
    //        }
    //    });
    //}
}