package com.example.a3.testapp.SupportClasses;

import com.example.a3.testapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IBM on 7/19/2018.
 */

public  class AssetSupport {

    private static final List<Integer> heads = new ArrayList<Integer>() {{
        add(0);
        add(R.drawable.icon4);
        add(R.drawable.icon1);
        add(R.drawable.icon1);
        add(R.drawable.icon2);
        add(R.drawable.icon5);
        add(R.drawable.icon13);
        add(R.drawable.icon3);
        add(R.drawable.icon13);
        add(R.drawable.icon1);
        add(R.drawable.icon1);
        add(R.drawable.icon9);
        add(R.drawable.icon17);
        add(R.drawable.icon6);
        add(R.drawable.icon6);
        add(R.drawable.icon7);
        add(R.drawable.icon8);
        add(R.drawable.icon8);
        add(R.drawable.icon16);
        add(R.drawable.icon9);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon20);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon5);
        add(R.drawable.icon20);
        add(R.drawable.icon9);
        add(R.drawable.icon15);
        add(R.drawable.icon15);
        add(R.drawable.icon10);
        add(R.drawable.icon12);
        add(R.drawable.icon22);
        add(R.drawable.icon18);
        add(R.drawable.icon18);
        add(R.drawable.icon14);
        add(R.drawable.icon14);
        add(R.drawable.icon27);
        add(R.drawable.icon27);
        add(R.drawable.icon27);
        add(R.drawable.icon27);
        add(R.drawable.icon27);


    }};

    public int getId(int i){
        if(i>heads.size()){
            return heads.get(1);
        }else {
            return heads.get(i);
        }
    }

}
