package edu.ib.filedemolab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String FOLDERNAME = "Mydir";
    public static final String FILENAME = "test2.txt";
    public static final String MSG = "Ala jedzie na narty!";
    public static final String TAG = "EDUIB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // !!!!!
        // If you cannot see your files in  Android Device File Explorer
        // right click on the app's folder and choose synchronize


        // EXTERNAL WRITE
        // The will be created in the external folder of your app in .../Android/data/files/FOLDER

        // File means path
        File myExternalFile = new File(getExternalFilesDir(FOLDERNAME), FILENAME);

        // try with resources
        try (FileOutputStream os = new FileOutputStream(myExternalFile)) {
            os.write(MSG.getBytes());
            Toast.makeText(this, "Save OK", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        //SIMPLE INTERNAL WRITE
        // The file will be created in the internal folder of your app in /data/data/files
        try (FileOutputStream os = openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            os.write(MSG.getBytes());
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }


        //SIMPLE INTERNAL READ
        try (FileInputStream is = openFileInput(FILENAME)) {
            byte[] bytes = new byte[2048];
            is.read(bytes);
            String msg = new String(bytes);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        //INTERNAL WRITE TO SUBFOLDER
        Context context = getApplicationContext();
        String folder = context.getFilesDir().getAbsolutePath() + File.separator + FOLDERNAME;
        File subfolder = new File(folder);
        if (!subfolder.exists()) subfolder.mkdirs();

        try (FileOutputStream os = new FileOutputStream(new File(subfolder, FILENAME))) {
            os.write(MSG.getBytes());
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}