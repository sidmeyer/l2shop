package sidmeyer.l2shop.core.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sidmeyer.l2shop.core.exceptions.L2ShopException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends L2ShopException {
    public NotFoundException(String message, Object... params) {
        super(message, params);
    }
}
