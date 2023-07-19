package com.example.btl_foodapp_2_7.Project.Fragments;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btl_foodapp_2_7.Project.Activity.MainActivity;
import com.example.btl_foodapp_2_7.Project.Activity.SlideItem;
import com.example.btl_foodapp_2_7.Project.Adapter.CategoryListAdapter;
import com.example.btl_foodapp_2_7.Project.Adapter.FoodListAdapter;
import com.example.btl_foodapp_2_7.Project.Adapter.SlideAdapter;
import com.example.btl_foodapp_2_7.Project.Model.Category;
import com.example.btl_foodapp_2_7.Project.Model.ProductFoodShare;
import com.example.btl_foodapp_2_7.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment_trang_chu extends Fragment {
    private RecyclerView.Adapter adapterFoodList;
    private RecyclerView recyclerViewFood;
    ViewPager2 viewPager2;

    RecyclerView homeHorizontalRec, homeVerticalRec;
    List<Category> homeHormodelList;
    CategoryListAdapter homeHorAdapter;
//    MyDAO mydao;
    CheckBox checkFav;

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);


        //Hiển thị danh sách danh mục món ăn
        homeHorizontalRec = view.findViewById(R.id.home_hor_rec);
        homeHormodelList = new ArrayList<>();
        homeHorizontalRec = view.findViewById(R.id.home_hor_rec);
        homeHormodelList = new ArrayList<>();
//        String mgs;
//
//        if (mydao.getCategory().moveToFirst()) {
//            do {
//                String name = mydao.getCategory().getString(0);
//                int image = mydao.getCategory().getInt(1);
////                homeHormodelList.add(new Category(image, name));
//                mgs = name;
//            } while (mydao.getCategory().moveToNext());
//
//            Toast.makeText(MainActivity.this, )
//            mydao.getCategory().close();
//        }



        homeHormodelList.add(new Category(R.drawable.pic_ansang, "Sáng"));
        homeHormodelList.add(new Category(R.drawable.pic_buatrua, "Trưa"));
        homeHormodelList.add(new Category(R.drawable.pic_buatoi, "Tối"));
        homeHormodelList.add(new Category(R.drawable.pic_anlau, "Lẩu"));
        homeHormodelList.add(new Category(R.drawable.pic_anchay, "Chay"));
        homeHormodelList.add(new Category(R.drawable.pic_banh, "Bánh"));
        homeHormodelList.add(new Category(R.drawable.pic_tea, "Trà sữa"));
        homeHormodelList.add(new Category(R.drawable.pic_kem, "Kem"));
        homeHorAdapter = new CategoryListAdapter(getActivity(), homeHormodelList);

        homeHorizontalRec.setAdapter(homeHorAdapter);

        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false ));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);



        viewPager2 = view.findViewById(R.id.viewPager);

        List<SlideItem> slideItems = new ArrayList<>();
        slideItems.add(new SlideItem(R.drawable.slide1));
        slideItems.add(new SlideItem(R.drawable.slide2));
        slideItems.add(new SlideItem(R.drawable.slide3));
        slideItems.add(new SlideItem(R.drawable.slide4));
        slideItems.add(new SlideItem(R.drawable.slide5));

        viewPager2.setAdapter(new SlideAdapter(slideItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);


            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
        return view;


    }



    //foodshare
    @SuppressLint("Range")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SQLiteDatabase db = MainActivity.getDatabase();
//
        Cursor cursor = db.rawQuery("SELECT food.name as 'foodname', food.description as 'description', food.img as 'img', food.thoiGianLam as 'thoiGianLam', food.rate as 'rate', food.ngayDang as 'ngayDang', food.favorite as 'favorite', user.name FROM food, user  where food.userID = user.id", null);
        ArrayList<ProductFoodShare> items = new ArrayList<>();
        while (cursor.moveToNext()) {
//            String name = cursor.getString(cursor.getColumnIndex("user.name"))
            String username = cursor.getString(cursor.getColumnIndex("name"));
//            Integer value = Integer.valueOf(cursor.getColumnName(7));
            String title = cursor.getString(cursor.getColumnIndex("foodname"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String time = cursor.getString(cursor.getColumnIndex("thoiGianLam"));
            Integer rate = Integer.valueOf(cursor.getString(cursor.getColumnIndex("rate")));
            String dateTime = cursor.getString(cursor.getColumnIndex("ngayDang"));
            Integer fav = Integer.valueOf(cursor.getString(cursor.getColumnIndex("favorite")));
            items.add(new ProductFoodShare(username, title, description, img, time, rate, fav));
        }
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFood = view.findViewById(R.id.view1);
        recyclerViewFood.setLayoutManager(linearLayoutManager);


        adapterFoodList= new FoodListAdapter(items);
        recyclerViewFood.setAdapter(adapterFoodList);




    }



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}