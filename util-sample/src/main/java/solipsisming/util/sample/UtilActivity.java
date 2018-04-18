package solipsisming.util.sample;

import android.app.Activity;
import android.os.Bundle;

import solipsisming.util.system.PrintLog;

public class UtilActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.util_activity);
        printLogUtilsTest();
    }

    private void printLogUtilsTest() {
        PrintLog.logE();
        PrintLog.logE("my tag", 1);
        PrintLog.logE(1);
        PrintLog.logE("my tag", 1L);
        PrintLog.logE(1L);
        PrintLog.logE("my tag", 1f);
        PrintLog.logE(1f);
        PrintLog.logE("my tag", 1d);
        PrintLog.logE(1d);
        PrintLog.logE("my tag", true);
        PrintLog.logE(true);
        PrintLog.logE("my tag", ((short) 1));
        PrintLog.logE(((short) 1));
        PrintLog.logE("my tag", ((byte) 1));
        PrintLog.logE(((byte) 1));
        PrintLog.logE("my tag", '1');
        PrintLog.logE('1');
        PrintLog.logE("my tag", "1");
        PrintLog.logE("1");
        PrintLog.logE("my tag", new Object());
        PrintLog.logE(new Object());
        PrintLog.logE("my tag", new Object[]{new Object()});
        PrintLog.logE(new Object[]{new Object()});
        PrintLog.logE("my tag", new int[]{1});
        PrintLog.logE(new int[]{1});
        PrintLog.logE("my tag", new long[]{1});
        PrintLog.logE(new long[]{1});
        PrintLog.logE("my tag", new short[]{1});
        PrintLog.logE(new short[]{1});
        PrintLog.logE("my tag", new byte[]{1});
        PrintLog.logE(new byte[]{1});
        PrintLog.logE("my tag", new char[]{'1'});
        PrintLog.logE(new char[]{'1'});
        PrintLog.logE("my tag", new double[]{1});
        PrintLog.logE(new double[]{1});
        PrintLog.logE("my tag", new float[]{1});
        PrintLog.logE(new float[]{1});
        PrintLog.logE("my tag", new boolean[]{false});
        PrintLog.logE(new boolean[]{true});
        PrintLog.logE("my tag", new String[]{"1"});
        PrintLog.logE(new String[]{"1"});
        PrintLog.logVarE("my tag", 1, 1l, 1d, 1f, new Integer(1));
        PrintLog.logVarE(1, 1l, 1d, 1f, new Integer(1));
        PrintLog.logErr();
        PrintLog.logErr("my tag", new Error("hahaha"));
        PrintLog.logErr(new Error("hahaha"));
        PrintLog.logErr(new Error("hahaha"), new Error("hehe"));
        PrintLog.logErr("my tag", new Error("hahaha"), new Error("hehe"));
        PrintLog.addMonitor();
    }
}