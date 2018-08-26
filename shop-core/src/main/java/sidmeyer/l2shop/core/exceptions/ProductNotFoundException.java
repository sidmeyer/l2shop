package sidmeyer.l2shop.core.exceptions;

public class ProductNotFoundException extends L2ShopException {
    public ProductNotFoundException(String message, Object... params) {
        super(message, params);
    }
}
