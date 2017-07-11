package app.dbe.thecreator.com.sampledbe.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import app.dbe.thecreator.com.sampledbe.R;
import app.dbe.thecreator.com.sampledbe.helper.Convert;
import app.dbe.thecreator.com.sampledbe.helper.DataHelper;

/**
 * Created by User2 on 17-04-2017.
 */

public class SampleDB extends AppCompatActivity {
    private DataHelper dh;
    byte[] userKey, mobile, email, password, name, refCardKey, emailCount, mobileCount, result, uploadPath;
    String uKey, mob, mail, pass, nam, refKey, emailCnt, mobileCnt, res, upPath;
    private TextView tvTest;
    private Button btnInsert, btnImportdb, btnExportdb, btnDeletedb;
    public String USERKEY, MOBILENO, EMAIL, PASSWORD, NAME, REFCARDKEY, EMAILCOUNT, MOBILECOUNT, RESULT, UPLOADPATH;
    String msg = "MSTM|266|SAFATA|6|3|31/03/2017|Recieved from 0253545756768|500.00";
    MCrypt mcrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcrypt = new MCrypt();
        tvTest = (TextView) findViewById(R.id.tvtest);
        btnInsert = (Button) findViewById(R.id.btn1);
        btnExportdb = (Button) findViewById(R.id.btn3);
        btnDeletedb = (Button) findViewById(R.id.btn4);
        dh = new DataHelper(SampleDB.this);
        btnImportdb = (Button) findViewById(R.id.btn2);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getApplicationContext(), "MSTM|266|SAFATA|6|3|31/03/2017|Recieved from 0253545756768|500.00", Toast.LENGTH_LONG).show();
                    userKey = mcrypt.encrypt(msg);
                    mobile = mcrypt.encrypt("9900350924");
                    email = mcrypt.encrypt("itsakshath@gmail.com");
                    password = mcrypt.encrypt("12345");
                    name = mcrypt.encrypt("Akshath");
                    refCardKey = mcrypt.encrypt("1");
                    emailCount = mcrypt.encrypt("2");
                    mobileCount = mcrypt.encrypt("3");
                    result = mcrypt.encrypt("100");
                    uploadPath = mcrypt.encrypt("akshathkotegar.website");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                uKey = MCrypt.byteArrayToHexString(userKey);
                mob = MCrypt.byteArrayToHexString(mobile);
                mail = MCrypt.byteArrayToHexString(email);
                pass = MCrypt.byteArrayToHexString(password);
                nam = MCrypt.byteArrayToHexString(name);
                refKey = MCrypt.byteArrayToHexString(refCardKey);
                emailCnt = MCrypt.byteArrayToHexString(emailCount);
                mobileCnt = MCrypt.byteArrayToHexString(mobileCount);
                res = MCrypt.byteArrayToHexString(result);
                upPath = MCrypt.byteArrayToHexString(uploadPath);
                dh.insertUserDetails(uKey, mob, mail, pass, nam, refKey, emailCnt, mobileCnt, res, upPath);

                Cursor cursor = dh.getUserDetails();
                while (cursor.moveToNext()) {
                    try {
                        USERKEY = new String(mcrypt.decrypt(cursor.getString(0)));
                        MOBILENO = new String(mcrypt.decrypt(cursor.getString(1)));
                        EMAIL = new String(mcrypt.decrypt(cursor.getString(2)));
                        PASSWORD = new String(mcrypt.decrypt(cursor.getString(3)));
                        NAME = new String(mcrypt.decrypt(cursor.getString(4)));
                        REFCARDKEY = new String(mcrypt.decrypt(cursor.getString(5)));
                        EMAILCOUNT = new String(mcrypt.decrypt(cursor.getString(6)));
                        MOBILECOUNT = new String(mcrypt.decrypt(cursor.getString(7)));
                        RESULT = new String(mcrypt.decrypt(cursor.getString(8)));
                        UPLOADPATH = new String(mcrypt.decrypt(cursor.getString(9)));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                tvTest.setText(
                        "USERKEY =" + USERKEY + "\n" +
                                "MOBILE NO =" + MOBILENO + "\n" +
                                "EMAIL =" + EMAIL + "\n" +
                                "PASSWORD =" + PASSWORD + "\n" +
                                "NAME =" + NAME + "\n" +
                                "REFCARDKEY =" + REFCARDKEY + "\n" +
                                "EMAILCOUNT =" + EMAILCOUNT + "\n" +
                                "MOBILECOUNT =" + MOBILECOUNT + "\n" +
                                "RESULT =" + RESULT + "\n" +
                                "UPLOADPATH =" + UPLOADPATH + "\n" +
                                "MsgLength b4 Encryption = " + msg.length() + "\n" +
                                "MsgLength after Encryption = " + uKey.length()
                );

            }
        });

        btnImportdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importDB();
            }
        });
        btnExportdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
            }
        });
        btnDeletedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.deleteDb();
                tvTest.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void importDB() {
        String SAMPLE_DB_NAME = dh.getDatabaseName();
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "/data/" + getApplicationContext().getPackageName() + "/databases/" + SAMPLE_DB_NAME;
                String backupDBPath = SAMPLE_DB_NAME;
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Cursor cursor = dh.getUserDetails();
                if (cursor.getCount() <= 0) {
                    tvTest.setText("");
                } else {
                    while (cursor.moveToNext()) {
                        try {
                            USERKEY = new String(mcrypt.decrypt(cursor.getString(0)));
                            MOBILENO = new String(mcrypt.decrypt(cursor.getString(1)));
                            EMAIL = new String(mcrypt.decrypt(cursor.getString(2)));
                            PASSWORD = new String(mcrypt.decrypt(cursor.getString(3)));
                            NAME = new String(mcrypt.decrypt(cursor.getString(4)));
                            REFCARDKEY = new String(mcrypt.decrypt(cursor.getString(5)));
                            EMAILCOUNT = new String(mcrypt.decrypt(cursor.getString(6)));
                            MOBILECOUNT = new String(mcrypt.decrypt(cursor.getString(7)));
                            RESULT = new String(mcrypt.decrypt(cursor.getString(8)));
                            UPLOADPATH = new String(mcrypt.decrypt(cursor.getString(9)));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                tvTest.setText(
                        "USERKEY =" + USERKEY + "\n" +
                                "MOBILE NO =" + MOBILENO + "\n" +
                                "EMAIL =" + EMAIL + "\n" +
                                "PASSWORD =" + PASSWORD + "\n" +
                                "NAME =" + NAME + "\n" +
                                "REFCARDKEY =" + REFCARDKEY + "\n" +
                                "EMAILCOUNT =" + EMAILCOUNT + "\n" +
                                "MOBILECOUNT =" + MOBILECOUNT + "\n" +
                                "RESULT =" + RESULT + "\n" +
                                "UPLOADPATH =" + UPLOADPATH + "\n"
                        // "MsgLength b4 Encryption = " + msg.length() + "\n" +
                        // "MsgLength after Encryption = " + uKey.length()
                );

            }
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void exportDB() {
        String SAMPLE_DB_NAME = dh.getDatabaseName();
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + getApplicationContext().getPackageName() + "/databases/" + SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
