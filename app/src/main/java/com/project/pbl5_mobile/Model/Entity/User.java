package com.project.pbl5_mobile.Model.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.pbl5_mobile.Model.Helper.FirebaseStrudents;

public class User implements Parcelable {
    public String time;
    public String name;
    private String avatar;
    private Integer id;

    public User(String time, String avatar) {
        this.time = time;
        this.avatar = avatar;
//        this.id = null;
//        this.name = "null";

    }

    public User(String time, String avatar, Integer id) {
        this.time = time;
        this.avatar = avatar;
        this.id = id;
        FirebaseStrudents firebaseStrudents = new FirebaseStrudents();
        firebaseStrudents.getStudentByID(id.toString(), new FirebaseStrudents.StudentCallback() {
            @Override
            public void onReceived(Student student) {
                if (student.getName() != null) {
                    // Đã tìm thấy sinh viên và nhận được thông tin
                    String studentName = student.getName();
                    // Xử lý thông tin sinh viên
                    User.this.name = studentName;
                } else {
                    // Không tìm thấy sinh viên với id tương ứng
                    // Xử lý trường hợp này
                }
            }
        });
    }


    public User(String time, String name, String avatar, Integer id) {
        this.time = time;
        this.name = name;
        this.avatar = avatar;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(){
        avatar="";

    }

    protected User(Parcel in) {
        time = in.readString();
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String id) {
        this.time = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(String id, String name, String avatar) {
        this.time = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(name);
        parcel.writeString(avatar);

    }
}
