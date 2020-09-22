package com.minilook.minilook.data.firebase;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.minilook.minilook.App;
import timber.log.Timber;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Timber.e("Firebase Token :: %s", token);
        App.getInstance().setPushToken(token);
    }
}