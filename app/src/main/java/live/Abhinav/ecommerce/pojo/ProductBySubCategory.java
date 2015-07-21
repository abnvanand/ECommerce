package live.Abhinav.ecommerce.pojo;

/**
 * Created by abhin on 7/21/2015.
 */
public class ProductBySubCategory {
    private String pName;
    private String pPrice;
    private String pUrlThumbnail;
    private String pSNo;


    public ProductBySubCategory() {
    }


    public ProductBySubCategory(String pName, String pPrice, String pUrlThumbnail, String pSNo) {
        this.pName = pName;
        this.pPrice = pPrice;
        this.pUrlThumbnail = pUrlThumbnail;
        this.pSNo = pSNo;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getpUrlThumbnail() {
        return pUrlThumbnail;
    }

    public void setpUrlThumbnail(String pUrlThumbnail) {
        this.pUrlThumbnail = pUrlThumbnail;
    }

    public String getpSNo() {
        return pSNo;
    }

    public void setpSNo(String pSNo) {
        this.pSNo = pSNo;
    }

    @Override
    public String toString() {
        return "Sno:" + pSNo +
                " Name " + pName +
                " Price " + pPrice +
                " UrlThumbnail " + pUrlThumbnail;
    }
}
