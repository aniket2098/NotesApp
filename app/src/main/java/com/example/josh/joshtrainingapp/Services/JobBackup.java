package com.example.josh.joshtrainingapp.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class JobBackup extends JobService {
    public JobBackup() {
        super();
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.i("LOGGG", "onStartJob: ");
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if(sd.canWrite()) {
                String currentDBPath = "//data//com.example.josh.joshtrainingapp//databases//DIARY.DB";
                String backupDBPath = "BACKUPDIARY.DB";
                File src = new File(data, currentDBPath);
                File dst = new File(sd, backupDBPath);

                InputStream in = new FileInputStream(src);
                try {
                    OutputStream out = new FileOutputStream(dst);
                    try {
                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
