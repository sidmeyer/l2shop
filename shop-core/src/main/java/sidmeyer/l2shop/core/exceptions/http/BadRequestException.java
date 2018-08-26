package sidmeyer.l2shop.core.exceptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sidmeyer.l2shop.core.exceptions.L2ShopException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends L2ShopException {
    public BadRequestException(String message, Object... params) {
        super(message, params);
    }
}
