package sidmeyer.l2shop.core.exceptions;

public class OrderNotFoundException extends L2ShopException {
    public OrderNotFoundException(String message, Object... params) {
        super(message, params);
    }
}
