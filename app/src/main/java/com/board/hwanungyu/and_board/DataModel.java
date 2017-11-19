package com.board.hwanungyu.and_board;

import java.util.HashMap;
import java.util.Map;


public class DataModel {

    //public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저들
    public Map<String,Dataset> dataset = new HashMap<>(); //list 목록

    public static class Dataset {
        public String username;
        public String uid;
        public String message;
        public String imgaeUrl;
        public Object timestamp;


    }
}
