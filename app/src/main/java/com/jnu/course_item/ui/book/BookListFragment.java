package com.jnu.course_item.ui.book;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.course_item.R;
import com.jnu.course_item.model.Book;

import java.util.ArrayList;
import java.util.List;


public class BookListFragment extends Fragment {

    public static final int RESULT_CODE_ADD_DATA = 996;
    public static final int REQUEST_CODE_ADD = 123;
    public static final int REQUEST_CODE_EDIT = REQUEST_CODE_ADD+1;
    View rootView ;
    List<Book> books;
    private MyRecyclerViewAdapter recyclerViewAdapter;



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode== REQUEST_CODE_ADD){     //请求的代码是123
//            if(resultCode== RESULT_CODE_ADD_DATA){    //返回的代码是996
//                String name=data.getStringExtra("name");
//                int position=data.getIntExtra("position",books.size()); //最后
//                books.add(position,new Book(name, R.drawable.book_3));
//                recyclerViewAdapter.notifyItemInserted(position);
//            }
//
//        }
//        if(requestCode== REQUEST_CODE_EDIT){     //请求的代码是123
//            String name=data.getStringExtra("name");
//            int position=data.getIntExtra("position",books.size()); //最后
//            books.get(position).setName(name);
//            recyclerViewAdapter.notifyItemChanged(position);
//        }
//
//    }


    public List<Book> getListBooks(){
        return books;
    }
    public void initDate(){
        books =new ArrayList<Book>();
        books.add(new Book("软件项目管理案例教程(第4版本)",R.drawable.book_1));
        books.add(new Book("创新工程实践",R.drawable.book_2));
        books.add(new Book("信息安全数学基础(第2版)",R.drawable.book_3));
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
        initDate();

        rootView=inflater.inflate(R.layout.fragment_book_list,container,false);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView mainRecycleView=rootView.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        mainRecycleView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new MyRecyclerViewAdapter(books, this.getContext());

        mainRecycleView.setAdapter(recyclerViewAdapter);

    }
}