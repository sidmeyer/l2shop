package sidmeyer.l2shop.core.exceptions;

public class ProductNotInStockException extends L2ShopException {
    public ProductNotInStockException(String message, Object... params) {
        super(message, params);
    }
}
