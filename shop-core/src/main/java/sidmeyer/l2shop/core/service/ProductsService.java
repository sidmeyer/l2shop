package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.controller.CategoriesDao;
import sidmeyer.l2shop.core.controller.ProductsDao;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Stas on 15.08.2018.
 */
@Service
public class ProductsService {

	@Autowired
	private ProductsDao productsDao;

	@Autowired
	private CategoriesDao categoriesDao;

	public long createProduct(final Product product) {
		return productsDao.save(product).getId();
	}

	public void editProduct(final Product product) {
		productsDao.save(product);
	}

	public List<Product> getProducts() {
		return (List<Product>) productsDao.findAll();
	}

	public Product getProduct(final long productId) {
		return productsDao.findById(productId).get();
	}

	public void deleteProduct(final long productId) {
		productsDao.deleteById(productId);
	}

	public Set<Category> getCategoriesForProduct(final long productId) {
		return productsDao.findById(productId).get().getCategories();
	}

	public void editProductCategories(final long productId, final Set<Category> categories) {
		Product product = productsDao.findById(productId).get();
		product.setCategories(categories);
		productsDao.save(product);
	}

	@PostConstruct
	private void init() {

		//create test categories

		Category category1 = new Category();
		category1.setName("Food");
		categoriesDao.save(category1);

		Category category2 = new Category();
		category2.setName("Drinks");
		category2.setParentCategory(category1);
		categoriesDao.save(category2);

		Category category3 = new Category();
		category3.setName("Burgers");
		category3.setParentCategory(category1);
		categoriesDao.save(category3);

		Category category4 = new Category();
		category4.setName("Special");
		category4.setParentCategory(category1);
		categoriesDao.save(category4);

		// create test products

		Product pepsi = new Product();
		pepsi.setName("Pepsi");
		pepsi.setPrice(8.5);
		pepsi.setImageUrl("https://www.pepsi.com/en-us/uploads/images/can-real-sugar-reg.png");
		pepsi.setCategories(Collections.singleton(category2));
		productsDao.save(pepsi);

		Product lemonade = new Product();
		lemonade.setName("Lemonade");
		lemonade.setPrice(7.40);
		lemonade.setImageUrl("https://assets.simplyrecipes.com/wp-content/uploads/2006/06/lemonade-640-dm.jpg");
		lemonade.setCategories(Collections.singleton(category2));
		productsDao.save(lemonade);

		System.err.println("TEST PRODUCTS CREATED");
	}
}
