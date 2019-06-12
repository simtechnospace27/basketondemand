package simtechnospace.tech.basketondemand.pojoclass;

public class ShopByCategoryModel {

    int mCategoryId;
    String mCategoryName;
    String mCategoryImageUrl;
    int mContainsSubCat;



    public ShopByCategoryModel(int categoryId, String categoryName, String categoryImageUrl, int containsSubCat) {
        this.mCategoryId = categoryId;
        this.mCategoryName = categoryName;
        this.mCategoryImageUrl = categoryImageUrl;
        this.mContainsSubCat = containsSubCat;
    }



    public int getContainsSubCat() {
        return mContainsSubCat;
    }

    public void setContainsSubCat(int containsSubCat) {
        this.mContainsSubCat = containsSubCat;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(int categoryId) {
        this.mCategoryId = categoryId;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
    }

    public String getCategoryImageUrl() {
        return mCategoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.mCategoryImageUrl = categoryImageUrl;
    }
}
