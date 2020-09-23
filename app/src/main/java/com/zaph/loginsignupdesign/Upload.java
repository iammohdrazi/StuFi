package com.zaph.loginsignupdesign;


public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload() {
        //Empty Constructor

    }
    public Upload(String name,String imageuel){
        if(name.trim().equals("")){
            name="No Name";
        }

        mName = name;
        mImageUrl = imageuel;
    }

    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public String getImageUrl(){
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
