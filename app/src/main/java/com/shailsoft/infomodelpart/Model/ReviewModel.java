package com.shailsoft.infomodelpart.Model;

/**
 * Created by ripegaurav on 7/10/2017.
 */
public class ReviewModel {

   private String UserImages;
    private String UserName;
    private Float StoreReview;
    private String StoreReviewTitle;
    private String StoreReviewDescription;
    private String ReviewTime;
    private int ReviewLike;
    private int TotalComments;
    private String Comments;

    public ReviewModel() {
    }

    public String getReviewTime() {
        return ReviewTime;
    }

    public void setReviewTime(String reviewTime) {
        ReviewTime = reviewTime;
    }

    public String getUserImages() {
        return UserImages;
    }

    public void setUserImages(String userImages) {
        UserImages = userImages;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Float getStoreReview() {
        return StoreReview;
    }

    public void setStoreReview(Float storeReview) {
        StoreReview = storeReview;
    }

    public String getStoreReviewTitle() {
        return StoreReviewTitle;
    }

    public void setStoreReviewTitle(String storeReviewTitle) {
        StoreReviewTitle = storeReviewTitle;
    }

    public String getStoreReviewDescription() {
        return StoreReviewDescription;
    }

    public void setStoreReviewDescription(String storeReviewDescription) {
        StoreReviewDescription = storeReviewDescription;
    }

    public int getReviewLike() {
        return ReviewLike;
    }

    public void setReviewLike(int reviewLike) {
        ReviewLike = reviewLike;
    }

    public int getTotalComments() {
        return TotalComments;
    }

    public void setTotalComments(int totalComments) {
        TotalComments = totalComments;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }
}
