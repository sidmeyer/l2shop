package sidmeyer.l2shop.api;

/**
 * Created by Stas on 16.08.2018.
 */
public interface Api {
	interface Users {
		String USERS_PATH = "/users";
		String USERS_ID_PATH = USERS_PATH + "/{userId}";
	}

	interface Orders {
		String ORDERS_PATH = "/orders";
		String ORDERS_ID_PATH = ORDERS_PATH + "/{orderId}";
		String PRODUCTS_IN_ORDER_PATH = ORDERS_ID_PATH + "/products";
	}

	interface Products {
		String PRODUCTS_PATH = "/products";
		String PRODUCTS_ID_PATH = PRODUCTS_PATH + "/{productId}";
		String PRODUCTS_CATEGORIES_PATH = PRODUCTS_ID_PATH + "/categories";
		String PRODUCTS_CATEGORIES_ID_PATH = PRODUCTS_CATEGORIES_PATH + "/{categoryId}";
	}

	interface Categories {
		String CATEGORIES_PATH = "/categories";
	}
}
