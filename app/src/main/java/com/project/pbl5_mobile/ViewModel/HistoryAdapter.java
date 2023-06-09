package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.Model.Helper.FirebaseStrudents;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.HistoryItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<User> listUser;


    public HistoryAdapter(List<User> listUser){
        this.listUser=listUser;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_item, parent, false);
        return new HistoryAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = listUser.get(position);
        if(user.getId()==null){
            holder.binding.tvId.setText("Unknown");
        }
        else holder.binding.tvId.setText(user.getId().toString());
        if(user.getName()==null){
            if(user.getId()!=null) {
                FirebaseStrudents.getInstance().getstudentById(user.getId(), new FirebaseStrudents.StudentCallback() {
                    @Override
                    public void onReceived(Student student) {
                        if (student.getName() != null) {
                            // Đã tìm thấy sinh viên và nhận được thông tin
                            String studentName = student.getName();
                            // Xử lý thông tin sinh viên
                            user.name = studentName;
                            holder.binding.tvName.setText(studentName);
                        } else {
                            // Không tìm thấy sinh viên với id tương ứng
                            // Xử lý trường hợp này
                            holder.binding.tvName.setText("id error");
                        }
                    }
                });

            }else
                holder.binding.tvName.setText("Unknown");
        }
        else
            holder.binding.tvName.setText(user.getName());
        holder.binding.tvTime.setText(user.getTime());
        if(!user.getAvatar().isEmpty())
            Picasso.get().load(user.getAvatar()).into(holder.binding.ivPerson);



    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public interface OnPersonListener{
        void onPersonClick(int position);
    }
    public void updateList(ArrayList<User> newList){
        listUser = new ArrayList<>();
        listUser.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public HistoryItemBinding binding;
        public MyViewHolder(HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public  void setFilterList(List<User> filterList){
        this.listUser = filterList;
        notifyDataSetChanged();
    }
}