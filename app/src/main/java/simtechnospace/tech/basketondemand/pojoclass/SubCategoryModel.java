package simtechnospace.tech.basketondemand.pojoclass;

public class SubCategoryModel {

    String mSubCategoryName;
    String mSubCategoryImageUrl;
    int mSubcategoryId;

    public SubCategoryModel(String mSubCategoryName, String mSubCategoryImageUrl, int mSubcategoryId) {
        this.mSubCategoryName = mSubCategoryName;
        this.mSubCategoryImageUrl = mSubCategoryImageUrl;
        this.mSubcategoryId = mSubcategoryId;
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
