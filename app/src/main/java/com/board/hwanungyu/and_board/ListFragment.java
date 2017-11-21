package com.board.hwanungyu.and_board;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ListFragment extends Fragment{
    @Nullable
    private RecyclerView recyclerView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);

        recyclerView = view.findViewById(R.id.listfragment_recyclerview);

        recyclerView.setAdapter(new ListRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        return view;
    }

    class ListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<DataModel.Dataset> dataModels = new ArrayList<>();
        private String uid;
        public ListRecyclerViewAdapter() {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                FirebaseDatabase.getInstance().getReference().child("board").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataModels.clear();

                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            dataModels.add(item.getValue(DataModel.Dataset.class));
                        }
//                        Comparator comparator = Collections.reverseOrder();
//                        Collections.sort(dataModels ,comparator);

                        Collections.sort(dataModels, textDesc) ;

                        notifyDataSetChanged(); //갱신
                        recyclerView.scrollToPosition(0);

                        //맨 마지막으로 이동

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }

        Comparator<DataModel.Dataset> textDesc = new Comparator<DataModel.Dataset>() {
            @Override
            public int compare(DataModel.Dataset item1, DataModel.Dataset item2) {
                return item2.timestamp.toString().compareTo(item1.timestamp.toString());
                // 아래와 같은 코드 /* int ret ; if (item1.getText().compareTo(item2.getText()) < 0) /
                // / item1이 작은 경우, ret = 1 ; else if (item1.getText().compareTo(item2.getText()) == 0) ret = 0 ;
                // else // item1이 큰 경우, ret = -1 ; return ret ; */ } } ;
            }
        };



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

            return new CustomViewHoler(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            CustomViewHoler customViewHoler = (CustomViewHoler)holder;
            customViewHoler.textView_title.setText(dataModels.get(position).username);
            customViewHoler.textView_message.setText(dataModels.get(position).message);
            Glide.with(holder.itemView.getContext()) //이미지
                    .load(dataModels.get(position).imgaeUrl)
                    .apply(new RequestOptions().placeholder(R.drawable.loading_icon))
                    .into(customViewHoler.imageView);
            long unixTime = (long) dataModels.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            customViewHoler.textView_timeStamp.setText(time);

        }

        @Override
        public int getItemCount() {
            return dataModels.size();
        }

        private class CustomViewHoler extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView_title;
            public TextView textView_timeStamp;
            public TextView textView_message;
            public CustomViewHoler(View view) {

                super(view);
                imageView = view.findViewById(R.id.listItem_Imageview);
                textView_title = view.findViewById(R.id.listItem_textView_title);
                textView_timeStamp = view.findViewById(R.id.listItem_textView_timeStamp);
                textView_message = view.findViewById(R.id.listItem_textView_message);
            }
        }
    }
}