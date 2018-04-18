package solipsisming.util.system;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 通知栏工具</p>
 * 创建于 2015-7-1 22:47:16
 *
 * @author 洪东明
 * @version 1.0
 */
public class NotificationUtils {

    /**
     * 禁止实例对象
     */
    private NotificationUtils() {
        throw new UnacceptableInstanceError();
    }


    /**
     * 发送默认通知
     *
     * @param context      当前应用
     * @param flag         通知标志
     * @param iconId       图标
     * @param tickerText   通知栏的提示语
     * @param contentTitle 通知栏的标题
     * @param contextText  通知栏的内容
     * @param clazz        启动的activity
     * @return 通知
     */
    public static Notification sendNotification(Context context, int flag, int iconId,
                                                String tickerText,
                                                String contentTitle, String contextText,
                                                Class<? extends Activity> clazz) {
        return sendNotification(context, flag, iconId, tickerText, contentTitle,
                contextText, null, clazz);
    }

    /**
     * 发送默认通知
     *
     * @param context     当前应用
     * @param flag        通知标志
     * @param iconId      图标
     * @param tickerText  通知栏的提示语
     * @param contextText 通知栏的内容
     * @return 通知
     */
    public static Notification sendNotification(Context context, int flag, int iconId,
                                                String tickerText, String contextText) {
        return sendNotification(context, flag, iconId, tickerText, null, contextText, null);
    }

    /**
     * 发送默认通知
     *
     * @param context      当前应用
     * @param flag         通知标志
     * @param iconId       图标
     * @param tickerText   通知栏的提示语
     * @param contentTitle 通知栏的标题
     * @param contextText  通知栏的内容
     * @param view         自定视图
     * @param clazz        启动的activity
     * @return 通知
     */
    public static Notification sendNotification(Context context, int flag, int iconId,
                                                String tickerText,
                                                String contentTitle, String contextText,
                                                RemoteViews view,
                                                Class<? extends Activity> clazz) {
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {

            Notification notification;
            Notification.Builder builder = new Notification.Builder(context);
            if (iconId != 0) {
                builder.setSmallIcon(iconId);//设置状态栏中的小图片，尺寸一般建议在24×24
            }
            if (tickerText != null)
                builder.setTicker(tickerText);//状态栏提示信息

            if (contentTitle != null)
                builder.setContentTitle(contentTitle);//通知栏的标题

            if (contextText != null)
                builder.setContentText(contextText);//通知栏的标题的内容

            if (clazz != null) {
                PendingIntent pendingIntent = PendingIntent
                        .getActivity(context, 0, new Intent(context, clazz), 0);
                builder.setContentIntent(pendingIntent);
            }

            if (Build.VERSION.SDK_INT >= 16) //api16以上支持
                notification = builder.build();
            else
                notification = builder.getNotification();

            if (view != null) {
                notification.contentView = view;
            }

            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;//设定是否声音
            notification.defaults |= Notification.DEFAULT_VIBRATE;//设定是否振动

            manager.notify(flag, notification);

            return notification;
        }
        return null;
    }

    /**
     * 清除通知
     *
     * @param context 当前应用
     * @param flag    通知的标志
     */
    public static void clearNotification(Context context, int flag) {
        NotificationManager manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null)
            manager.cancel(flag);
    }
}