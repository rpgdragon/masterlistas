package org.jcastellano.masterlistas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by jmcastellano on 11/12/2017.
 */

public class RateMyApp {
    private static String APP_PACKAGENAME;
    //Dias necesarios para que invoque al RateMyApp
    private final static int DAYS_UNTIL_PROMPT = 1;
    //Veces que lanza la app para que invoque al RateMyApp
    private final static int LAUNCHES_UNTIL_PROMPT = 3;
    private long launch_count;
    //Contador de lanzamientos
    // Hasta que no se cumplen los dias y lanzamientos no lanza RateMyApp
    private final static boolean DAYS_AND_LAUNCHES = false;
    // Configurado para que al 3 lanzamiento muestre RateMyApp
    private boolean exceedsSpecifiedLaunches;
    private Activity callerActivity;
    private AlertDialog ratemyappDialog;

    public RateMyApp(Activity activity) {
        callerActivity = activity; APP_PACKAGENAME = activity.getPackageName();
    }

    public void app_launched() {
        SharedPreferences prefs = callerActivity.getSharedPreferences( "apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();
        launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis(); editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        exceedsSpecifiedLaunches = launch_count >= LAUNCHES_UNTIL_PROMPT;
        boolean exceedsDaysSinceFirstLaunch = System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000);
        if ((exceedsSpecifiedLaunches || exceedsDaysSinceFirstLaunch) && !DAYS_AND_LAUNCHES) {
            editor.putLong("launch_count",0); showRateDialog(editor);
        } else if (exceedsSpecifiedLaunches && exceedsDaysSinceFirstLaunch && DAYS_AND_LAUNCHES) {
            editor.putLong("launch_count",0); showRateDialog(editor);
        } editor.apply();
    }

    public void showRateDialog(final SharedPreferences.Editor editor) {
        if(APP_PACKAGENAME.equals(""))
            return;
        AlertDialog.Builder builder;
        LayoutInflater inflater = callerActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_ratemyapp, null);
        Button rateButton = (Button) layout.findViewById( R.id.ratemyapp_dialog_accept_button);
        Button cancelButton = (Button) layout.findViewById( R.id.ratemyapp_dialog_cancel_button);
        Button laterButton = (Button) layout.findViewById( R.id.ratemyapp_dialog_later_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callerActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + APP_PACKAGENAME)));
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.apply(); } ratemyappDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putLong("launch_count", 0); editor.apply();
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "empresa@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "MasterListas Issue: ");
                intent.putExtra(Intent.EXTRA_TEXT, "Put inside the issue");
                callerActivity.startActivity( Intent.createChooser(intent, "Send Email"));
                ratemyappDialog.dismiss();
            }
        });
        laterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editor != null) {
                    editor.putLong("launch_count", 0);
                    editor.apply(); } ratemyappDialog.dismiss();
            }
        });
        builder = new AlertDialog.Builder(callerActivity);
        builder.setView(layout);
        ratemyappDialog = builder.create();
        ratemyappDialog.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ratemyappDialog.show();
    }
}
