package bm.pdm.ifpb.com.businessmanager.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import bm.pdm.ifpb.com.businessmanager.infra.NetworkUtils;
import bm.pdm.ifpb.com.businessmanager.views.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    private NetworkUtils utils;

    @Override
    public void onReceive(Context context, Intent intent) {
        utils = new NetworkUtils();
        if(utils.verificarConexao(context)){
            Intent caminho = new Intent(context, MainActivity.class);
            TaskStackBuilder builderTask = TaskStackBuilder.create(context);
            builderTask.addParentStack(MainActivity.class);
            builderTask.addNextIntent(caminho);
            PendingIntent p = builderTask.getPendingIntent(1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("Business Manager - Você está conectado a internet");
            builder.setSmallIcon(android.R.drawable.stat_notify_sync);
            builder.setContentText("Aproveite para verificar se você possui novas atividades ou duvidas!");
            builder.setContentIntent(p);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, builder.build());
        }
    }
}
