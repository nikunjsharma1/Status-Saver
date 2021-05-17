package com.nikunj.statussaver.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.nikunj.statussaver.R;
import com.nikunj.statussaver.model.Status;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Common {


       public static final int GRID_COUNT = 2;

       public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() +
               File.separator + "WhatsApp/Media/.Statuses");

       public static String APP_DIR="/SavedStatus";


}

