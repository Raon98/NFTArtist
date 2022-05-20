package com.example.nftartist.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nftartist.Model.MainItem;
import com.example.nftartist.Model.NFTMain;
import com.example.nftartist.Model.userInfo;
import com.example.nftartist.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<NFTMain> mDataset;
    private Activity activity;

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    public class MainViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        MainViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
    public MainAdapter(Activity activity, ArrayList<NFTMain> myDataset) {
        this.mDataset = myDataset;
        this.activity = activity;
    }

    public int getItemViewType(int position){
        return position;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_title_layout, parent, false);
        final MainViewHolder mainViewHolder = new MainViewHolder(cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView mainImage = cardView.findViewById(R.id.Main_image);
        TextView mainTitle = cardView.findViewById(R.id.mainPageTitle);
        TextView conTitle = cardView.findViewById(R.id.content_to_Title);
        TextView conEx = cardView.findViewById(R.id.content_to_Ex);
        TextView conName =cardView.findViewById(R.id.content_name_view);
        TextView conEmail =cardView.findViewById(R.id.content_email_view);

        NFTMain nftMain = mDataset.get(position);
        if(mDataset.get(position).getNftUri() != null){
            Glide.with(activity).load(mDataset.get(position).getNftUri()).centerCrop().override(500).into(mainImage);
        }
        mainTitle.setText(nftMain.getNftTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
