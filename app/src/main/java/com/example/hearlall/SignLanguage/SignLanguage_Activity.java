package com.example.hearlall.SignLanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.hearlall.R;

import java.util.ArrayList;

public class SignLanguage_Activity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_language);

        final int choice = getIntent().getExtras().getInt("Userchoice");
        gridView = (GridView) findViewById(R.id.gridView);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData(choice));
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                /* Call DisplayActivity with the choice and image position */
                Intent intent = new Intent(SignLanguage_Activity.this, Display_Activity.class);
                intent.putExtra("Image Int", position);
                intent.putExtra("Choice", choice);
                startActivity(intent);
                finish();
            }

        });
    }

    private ArrayList<ImageItem> getData(int choice) {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();

        int[] arr_st = {R.array.image_ids,
                R.array.image_num,
                R.array.image_fuw};
        TypedArray imgs = getResources().obtainTypedArray(arr_st[choice - 1]);

        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                    imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, " " + i + 1));
        }

        return imageItems;
    }
}