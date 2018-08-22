package sidmeyer.l2shop.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sidmeyer.l2shop.core.model.Category;

/**
 * Created by Stas on 19.08.2018.
 */
@Repository
public interface CategoriesDao extends CrudRepository<Category, Long> {
}
