package com.board.hwanungyu.and_board;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment{

    private static final int PICK_FROM_ALBUM = 10;
    private EditText message;
    private Button sendbtn;
    private ImageView imageview;
    private Uri imageUri;
    private String uid;
    private String userName;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload,container,false);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        imageview =  view.findViewById(R.id.fragment_upload_imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_FROM_ALBUM);

            }
        });

        message = (EditText)view.findViewById(R.id.fragment_upload_message);
        sendbtn = (Button)view.findViewById(R.id.fragment_upload_send);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (message.getText().toString() == null || imageUri == null) {
                    Toast.makeText(getActivity(), "이미지 또는 텍스트가 없음", Toast.LENGTH_SHORT).show();
                                    //Fragment에서 토스트 getActivity
                    return;
                }
                message.setText("");
                sendbtn.setEnabled(false);
                Toast.makeText(getActivity(), "업로드 중입니다..", Toast.LENGTH_SHORT).show();

                FirebaseStorage.getInstance().getReference().child("userImage/"+imageUri.getLastPathSegment()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        @SuppressWarnings("VisibleForTests")
                        String imageUrl = task.getResult().getDownloadUrl().toString();



                        DataModel.Dataset dataset = new DataModel.Dataset();
                        dataset.username = userName;
                        dataset.uid = uid;
                        dataset.message = message.getText().toString();
                        dataset.timestamp = ServerValue.TIMESTAMP;
                        dataset.imgaeUrl = imageUrl;
                        FirebaseDatabase.getInstance().getReference().child("board").push().setValue(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "등록 완료", Toast.LENGTH_SHORT).show();
                                //finish();
                                getFragmentManager().beginTransaction().replace(R.id.mainActivity_framelayout, new ListFragment()).commit();

                            }
                        });

                    }
                });



            }

        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            imageview.setImageURI(data.getData());  //가운데 이미지 바꿈
            imageUri = data.getData();  //이미지 경로 원본 string
        }
    }
}
