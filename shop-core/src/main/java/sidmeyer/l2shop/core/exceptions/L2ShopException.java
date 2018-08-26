package sidmeyer.l2shop.core.exceptions;

public class L2ShopException extends RuntimeException {
    public L2ShopException(String message, Object... params) {
        super(String.format(message, params));
    }
}
