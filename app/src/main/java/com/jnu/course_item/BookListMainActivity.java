package com.jnu.course_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate();

        RecyclerView mainRecycleView=findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);

        mainRecycleView.setAdapter(new MyRecyclerViewAdapter(books));
    }
    public List<Book> getListBooks(){
        return books;
    }
    public void initDate(){
        books =new ArrayList<Book>();
        books.add(new Book("软件项目管理案例教程(第4版本)",R.drawable.book_1));
        books.add(new Book("创新工程实践",R.drawable.book_2));
        books.add(new Book("信息安全数学基础(第2版)",R.drawable.book_3));
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private final List<Book> books;

        public MyRecyclerViewAdapter(List<Book> books) {
            this.books = books;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {  //给布局中的项目，设定资源
            MyViewHolder holder=(MyViewHolder)Holder;
            holder.getImageView().setImageResource(books.get(position).getCoverResourceId());
            holder.getTextViewName().setText(books.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {  //联系布局和内存的，资源绑定器
            private ImageView imageView;
            private TextView textViewName;

            public MyViewHolder(View view) {
                super(view);

                this.imageView=view.findViewById(R.id.image_view_book_cover);
                this.textViewName=view.findViewById(R.id.text_view_book_title);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewName;
            }

        }
    }
}