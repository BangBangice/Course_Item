package com.jnu.course_item.ui.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.course_item.R;
import com.jnu.course_item.model.DataBank;
import com.jnu.course_item.model.ShopItem;
import java.util.List;


public class BookItem extends Fragment {

    public static final int result_Code_add = 56;
    private List<ShopItem> shopItems;
    private MyRecyclerViewAdapter recycleViewAdapter;


    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == result_Code_add) {
                if (null == data) return;
                String name1 = data.getStringExtra("name");
                Double message1 = data.getDoubleExtra("message", 0);
                int position = data.getIntExtra("position", shopItems.size());
                int picture = data.getIntExtra("picture", R.drawable.a5);
                if(name1!=null) {
                    shopItems.add(position, new ShopItem(name1, picture, message1));
                    if(picture==R.drawable.a5){
                        Toast.makeText(BookItem.this.getContext(),"未输入图片采取默认图片",Toast.LENGTH_LONG).show();
                    }
                    dataBank.saveData();
                    recycleViewAdapter.notifyItemInserted(position);
                    Toast.makeText(BookItem.this.getContext(), "添加成功", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(BookItem.this.getContext(), "添加失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    });
    ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if (resultCode == result_Code_add) {
                if (null == data) return;
                String name2 = data.getStringExtra("name");
                Double message2 = data.getDoubleExtra("message", 0);
                int picture2 = data.getIntExtra("picture", 0);
                int position = data.getIntExtra("position", shopItems.size());
                shopItems.get(position).setName(name2);
                shopItems.get(position).setMessage(message2);
                shopItems.get(position).setPicture(picture2);
                dataBank.saveData();
                recycleViewAdapter.notifyItemChanged(position);
                Toast.makeText(BookItem.this.getContext(), "修改成功", Toast.LENGTH_LONG).show();
            }
        }
    });
    private DataBank dataBank;

    public BookItem() {
        // Required empty public constructor
    }
    public static BookItem newInstance() {
        BookItem fragment = new BookItem();
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
        // Inflate the layout for this fragment
        initData();
        View rootView=inflater.inflate(R.layout.fragment_book_item, container, false);
        FloatingActionButton fadAdd=rootView.findViewById(R.id.floating_Action_Button);
        fadAdd.setOnClickListener(view->{
            Intent intent=new Intent(this.getContext(),inputActivity.class);
            intent.putExtra("position",shopItems.size());
            launcherAdd.launch(intent);
        });

        RecyclerView MyRecyclerView = rootView.findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        MyRecyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new MyRecyclerViewAdapter(shopItems);
        MyRecyclerView.setAdapter(recycleViewAdapter);
        return rootView;
    }

    public void initData(){
        dataBank = new DataBank(this.getContext());
        shopItems= dataBank.loadData();
    }

    private  class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<ShopItem> shopItems;

        public MyRecyclerViewAdapter(List<ShopItem> shopItems) {
            this.shopItems = shopItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            MyViewHolder holder = (MyViewHolder) Holder;
            holder.getImageView().setImageResource(shopItems.get(position).getPicture());
            holder.getTextViewName().setText(shopItems.get(position).getName());
            holder.getTextViewMessage().setText("价格：" + String.valueOf(shopItems.get(position).getMessage()));
        }

        @Override
        public int getItemCount() {
            return shopItems.size();
        }


        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

            public static final int item_add = 1;
            public static final int item_edit = 2;
            public static final int item_delete = 3;
            private final ImageView imageView;
            private final TextView textViewName;
            private final TextView textViewMessage;


            public MyViewHolder(View itemView) {

                super(itemView);

                this.imageView = itemView.findViewById(R.id.image_view_book_cover);
                this.textViewName = itemView.findViewById(R.id.book_text_name);
                this.textViewMessage = itemView.findViewById(R.id.book_text_message);

                itemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            public TextView getTextViewMessage() {
                return textViewMessage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu Contextmenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int position = getAdapterPosition();
                Contextmenu.setHeaderTitle("具体操作");
                MenuItem menuItemAdd = Contextmenu.add(Menu.NONE, item_add, item_add, "添加" + position);
                MenuItem menuItemEdit = Contextmenu.add(Menu.NONE, item_edit, item_edit, "修改" + position);
                MenuItem menuItemDelete = Contextmenu.add(Menu.NONE, item_delete, item_delete, "删除" + position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position = getAdapterPosition();
                Intent intent;
                switch (menuItem.getItemId()) {
                    case item_add:
                        intent=new Intent(BookItem.this.getContext(),inputActivity.class);
                        intent.putExtra("position",position);
                        launcherAdd.launch(intent);
                        break;

                    case item_edit:
                        intent=new Intent(BookItem.this.getContext(),inputActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("name",shopItems.get(position).getName());
                        intent.putExtra("message",shopItems.get(position).getMessage());
                        intent.putExtra("picture",shopItems.get(position).getPicture());
                        launcherEdit.launch(intent);
                        break;

                    case item_delete:
                        AlertDialog.Builder alertDB=new AlertDialog.Builder(BookItem.this.getContext());
                        alertDB.setPositiveButton(BookItem.this.getContext().getResources().getString(R.string.string_confirmation), (dialogInterface, i) -> {
                            shopItems.remove(position);
                            MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                            dataBank.saveData();
                            Toast.makeText(BookItem.this.getContext(), "删除成功", Toast.LENGTH_LONG).show();
                        });
                        alertDB.setNegativeButton(BookItem.this.getContext().getResources().getString(R.string.string_cancel), (dialogInterface, i) -> {
                        });
                        alertDB.setMessage(BookItem.this.getContext().getResources().getString(R.string.string_confirm_delete) +shopItems.get(position).getName()+"？");
                        alertDB.setTitle(BookItem.this.getContext().getResources().getString(R.string.hint)).show();
                        break;
                }
                return false;
            }
        }
    }
}