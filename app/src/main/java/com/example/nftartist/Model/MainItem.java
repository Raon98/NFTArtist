package com.example.nftartist.Model;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple POJO model for example
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class MainItem {

    //mainpage
    private String mainPageTitle;
    private String content_to_Title;
    private String content_to_Ex;
    private String content_name_view;
    private String content_email_view;
    private String content_photoUrl;

    public MainItem(String mainPageTitle, String content_to_Title, String content_to_Ex, String content_name_view, String content_email_view, String content_photoUrl) {
        this.mainPageTitle = mainPageTitle;
        this.content_to_Title = content_to_Title;
        this.content_to_Ex = content_to_Ex;
        this.content_name_view = content_name_view;
        this.content_email_view = content_email_view;
        this.content_photoUrl = content_photoUrl;
    }
    public MainItem(String mainPageTitle,String content_photoUrl){
        this.mainPageTitle = mainPageTitle;
        this.content_photoUrl = content_photoUrl;
    }

    private View.OnClickListener requestBtnClickListener;

    public MainItem() {
    }



    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        FoldingItem foldingItem = (FoldingItem) o;
//
//        if (requestsCount != foldingItem.requestsCount) return false;
//        if (price != null ? !price.equals(foldingItem.price) : foldingItem.price != null) return false;
//        if (pledgePrice != null ? !pledgePrice.equals(foldingItem.pledgePrice) : foldingItem.pledgePrice != null)
//            return false;
//        if (fromAddress != null ? !fromAddress.equals(foldingItem.fromAddress) : foldingItem.fromAddress != null)
//            return false;
//        if (toAddress != null ? !toAddress.equals(foldingItem.toAddress) : foldingItem.toAddress != null)
//            return false;
//        if (date != null ? !date.equals(foldingItem.date) : foldingItem.date != null) return false;
//        return !(time != null ? !time.equals(foldingItem.time) : foldingItem.time != null);
//
//    }

//    @Override
//    public int hashCode() {
//        int result = price != null ? price.hashCode() : 0;
//        result = 31 * result + (pledgePrice != null ? pledgePrice.hashCode() : 0);
//        result = 31 * result + (fromAddress != null ? fromAddress.hashCode() : 0);
//        result = 31 * result + (toAddress != null ? toAddress.hashCode() : 0);
//        result = 31 * result + requestsCount;
//        result = 31 * result + (date != null ? date.hashCode() : 0);
//        result = 31 * result + (time != null ? time.hashCode() : 0);
//        return result;
//    }

    public String getMainPageTitle() {
        return mainPageTitle;
    }

    public void setMainPageTitle(String mainPageTitle) {
        this.mainPageTitle = mainPageTitle;
    }

    public String getContent_to_Title() {
        return content_to_Title;
    }

    public void setContent_to_Title(String content_to_Title) {
        this.content_to_Title = content_to_Title;
    }

    public String getContent_to_Ex() {
        return content_to_Ex;
    }

    public void setContent_to_Ex(String content_to_Ex) {
        this.content_to_Ex = content_to_Ex;
    }

    public String getContent_name_view() {
        return content_name_view;
    }

    public void setContent_name_view(String content_name_view) {
        this.content_name_view = content_name_view;
    }

    public String getContent_email_view() {
        return content_email_view;
    }

    public void setContent_email_view(String content_email_view) {
        this.content_email_view = content_email_view;
    }

    public String getContent_photoUrl() {
        return content_photoUrl;
    }

    public void setContent_photoUrl(String content_photoUrl) {
        this.content_photoUrl = content_photoUrl;
    }

    /**
     * @return List of elements prepared for tests
     */
    public Map<String, Object> getPostData(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("mainPageTitle",mainPageTitle);
        docData.put("content_to_Title",content_to_Title);
        docData.put("content_to_Ex",content_to_Ex);
        docData.put("content_name_view",content_name_view);
        docData.put("content_email_view",content_email_view);
        docData.put("content_photoUrl",content_photoUrl);
        return  docData;
    }


}
