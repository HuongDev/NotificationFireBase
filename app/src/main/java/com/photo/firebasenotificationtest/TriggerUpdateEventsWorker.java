package com.photo.firebasenotificationtest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class TriggerUpdateEventsWorker extends Worker {
    public TriggerUpdateEventsWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
    }

    @NonNull
    @Override
    public Result doWork() {
        //first remove any existing work that is scheduled to prevent duplicates due to
        // inconsistent trigger time
        WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(EVENTS_JSON_WORK_TAG);

        //set constraints to prevent running when it's not supposed to
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        //then reschedule all future work to run daily
        PeriodicWorkRequest getEventJson = new PeriodicWorkRequest
                .Builder(UpdateEventsWorker.class, 24, TimeUnit.HOURS)
                .setInputData(getInputData())
                .setConstraints(constraints)
                .addTag(EVENTS_JSON_WORK_TAG)
                .build();

        //and of course enqueue...
        WorkManager.getInstance(getApplicationContext()).enqueue(getEventJson);

        return Result.success();
    }
}