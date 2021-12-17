package com.jnu.course_item;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BookListFragment extends Fragment {

    public static final int RESULT_CODE_ADD_DATA = 996;
    public static final int REQUEST_CODE_ADD = 123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE_ADD+1;
    List<Book> books;
    private BookListFragment.MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE_ADD){     //请求的代码是123
            if(resultCode== RESULT_CODE_ADD_DATA){    //返回的代码是996
                String name=data.getStringExtra("name");
                int position=data.getIntExtra("position",books.size()); //最后
                books.add(position,new Book(name,R.drawable.book_3));
                recyclerViewAdapter.notifyItemInserted(position);
            }

        }
        if(requestCode== REQUEST_CODE_EDIT){     //请求的代码是123
            String name=data.getStringExtra("name");
            int position=data.getIntExtra("position",books.size()); //最后
            books.get(position).setName(name);
            recyclerViewAdapter.notifyItemChanged(position);
        }

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

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {   //为recyclerview写适配器
        private final List<Book> books;

        public MyRecyclerViewAdapter(List<Book> books) {
            this.books = books;
        } //将外部数据赋值给内部
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout,parent,false);
            return new BookListFragment.MyRecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {  //给布局中的项目，设定资源
            BookListFragment.MyRecyclerViewAdapter.MyViewHolder holder=(BookListFragment.MyRecyclerViewAdapter.MyViewHolder)Holder;
            holder.getImageView().setImageResource(books.get(position).getCoverResourceId());
            holder.getTextViewName().setText(books.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }


        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            public static final int CONTEX_MENU_ID_ADD = 1;
            public static final int CONTEX_MENU_ID_UPDATE = 2;
            public static final int CONTEX_MENU_ID_DELETE = 3;
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
                Intent intent;
                switch(menuItem.getItemId())
                {
                    case CONTEX_MENU_ID_ADD:
//                        View dialagueView= LayoutInflater.from(BookListFragment.this).inflate(R.layout.dialogue_input_item,null);
//                        AlertDialog.Builder alertDialogBuiler = new AlertDialog.Builder(BookListFragment.this);
//                        alertDialogBuiler.setView(dialagueView);
//
//                        alertDialogBuiler.setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                EditText editName=dialagueView.findViewById(R.id.edit_text_name);
//                                //EditText editPrice=dialagueView.findViewById(R.id.edit_text_price);
//                                books.add(position,new Book(editName.getText().toString(),R.drawable.book_3));
//                                MyRecyclerViewAdapter.this.notifyItemInserted(position);
//                            }
//                        });
//                        alertDialogBuiler.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        alertDialogBuiler.create().show();;
                        //对话框，不要了
                        intent=new Intent(BookListFragment.this.getContext(),InputActivity.class);
                        intent.putExtra("position",position);
                        BookListFragment.this.startActivityForResult(intent, REQUEST_CODE_ADD);

                        break;
                    case CONTEX_MENU_ID_UPDATE:
                        intent=new Intent(BookListFragment.this.getContext(),InputActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("name",books.get(position).getTitle());
                        BookListFragment.this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                        break;

                    case CONTEX_MENU_ID_DELETE:
                        books.remove(position);
                        BookListFragment.MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }

                Toast.makeText(BookListFragment.this.getContext(),"点击了"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                int position=getAdapterPosition();
                MenuItem menuItemAdd=contextMenu.add(Menu.NONE, CONTEX_MENU_ID_ADD, CONTEX_MENU_ID_ADD,"Add"+position);
                MenuItem menuItemEdit=contextMenu.add(Menu.NONE, CONTEX_MENU_ID_UPDATE, CONTEX_MENU_ID_UPDATE,"Edit"+position);
                MenuItem menuItemelete=contextMenu.add(Menu.NONE, CONTEX_MENU_ID_DELETE, CONTEX_MENU_ID_DELETE,"Delete"+position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemelete.setOnMenuItemClickListener(this);
            }

        }
    }


    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // initDate();

        View rootView=inflater.inflate(R.layout.fragment_book_list,container,false);

        RecyclerView mainRecycleView=rootView.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new BookListFragment.MyRecyclerViewAdapter(books);
        Log.d("tag",recyclerViewAdapter.toString());
        mainRecycleView.setAdapter(recyclerViewAdapter);
        if (recyclerViewAdapter == null) {
            Log.e("TAG", "No adapter attached; skipping layout");
            // leave the state in START

        }
        if (layoutManager == null) {
            Log.e("TAG", "No layout manager attached; skipping layout");
            // leave the state in START

        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }



}