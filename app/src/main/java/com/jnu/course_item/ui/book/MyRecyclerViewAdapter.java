package com.jnu.course_item.ui.book;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static com.jnu.course_item.ui.book.BookListFragment.REQUEST_CODE_ADD;
import static com.jnu.course_item.ui.book.BookListFragment.REQUEST_CODE_EDIT;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.course_item.R;
import com.jnu.course_item.model.Book;

import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter {   //为recyclerview写适配器
    private final List<Book> books;
    private final Context context;

    public MyRecyclerViewAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    } //将外部数据赋值给内部
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout,parent,false);
        return new MyRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {  //给布局中的项目，设定资源
        MyRecyclerViewAdapter.MyViewHolder holder=(MyRecyclerViewAdapter.MyViewHolder)Holder;
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
            this.textViewName=view.findViewById(R.id.book_text_name);

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
                    intent=new Intent(context, inputActivity.class);
                    intent.putExtra("position",position);
                    //startActivityForResult(intent, REQUEST_CODE_ADD);


                    break;
                case CONTEX_MENU_ID_UPDATE:
                    intent=new Intent(context,inputActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("name",books.get(position).getTitle());
                    //startActivityForResult(intent, REQUEST_CODE_EDIT);
                    break;

                case CONTEX_MENU_ID_DELETE:
                    books.remove(position);
                    MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                    break;
            }

            Toast.makeText(context,"点击了"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
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