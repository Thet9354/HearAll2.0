package com.example.hearlall.Call;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.hearlall.R;

import java.util.ArrayList;

public class ContactList_Activity extends AppCompatActivity {

    private RecyclerView rc_contactList;
    ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        initWidget();

        checkPermission();
    }

    private void checkPermission() {
        //Check condition
        if (ContextCompat.checkSelfPermission(ContactList_Activity.this,
                Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED)
        {
            //When permission is not granted, request permission
            ActivityCompat.requestPermissions(ContactList_Activity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }
        else
        {
            getContactList();
        }
    }

    private void getContactList() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

        Cursor cursor = getContentResolver().query(
                uri, null, null, null, sort
        );

        if (cursor.getCount() > 0)
        {
            //When count is greater than 0, use while loop
            while (cursor.moveToNext())
            {

                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID
                ));

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));

                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        +" =?";

                Cursor phoneCursor = getContentResolver().query(
                        uriPhone, null, selection,
                        new String[]{id}, null
                );

                if (phoneCursor.moveToNext())
                {
                    @SuppressLint("Range") String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));

                    ContactModel model = new ContactModel();

                    model.setName(name);

                    model.setNumber(number);

                    arrayList.add(model);

                    phoneCursor.close();
                }
            }

            cursor.close();
        }

        rc_contactList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, arrayList);
        rc_contactList.setAdapter(adapter);
    }

    private void initWidget() {
        rc_contactList = findViewById(R.id.rc_contactList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
        == PackageManager.PERMISSION_GRANTED)
        {
            getContactList();
        }
        else
        {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
    }
}