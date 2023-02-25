package com.example.hearlall.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hearlall.Model.Article;
import com.example.hearlall.R;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.CardViewHolder>{

    private final ArrayList<Article> articleArrayList;
    private final Context mContext;

    public ArticleAdapter(Context mContext, ArrayList<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ArticleAdapter.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(ArticleAdapter.OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public ArticleAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.row_article, parent, false);

        return new ArticleAdapter.CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.CardViewHolder holder, int position) {

        holder.txtView_articleTitle.setText(articleArrayList.get(position).getArticle());
        holder.imgView_article.setImageResource(articleArrayList.get(position).getArticlePic());

        holder.cv_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();

                switch (pos)
                {
                    case 0:
                        String url = "https://medium.com/thrive-global/help-for-wrist-pain-feel-better-fast-with-these-exercises-dca01573d6d1";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        mContext.startActivity(i);
                        break;

                    case 1:
                        String url2 = "https://medium.com/better-humans/a-helpful-way-to-stretch-and-strengthen-the-wrists-3e99d8edfafd";
                        Intent i2 = new Intent(Intent.ACTION_VIEW);
                        i2.setData(Uri.parse(url2));
                        mContext.startActivity(i2);
                        break;

                    case 2:
                        String url3 = "https://medium.com/@ggermanji24/hand-wrist-exercises-for-keyboard-users-e4d353dc20ec";
                        Intent i3 = new Intent(Intent.ACTION_VIEW);
                        i3.setData(Uri.parse(url3));
                        mContext.startActivity(i3);
                        break;

                    case 3:
                        String url4 = "https://medium.com/in-fitness-and-in-health/easy-exercise-modifications-for-wrist-pain-37e19fff3b61";
                        Intent i4 = new Intent(Intent.ACTION_VIEW);
                        i4.setData(Uri.parse(url4));
                        mContext.startActivity(i4);
                        break;

                    case 4:
                        String url5 = "https://medium.com/@jenna_77824/hey-moms-get-wrist-pain-when-you-exercise-try-this-730f7dac54d9";
                        Intent i5 = new Intent(Intent.ACTION_VIEW);
                        i5.setData(Uri.parse(url5));
                        mContext.startActivity(i5);
                        break;

                    case 5:
                        String url6 = "https://medium.com/@ggermanji24/hand-wrist-exercises-for-keyboard-users-e4d353dc20ec";
                        Intent i6 = new Intent(Intent.ACTION_VIEW);
                        i6.setData(Uri.parse(url6));
                        mContext.startActivity(i6);
                        break;

                    case 6:
                        String url7 = "https://medium.com/in-fitness-and-in-health/easy-exercise-modifications-for-wrist-pain-37e19fff3b61";
                        Intent i7 = new Intent(Intent.ACTION_VIEW);
                        i7.setData(Uri.parse(url7));
                        mContext.startActivity(i7);
                        break;

                    case 7:
                        String url8 = "https://medium.com/@jenna_77824/hey-moms-get-wrist-pain-when-you-exercise-try-this-730f7dac54d9";
                        Intent i8 = new Intent(Intent.ACTION_VIEW);
                        i8.setData(Uri.parse(url8));
                        mContext.startActivity(i8);
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView txtView_articleTitle;
        private androidx.cardview.widget.CardView cv_article;
        private ImageView imgView_article;


        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            txtView_articleTitle = itemView.findViewById(R.id.txtView_articleTitle);
            imgView_article = itemView.findViewById(R.id.imgView_article);
            cv_article = itemView.findViewById(R.id.cv_article);

        }
    }
}
