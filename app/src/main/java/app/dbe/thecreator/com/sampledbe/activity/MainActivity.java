package app.dbe.thecreator.com.sampledbe.activity;

import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

import app.dbe.thecreator.com.sampledbe.R;
import app.dbe.thecreator.com.sampledbe.helper.AdvancedEncryptionStandard;
import app.dbe.thecreator.com.sampledbe.helper.Convert;
import app.dbe.thecreator.com.sampledbe.helper.DataHelper;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {
    private DataHelper dh;
    private TextView tvTest;
    public String USERKEY, MOBILENO, EMAIL, PASSWORD, NAME, REFCARDKEY, EMAILCOUNT, MOBILECOUNT, RESULT, UPLOADPATH;
    String userKey, mobile, email, password, name, refCardKey, emailCount, mobileCount, result, uploadPath;
    private Button btnInsert, btnImportdb;
    //byte[] userKey, mobile, email, password, name, refCardKey, emailCount, mobileCount, result, uploadPath;
   // byte[] encryptionKey = "MZygpewJsCpRrfOr".getBytes(StandardCharsets.UTF_8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dh = new DataHelper(MainActivity.this);
        tvTest = (TextView) findViewById(R.id.tvtest);
        btnInsert = (Button) findViewById(R.id.btn1);
        btnImportdb=(Button) findViewById(R.id.btn2);
         btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userKey = Convert.convert("MSTM|266|SAFATA|6|3|31/03/2017|Recieved from 0253545756768|500.00", "write");
                mobile = Convert.convert("9900350924", "write");
                email = Convert.convert("itsakshath@gmail.com", "write");
                password = Convert.convert("12345", "write");
                name = Convert.convert("Akshath", "write");
                refCardKey = Convert.convert("1", "write");
                emailCount = Convert.convert("2", "write");
                mobileCount = Convert.convert("3", "write");
                result = Convert.convert("100", "write");
                uploadPath = Convert.convert("akshathkotegar.website", "write");

                dh.insertUserDetails(userKey, mobile, email, password, name, refCardKey, emailCount, mobileCount, result, uploadPath);

                Cursor cursor = dh.getUserDetails();
                while (cursor.moveToNext()) {
                    USERKEY = Convert.convert(cursor.getString(0), "read");
                    MOBILENO = Convert.convert(cursor.getString(1), "read");
                    EMAIL = Convert.convert(cursor.getString(2), "read");
                    PASSWORD = Convert.convert(cursor.getString(3), "read");
                    NAME = Convert.convert(cursor.getString(4), "read");
                    REFCARDKEY = Convert.convert(cursor.getString(5), "read");
                    EMAILCOUNT = Convert.convert(cursor.getString(6), "read");
                    MOBILECOUNT = Convert.convert(cursor.getString(7), "read");
                    RESULT = Convert.convert(cursor.getString(8), "read");
                    UPLOADPATH = Convert.convert(cursor.getString(9), "read");
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
                                "UPLOADPATH =" + UPLOADPATH
                );

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exportDB();
        finish();
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


  /* Simple Insert
   dh.insertUserDetails(u, "9900350924", "itsakshath@gmail.com", "12345", "Akshath",
                "1", "2", "3", "100", "akshathkotegar.website");*/


  /* Complicated Insert
  userKey = Convert.convert("1", "write");
           mobile = Convert.convert("9900350924", "write");
           email = Convert.convert("itsakshath@gmail.com", "write");
           password = Convert.convert("12345", "write");
           name = Convert.convert("Akshath", "write");
           refCardKey = Convert.convert("1", "write");
           emailCount = Convert.convert("2", "write");
           mobileCount = Convert.convert("3", "write");
           result = Convert.convert("100", "write");
           uploadPath = Convert.convert("akshathkotegar.website", "write");

           dh.insertUserDetails(userKey, mobile, email, password, name, refCardKey, emailCount, mobileCount, result, uploadPath);

           Cursor cursor = dh.getUserDetails();
           while (cursor.moveToNext()) {
           USERKEY = Convert.convert(cursor.getString(0), "read");
           MOBILENO = Convert.convert(cursor.getString(1), "read");
           EMAIL = Convert.convert(cursor.getString(2), "read");
           PASSWORD = Convert.convert(cursor.getString(3), "read");
           NAME = Convert.convert(cursor.getString(4), "read");
           REFCARDKEY = Convert.convert(cursor.getString(5), "read");
           EMAILCOUNT = Convert.convert(cursor.getString(6), "read");
           MOBILECOUNT = Convert.convert(cursor.getString(7), "read");
           RESULT = Convert.convert(cursor.getString(8), "read");
           UPLOADPATH = Convert.convert(cursor.getString(9), "read");
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
           "UPLOADPATH =" + UPLOADPATH
           );*/