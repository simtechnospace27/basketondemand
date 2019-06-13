package simtechnospace.tech.basketondemand.pojoclass;

public class ClickedCategoryForProducts {

    public static int mSubCatId;
    public static int mCatId;

    public ClickedCategoryForProducts(int subCatId, int catId) {
        this.mSubCatId = subCatId;
        this.mCatId = catId;
    }

    public static int getSubCatId() {
        return mSubCatId;
    }

    public void setSubCatId(int subCatId) {
        this.mSubCatId = subCatId;
    }

    public static int getCatId() {
        return mCatId;
    }

    public void setCatId(int catId) {
        this.mCatId = catId;
    }
}
