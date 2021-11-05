package com.jnu.course_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {  //联系布局和内存的，资源绑定器
            private ImageView imageView;
            private TextView textViewName;

            public MyViewHolder(View view) {
                super(view);

                this.imageView=view.findViewById(R.id.image_view_book_cover);
                this.textViewName=view.findViewById(R.id.text_view_book_title);

                itemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewName;
            }


            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position= getAdapterPosition();
                switch(menuItem.getItemId())
                {
                    case 1:
                        View dialagueView= LayoutInflater.from(BookListMainActivity.this).inflate(R.layout.dialogue_input_item,null);
                        AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(BookListMainActivity.this);
                        alertDialogBuiler.setView(dialagueView);

                        alertDialogBuiler.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText editName=dialagueView.findViewById(R.id.edit_text_name);
                                //EditText editPrice=dialagueView.findViewById(R.id.edit_text_price);
                                books.add(position,new Book(editName.getText().toString(),R.drawable.book_3));
                                MyRecyclerViewAdapter.this.notifyItemInserted(position);
                            }
                        });
                        alertDialogBuiler.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialogBuiler.create().show();;

                        break;
                    case 2:
                        books.get(position).setName("测试修改");
                        MyRecyclerViewAdapter.this.notifyItemChanged(position);
                        break;

                    case 3:
                        books.remove(position);
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }

                Toast.makeText(BookListMainActivity.this,"点击了"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                int position=getAdapterPosition();
                MenuItem menuItemAdd=contextMenu.add(Menu.NONE,1,1,"Add"+position);
                MenuItem menuItemEdit=contextMenu.add(Menu.NONE,2,2,"Edit"+position);
                MenuItem menuItemelete=contextMenu.add(Menu.NONE,3,3,"Delete"+position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemelete.setOnMenuItemClickListener(this);
            }

        }
    }
}