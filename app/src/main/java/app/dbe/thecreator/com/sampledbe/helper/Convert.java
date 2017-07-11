package app.dbe.thecreator.com.sampledbe.helper;

import android.util.Log;

import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import static com.tozny.crypto.android.AesCbcWithIntegrity.decryptString;
import static com.tozny.crypto.android.AesCbcWithIntegrity.encrypt;
import static com.tozny.crypto.android.AesCbcWithIntegrity.generateKeyFromPassword;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keyString;
import static com.tozny.crypto.android.AesCbcWithIntegrity.keys;

/**
 * Created by User1 on 26-09-2016.
 */
public class Convert {
    public static final String TAG = "Tozny";

    private static boolean PASSWORD_BASED_KEY = true;
    private static String EXAMPLE_PASSWORD = "1234";
    private static String keyStr = "";
    private static String salt = "e8j5EuCChUNbWYb0ITExgqVkGA1MTeZLuOroDZjBvLgInySxpYJe//Zpq7CJJbzbo4PNjEheKbYusKCzece3KxNsaylmonrfh1iyVcncM7tF7OBBrogZCay7mh84yU9ySoMhCzL6f0/G+6CLQF8FE/K+lpf/ZH5brqtnWhpNZ90=";
    private ArrayList<String> result= new ArrayList<>();

   /* public static  void convertDBAssets()
    {
     String changedValue = convert(dataHelper.WALLET_TABLE,"write");
        changedValue =   changedValue.substring(0,10);
        changedValue = changedValue.replaceAll("[^a-zA-Z]+","");
        dataHelper.WALLET_TABLE =changedValue;
    }
*/

    public static String convert(String text, String operation)
    {
        String textNeeded= "";

        try {
            AesCbcWithIntegrity.SecretKeys key;
            //if (PASSWORD_BASED_KEY) {//example for password based keys
            //    String salt = saltString(generateSalt());
                //If you generated the key from a password, you can store the salt and not the key.
                Log.i(TAG, "Salt: " + salt);
                key = generateKeyFromPassword(EXAMPLE_PASSWORD, salt);
                keyStr= keyString(key);
                key = null; //Pretend to throw that away so we can demonstrate converting it from str
                //String textToEncrypt = text;
                Log.i(TAG, "Before encryption: " + text);

            // Read from storage & decrypt
            key = keys(keyStr); // alternately, regenerate the key from password/salt.
            if(operation.equalsIgnoreCase("write"))
            {

                    AesCbcWithIntegrity.CipherTextIvMac civ = encrypt(text, key);
                    Log.i(TAG, "Encrypted: " + civ.toString());
                    textNeeded = civ.toString();


            }
            else {

                    AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac = new AesCbcWithIntegrity.CipherTextIvMac(text);

                    String decryptedText = decryptString(cipherTextIvMac, key);

                    textNeeded = decryptedText;
                    Log.i(TAG, "Decrypted: " + decryptedText);

                //Note: "String.equals" is not a constant-time check, which can sometimes be problematic.
               // Log.i(TAG, "Do they equal: " + textToEncrypt.equals(decryptedText));
            }

        }
        catch (GeneralSecurityException e) {
            Log.e(TAG, "GeneralSecurityException", e);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException", e);
        }
     return  textNeeded;
    }
}

