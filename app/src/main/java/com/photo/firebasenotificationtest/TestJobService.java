package com.photo.firebasenotificationtest;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class TestJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Utils.schedulerJob(getApplicationContext()); // reschedule the job
        Toast.makeText(this, "Bg Service", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}