package sidmeyer.l2shop.core.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sidmeyer.l2shop.core.model.ProductInOrder;

/**
 * Created by Stas on 17.08.2018.
 */
@Repository
public interface ProductsInOrdersDao extends CrudRepository<ProductInOrder, Long> {
}
