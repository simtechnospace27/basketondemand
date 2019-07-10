package simtechnospace.tech.basketondemand.pojoclass;

public class ProductListModel {
    String mImgUrl,mProductContent,mProductName,mProductId;
    int mDiscountPrice;
    double mOriginalMrp;

    String mSubcatName, mCatName;



    public ProductListModel(String mProductId, String mImgUrl,String ProductName ,String mProductContent, int mDiscountPrice, double mOriginalMrp) {
        this.mImgUrl = mImgUrl;
        this.mProductName = ProductName;
        this.mProductContent = mProductContent;
        this.mDiscountPrice = mDiscountPrice;
        this.mOriginalMrp = mOriginalMrp;
        this.mProductId = mProductId;
    }

    public ProductListModel(String mProductId, String mImgUrl, String mProductName, String mProductContent,   int mDiscountPrice, double mOriginalMrp, String mSubcatName, String mCatName) {
        this.mImgUrl = mImgUrl;
        this.mProductContent = mProductContent;
        this.mProductName = mProductName;
        this.mProductId = mProductId;
        this.mDiscountPrice = mDiscountPrice;
        this.mOriginalMrp = mOriginalMrp;
        this.mSubcatName = mSubcatName;
        this.mCatName = mCatName;
    }

    public String getmSubcatName() {
        return mSubcatName;
    }

    public void setmSubcatName(String mSubcatName) {
        this.mSubcatName = mSubcatName;
    }

    public String getmCatName() {
        return mCatName;
    }

    public void setmCatName(String mCatName) {
        this.mCatName = mCatName;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public String getmProductContent() {
        return mProductContent;
    }

    public void setmProductContent(String mProductContent) {
        this.mProductContent = mProductContent;
    }

    public int getmDiscountPrice() {
        return mDiscountPrice;
    }

    public void setmDiscountPrice(int mDiscountPrice) {
        this.mDiscountPrice = mDiscountPrice;
    }

    public double getmOriginalMrp() {
        return mOriginalMrp;
    }

    public void setmOriginalMrp(double mOriginalMrp) {
        this.mOriginalMrp = mOriginalMrp;
    }
}
