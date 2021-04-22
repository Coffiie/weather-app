package com.example.weatherapp;

public class ExampleItem {
    private int mImageResouce;
    private  String mText1;
    private  String mText2;

    public ExampleItem(int ImageResouce,String Text1,String Text2){
        mImageResouce=ImageResouce;
        mText1=Text1;
        mText2=Text2;
    }

    public int getmImageResouce(){
        return mImageResouce;
    }
    public String getmTex1(){
        return mText1;
    }
    public String getmTex2(){
        return mText2;
    }
}
