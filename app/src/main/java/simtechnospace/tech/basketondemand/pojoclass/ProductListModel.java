package simtechnospace.tech.basketondemand.pojoclass;

public class ProductListModel {
    String mImgUrl,mProductContent,mProductName,mProductId;
    int mDiscountPrice;
    double mOriginalMrp;



    public ProductListModel(String mProductId, String mImgUrl,String ProductName ,String mProductContent, int mDiscountPrice, double mOriginalMrp) {
        this.mImgUrl = mImgUrl;
        this.mProductName = ProductName;
        this.mProductContent = mProductContent;
        this.mDiscountPrice = mDiscountPrice;
        this.mOriginalMrp = mOriginalMrp;
        this.mProductId = mProductId;
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
