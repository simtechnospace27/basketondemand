package simtechnospace.tech.basketondemand.pojoclass;

public class Cart {
    public static final String TABLE_NAME = "product_cart";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_QUANTITY = "product_quantity";
    public static final String COLUMN_PRODUCT_SKU = "product_sku";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String product_id;
    private String product_quantity;
    private  String product_sku;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ID + " TEXT,"
                    + COLUMN_PRODUCT_QUANTITY + " TEXT, "
                    + COLUMN_PRODUCT_SKU + " TEXT, "
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Cart() {

    }

    public Cart(int id, String product_id, String product_quantity, String product_sku, String timestamp) {
        this.id = id;
        this.product_id = product_id;
        this.product_quantity = product_quantity;
        this.product_sku = product_sku;
        this.timestamp = timestamp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}