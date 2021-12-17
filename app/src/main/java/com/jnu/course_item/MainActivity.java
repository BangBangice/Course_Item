package com.jnu.course_item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPagerFragments=findViewById(R.id.viewpager2_content);
        viewPagerFragments.setAdapter(new MyFragmentAdpater(this));

        TabLayout tabLayoutHeader= findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayoutHeader, viewPagerFragments, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position==0)
                    tab.setText("图书");
                else if (position==1)
                    tab.setText("新闻");
                else
                    tab.setText("卖家");

            }
        });
        tabLayoutMediator.attach();

    }

    private class MyFragmentAdpater extends FragmentStateAdapter {


        public MyFragmentAdpater(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position)
            {
                case 0:
                    return BookListFragment.newInstance();

                case 1:
                    return WebViewFragment.newInstance();
                default:
                    return MapFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}