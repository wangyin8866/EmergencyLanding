package com.zyjr.emergencylending.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.BankDbBean;
import com.zyjr.emergencylending.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库银行工具类
 * Created by neil on 2017/10/24
 */
public class BankcardDb {

    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.BANK_DB + "bankcard.db";
    private String pathStr = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.BANK_DB;

    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static BankcardDb instance;
    private SQLiteDatabase mDatabase;

    /**
     * 获得当前实例对象
     *
     * @return
     */
    public static synchronized BankcardDb getInstance() {
        if (instance == null) {
            instance = new BankcardDb();
        }
        return instance;
    }


    public synchronized SQLiteDatabase openDatabase(Context context) {
        LogUtils.d("数据库-filePath:" + filePath);
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            LogUtils.d("存在数据库------打开");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            //不存在先创建文件夹
            LogUtils.d("不存在数据库------创建");
            File path = new File(pathStr);
            LogUtils.d("pathStr=" + path);
            if (path.mkdir()) {
                LogUtils.d("创建成功");
            } else {
                LogUtils.d("创建失败");
            }
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("bankcard.db");
                LogUtils.d("输入流---->" + is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                LogUtils.d("输出流---->" + "fos=" + fos);
                LogUtils.d("dbPath---->" + jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }

    /**
     * 多线程下关闭
     */
    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

    public BankDbBean queryBankcard(SQLiteDatabase db, String cardNum) {
        Cursor cursor = null;
        BankDbBean bankDbBean = null;
        try {
            cursor = db.rawQuery(
                    "SELECT b.* FROM ( SELECT ? bankcard ) t " +
                            "LEFT JOIN card_bin b ON 1=1 where t.bankcard  LIKE  b.bin||'%'", new String[]{cardNum});
            if (cursor.moveToNext()) {
                String BIN = cursor.getString(cursor.getColumnIndex("BIN"));
                String Bank_name = cursor.getString(cursor.getColumnIndex("Bank_name"));
                String Bank_code = cursor.getString(cursor.getColumnIndex("Bank_code"));
                String is_enable = cursor.getString(cursor.getColumnIndex("is_enable"));
                LogUtils.d("银行卡信息---->" + "BIN = " + BIN + " ,Bank_name = " + Bank_name + ",Bank_code =  " + Bank_code + ",is_enable = " + is_enable);
                bankDbBean = new BankDbBean();
                bankDbBean.setBank_code(BIN);
                bankDbBean.setBank_name(Bank_name);
                bankDbBean.setBank_code(Bank_code);
                bankDbBean.setIs_enable(is_enable);
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            LogUtils.e("queryBankcard" + e.toString());
        }
        return bankDbBean;
    }

}
