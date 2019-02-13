/**
 * 
 */
package br.com.adslima.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import br.com.adslima.model.Category;

/**
 * @author andrews.silva
 *
 */
public interface CategoryRepository extends SolrCrudRepository<Category, String> {

	Category findByCategoryId(String categoryId);

	@Query("edesc:*?0*")
	Page<Category> findByExpenseDescription(String searchTerm, Pageable pageable);

	@Query("edesc:*?0* OR cdesc:*?0*")
	Page<Category> findByCustomerQuery(String searchTerm, Pageable pageable);

	List<Category> findByExpenseDescription(String description);

	@Query("categories_txt:*?0*")
	@Facet(fields = { "categories_txt" }, limit = 5)
	FacetPage<Category> findByDescriptionAndFacetOnCategories(final String description, final Pageable page);

}