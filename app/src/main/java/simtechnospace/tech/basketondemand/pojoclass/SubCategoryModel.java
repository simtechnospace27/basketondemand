package simtechnospace.tech.basketondemand.pojoclass;

public class SubCategoryModel {

    String mSubCategoryName;
    String mSubCategoryImageUrl;
    int mSubcategoryId;
    int mCatId;

    public SubCategoryModel(String mSubCategoryName, String mSubCategoryImageUrl, int mSubcategoryId, int catId) {
        this.mSubCategoryName = mSubCategoryName;
        this.mSubCategoryImageUrl = mSubCategoryImageUrl;
        this.mSubcategoryId = mSubcategoryId;
        this.mCatId = catId;
    }

    public int getCatId() {
        return mCatId;
    }

    public void setCatId(int catId) {
        this.mCatId = catId;
    }

    public String getSubCategoryName() {
        return mSubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.mSubCategoryName = subCategoryName;
    }

    public String getSubCategoryImageUrl() {
        return mSubCategoryImageUrl;
    }

    public void setSubCategoryImageUrl(String subCategoryImageUrl) {
        this.mSubCategoryImageUrl = subCategoryImageUrl;
    }

    public int getSubcategoryId() {
        return mSubcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.mSubcategoryId = subcategoryId;
    }


}
