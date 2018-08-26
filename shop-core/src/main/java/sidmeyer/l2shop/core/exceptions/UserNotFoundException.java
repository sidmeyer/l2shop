package sidmeyer.l2shop.core.exceptions;

public class UserNotFoundException extends L2ShopException {
    public UserNotFoundException(String message, Object... params) {
        super(message, params);
    }
}
