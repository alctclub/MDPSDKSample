package com.alct.mdpsdksample.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class ViewUtil {
    private ViewUtil() {
        //Nothing here
    }

    public interface TaskOnUi {
        void work();
    }

    public static void show(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void show(Context context, int message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showOnUI(final Context context, final CharSequence message, final int duration) {
        workOnUi(context, new TaskOnUi() {
            @Override
            public void work() {
                show(context, message, duration);
            }
        });
    }

    public static void showOnUI(final Context context, final CharSequence message) {
        workOnUi(context, new TaskOnUi() {
            @Override
            public void work() {
                show(context, message, Toast.LENGTH_SHORT);
            }
        });
    }

    public static void showOnDialogUi(final Context context, final CharSequence message) {
        workOnUi(context, new TaskOnUi() {
            @Override
            public void work() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("提示")
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    public static void workOnUi(final Context context, final TaskOnUi work) {
        if (!(context instanceof Activity)) {
            Log.i("ViewUtil", "try to work workOnUI on a no Activity context...");
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    work.work();
                }
            });
        }
    }
}
