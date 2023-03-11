package com.example.hearlall.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hearlall.Model.Article;
import com.example.hearlall.Model.Tutorial;
import com.example.hearlall.Onboarding.RegistrationPage_Activity;
import com.example.hearlall.Onboarding.SignInPage_Activity;
import com.example.hearlall.R;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.CardViewHolder>{

    private final ArrayList<Tutorial> tutorialArrayList;
    private final Context mContext;

    public TutorialAdapter(Context mContext, ArrayList<Tutorial> tutorialArrayList) {
        this.tutorialArrayList = tutorialArrayList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private TutorialAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(TutorialAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public TutorialAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.row_tutorial, parent, false);

        return new TutorialAdapter.CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialAdapter.CardViewHolder holder, int position) {

        holder.txtView_tutorial.setText(tutorialArrayList.get(position).getTutorialName());
        holder.imgView_tutorial.setImageResource(tutorialArrayList.get(position).getTutorialPic());

        int pos = holder.getAdapterPosition();

        holder.cv_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (pos)
                {
                    case 0:
                        String url = "https://www.youtube.com/watch?v=LkGOGzpbrCk&list=LL&index=8";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        mContext.startActivity(intent);
                        break;

                    case 1:
                        String url2 = "https://www.youtube.com/watch?v=u-aGXaCk04M&list=LL&index=7";
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(Uri.parse(url2));
                        mContext.startActivity(intent2);
                        break;

                    case 2:
                        String url3 = "https://www.youtube.com/watch?v=v4Nq-bLkvP8&list=LL&index=6&t=1s";
                        Intent intent3 = new Intent(Intent.ACTION_VIEW);
                        intent3.setData(Uri.parse(url3));
                        mContext.startActivity(intent3);
                        break;

                    case 3:
                        String url4 = "https://www.youtube.com/watch?v=FVS8bcPUjw8&list=LL&index=5";
                        Intent intent4 = new Intent(Intent.ACTION_VIEW);
                        intent4.setData(Uri.parse(url4));
                        mContext.startActivity(intent4);
                        break;

                    case 4:
                        String url5 = "https://www.youtube.com/watch?v=tqAhZSUtOjk&list=LL&index=4&t=4s";
                        Intent intent5 = new Intent(Intent.ACTION_VIEW);
                        intent5.setData(Uri.parse(url5));
                        mContext.startActivity(intent5);
                        break;

                    case 5:
                        String url6 = "https://www.youtube.com/watch?v=ZxF_S3thP-4&list=LL&index=3";
                        Intent intent6 = new Intent(Intent.ACTION_VIEW);
                        intent6.setData(Uri.parse(url6));
                        mContext.startActivity(intent6);
                        break;

                    case 6:
                        String url7 = "https://www.youtube.com/watch?v=ifBKOomw8SY&list=LL&index=2";
                        Intent intent7 = new Intent(Intent.ACTION_VIEW);
                        intent7.setData(Uri.parse(url7));
                        mContext.startActivity(intent7);
                        break;

                    case 7:
                        String url8 = "https://www.youtube.com/watch?v=K2bEv3gUFkA&list=LL&index=1";
                        Intent intent8 = new Intent(Intent.ACTION_VIEW);
                        intent8.setData(Uri.parse(url8));
                        mContext.startActivity(intent8);
                        break;

                }

            }
        });
    }

    private void externalLink() {

    }

    @Override
    public int getItemCount() {
        return tutorialArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private androidx.cardview.widget.CardView cv_tutorial;
        private ImageView imgView_tutorial;
        private TextView txtView_tutorial;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            txtView_tutorial = itemView.findViewById(R.id.txtView_tutorial);
            imgView_tutorial = itemView.findViewById(R.id.imgView_tutorial);
            cv_tutorial = itemView.findViewById(R.id.cv_tutorial);
        }
    }
}
