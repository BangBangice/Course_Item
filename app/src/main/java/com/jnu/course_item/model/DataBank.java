package com.jnu.course_item.model;

import android.content.Context;

import com.jnu.course_item.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static final String DATA_File_Name = "data";
    private final Context context;
    List<ShopItem> shopItemList;

    public DataBank(Context context) {
        this.context=context;

    }

    public List<ShopItem> loadData() {
        shopItemList=new ArrayList<>();
        shopItemList.add(new ShopItem("软件工程专业英语",R.drawable.a1,5.6));
        shopItemList.add(new ShopItem("软件项目管理",R.drawable.a2,8.6));
        shopItemList.add(new ShopItem("数字图像处理",R.drawable.a3,2.6));
        shopItemList.add(new ShopItem("Java编程",R.drawable.a4,1.6));
        try{
            ObjectInputStream objectInputStream=new ObjectInputStream(context.openFileInput(DATA_File_Name));
            shopItemList=(ArrayList<ShopItem>) objectInputStream.readObject();
        }
        catch(Exception error){
            error.printStackTrace();
        }
        return shopItemList;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;

        try {
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_File_Name, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(shopItemList);
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (Exception error) {
                error.printStackTrace();
            }

        }
    }
}
