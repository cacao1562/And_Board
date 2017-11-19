package com.board.hwanungyu.and_board;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.listfragment_recyclerview);

      //  recyclerView.setAdapter(new ChatRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        return view;
    }

//    class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//        private List<ChatModel> chatModels = new ArrayList<>();
//        private String uid;
//        public ChatRecyclerViewAdapter() {
//            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+uid).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    chatModels.clear();
//                    for (DataSnapshot item : dataSnapshot.getChildren()){
//                        chatModels.add(item.getValue(ChatModel.class));
//                    }
//                    notifyDataSetChanged(); //새로고침
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
//
//            return new CustomViewHoler(view);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//            final CustomViewHoler customViewHoler = (CustomViewHoler)holder;
//            String destinationUid = null;
//
//            //챗방에 있는 유저 다 체크
//            for (String user: chatModels.get(position).users.keySet()) {
//                if (!user.equals(uid)) {
//                    destinationUid = user;
//                }
//            }
//            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
//                    Glide.with(customViewHoler.itemView.getContext())
//                            .load(userModel.profileImageUrl)
//                            .apply(new RequestOptions().circleCrop())
//                            .into(customViewHoler.imageView);
//                    customViewHoler.textView_title.setText(userModel.name);
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//            //메세지를 내림차순으로 정렬 후 마지막 메세지의 키값을 가져옴
//            Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
//            commentMap.putAll(chatModels.get(position).comments);
//            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
//            customViewHoler.textView_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);
//        }
//
//        @Override
//        public int getItemCount() {
//            return chatModels.size();
//        }
//
//        private class CustomViewHoler extends RecyclerView.ViewHolder {
//            public ImageView imageView;
//            public TextView textView_title;
//            public TextView textView_message;
//            public CustomViewHoler(View view) {
//
//                super(view);
//                imageView = view.findViewById(R.id.listItem_Imageview);
//                textView_title = view.findViewById(R.id.listItem_textView_title);
//                textView_message = view.findViewById(R.id.listItem_textView_message);
//            }
//        }
//    }
}