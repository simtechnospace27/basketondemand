package simtechnospace.tech.basketondemand.pojoclass;

public class ProductListModel {
    String mImgUrl,mProductContent,mProductName;
    int mDiscountPrice;
    double mOriginalMrp;


    public ProductListModel(String mImgUrl,String ProductName ,String mProductContent, int mDiscountPrice, double mOriginalMrp) {
        this.mImgUrl = mImgUrl;
        this.mProductName = ProductName;
        this.mProductContent = mProductContent;
        this.mDiscountPrice = mDiscountPrice;
        this.mOriginalMrp = mOriginalMrp;
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
