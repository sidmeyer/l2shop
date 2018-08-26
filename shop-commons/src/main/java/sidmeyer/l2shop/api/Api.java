package sidmeyer.l2shop.api;

/**
 * Created by Stas on 16.08.2018.
 */
public interface Api {
    String ROOT = "/api";
    String USER = ROOT + "/user";
    String ADMIN = ROOT + "/admin";

	interface Users {
        String USERS = "/users";
        String USERS_ID = USERS + "/{userId}";
	}

	interface Orders {
        String ORDERS = "/orders";
        String ORDERS_ID = ORDERS + "/{orderId}";
        String PRODUCTS_IN_ORDER = ORDERS_ID + "/products";
	}

	interface Products {
        String PRODUCTS = "/products";
        String PRODUCTS_ID = PRODUCTS + "/{productId}";
        String PRODUCTS_CATEGORIES = PRODUCTS_ID + "/categories";
        String PRODUCTS_CATEGORIES_ID = PRODUCTS_CATEGORIES + "/{categoryId}";
	}

	interface Categories {
        String CATEGORIES = "/categories";
	}
}
