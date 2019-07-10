package simtechnospace.tech.basketondemand.pojoclass;

public class CardDetailsModel {

    String mImgUrl,mProductContent,mProductName,mProductId,mTax;
    int mDiscountPrice;
    double mOriginalMrp;
    Double mProductQuantityt;

    public CardDetailsModel(String mProductId, String mImgUrl,String ProductName ,String mProductContent, int mDiscountPrice, double mOriginalMrp, Double  mProductQuantityt, String mTax) {
        this.mImgUrl = mImgUrl;
        this.mProductName = ProductName;
        this.mProductContent = mProductContent;
        this.mDiscountPrice = mDiscountPrice;
        this.mOriginalMrp = mOriginalMrp;
        this.mProductId = mProductId;
        this.mProductQuantityt = mProductQuantityt;
        this.mTax = mTax;
    }

    public String getmTax() {
        return mTax;
    }

    public void setmTax(String mTax) {
        this.mTax = mTax;
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

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
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

    public Double getmProductQuantityt() {
        return mProductQuantityt;
    }

    public void setmProductQuantityt(Double mProductQuantityt) {
        this.mProductQuantityt = mProductQuantityt;
    }
}
