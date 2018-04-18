package solipsisming.util.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import solipsisming.util.exception.UnacceptableInstanceError;

/**
 * 判断网络状态</p>
 * 创建于 2015-5-29 19:25:49
 *
 * @author 洪东明
 * @version 1.0
 */
public class NetworkUtils {

    /**
     * 禁止创建对象
     */
    private NetworkUtils() {
        throw new UnacceptableInstanceError();
    }

    /**
     * 判断网络是否可用
     *
     * @param context 当前应用
     * @return 网络是否可用
     */
    public static boolean isNetworkUseful(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 判断wifi是否可用
     *
     * @param context 当前应用
     * @return wifi是否可用
     */
    public static boolean isWifiUseful(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context, ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 判断移动数据是否可用
     *
     * @param context 当前应用
     * @return 移动数据是否可用
     */
    public static boolean isMobileUseful(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context, ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 判断当前网络类型
     *
     * @param context 当前应用
     * @return 网络类型 <b>(-1=不可用,0=不可用,1=wifi)</b>
     */
    public static int getNetworkType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null && networkInfo.isAvailable())
            return networkInfo.getType();
        return -1;
    }

    /**
     * 判断网络是否可用 -2(表示只获取网络信息，不指定网络状态)
     *
     * @param context 当前应用
     * @return 网获取网络的信息
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        return getNetworkInfo(context, -2);
    }

    /**
     * 判断网络是否可用
     *
     * @param context 当前应用
     * @param type    网络类型
     * @return 网获取网络的信息
     */
    private static NetworkInfo getNetworkInfo(Context context, int type) {
        NetworkInfo networkInfo = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            switch (type) {
                case 0://移动数据
                    networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    break;
                case 1://wifi
                    networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    break;
                default://当前网络状态
                    networkInfo = connManager.getActiveNetworkInfo();
                    break;
            }
        }
        return networkInfo;
    }

    /**
     * 获取ip地址
     *
     * @param context 当前应用
     * @return ip地址
     */
    public static String getIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            if (!wifiManager.isWifiEnabled()) {//判断wifi是否开启
                return "null";
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            return toStringIp(wifiInfo.getIpAddress());
        }
        return null;
    }

    /**
     * 将2进制转换为string IP地址
     *
     * @param i 2进制ip
     * @return string ip
     */
    private static String toStringIp(int i) {
        return (i & 0xFF) + "." + (i >> 8 & 0xFF) + "." + (i >> 16 & 0xFF) + "." + (i >> 24 & 0xFF) + ".";
    }

    /**
     * 打开wifi
     *
     * @param context 当前应用
     */
    public static void openWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && !wifiManager.isWifiEnabled()) {//判断wifi是否开启
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 获取手机mac地址
     *
     * @param context 当前应用
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            String macAddress = "null";
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress();
            }
            return macAddress;
        }
        return null;
    }

    /**
     * 获取详细网络类型(string)
     *
     * @param context 当前应用
     */

    public static String getDetailNetworkType(Context context) {
        String strNetworkType = "null";
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    strNetworkType = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String typeName = networkInfo.getSubtypeName();
                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            strNetworkType = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            strNetworkType = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            strNetworkType = "4G";
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (typeName.equalsIgnoreCase("TD-SCDMA") ||
                                    typeName.equalsIgnoreCase("WCDMA") ||
                                    typeName.equalsIgnoreCase("CDMA2000")) {
                                strNetworkType = "3G";
                            } else {
                                strNetworkType = typeName;
                            }

                            break;
                    }
                }
            }
        }
        return strNetworkType;
    }
}