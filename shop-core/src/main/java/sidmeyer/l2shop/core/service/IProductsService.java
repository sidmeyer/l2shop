package sidmeyer.l2shop.core.service;

import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;

import java.util.List;

public interface IProductsService {

    Product createProduct(final Product product);

    Product updateProduct(final Product product);

    void deleteProduct(final long productId);

    List<Product> getProducts();

    Product getProduct(final long productId);

    List<Category> getAllCategories();

    void addCategoryToProduct(final long productId, final long categoryId);

    void deleteCategoryFromProduct(final long productId, final long categoryId);
}
