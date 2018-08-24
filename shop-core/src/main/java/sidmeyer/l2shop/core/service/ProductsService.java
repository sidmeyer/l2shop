package sidmeyer.l2shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sidmeyer.l2shop.core.exceptions.ProductNotFoundException;
import sidmeyer.l2shop.core.model.Category;
import sidmeyer.l2shop.core.model.Product;
import sidmeyer.l2shop.core.repository.CategoriesDao;
import sidmeyer.l2shop.core.repository.ProductsDao;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stas on 15.08.2018.
 */
@Service
public class ProductsService implements IProductsService {

	@Autowired
	private ProductsDao productsDao;

	@Autowired
	private CategoriesDao categoriesDao;

	@Override
	public Product createProduct(final Product product) {
		return productsDao.save(product);
	}

	@Override
	public Product updateProduct(final Product product) {
		if (!productsDao.findById(product.getId()).isPresent()) {
			throw new ProductNotFoundException("Product  with id " + product.getId() + " does not exist.");
		}
		return productsDao.save(product);
	}

	@Override
	public List<Product> getProducts() {
		return (List<Product>) productsDao.findAll();
	}

	@Override
	public Product getProduct(final long productId) {
		return productsDao.findById(productId).get();
	}

	@Override
	public void deleteProduct(final long productId) {
		productsDao.deleteById(productId);
	}

	public List<Category> getCategoriesForProduct(final long productId) {
		return productsDao.findById(productId).get().getCategories();
	}

	public void editProductCategories(final long productId, final List<Category> categories) {
		Product product = productsDao.findById(productId).get();
		product.setCategories(categories);
		productsDao.save(product);
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = (List<Category>) categoriesDao.findAll();
		return categories;
	}

	@Override
	public void addCategoryToProduct(final long productId, final long categoryId) {
		Category category = categoriesDao.findById(categoryId).get();
		Product product = getProduct(productId);
		product.getCategories().add(category);
		productsDao.save(product);
	}

	@Override
	public void deleteCategoryFromProduct(final long productId, final long categoryId) {
		Category category = categoriesDao.findById(categoryId).get();
		Product product = getProduct(productId);
		product.getCategories().remove(category);
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
		pepsi.setCategories(Collections.singletonList(category2));
		productsDao.save(pepsi);

		Product lemonade = new Product();
		lemonade.setName("Lemonade");
		lemonade.setPrice(7.40);
		lemonade.setImageUrl("https://assets.simplyrecipes.com/wp-content/uploads/2006/06/lemonade-640-dm.jpg");
		lemonade.setCategories(Collections.singletonList(category2));
		productsDao.save(lemonade);

		System.err.println("TEST PRODUCTS CREATED");
	}
}
