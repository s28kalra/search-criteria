package com.example.search.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.search.criteria.SearchCriteria.SearchOperation;

/**
 * @author sagar.kalra
 */

@RestController
public class SearchCriteriaController {

	@Autowired
	private UserDetailRepository userDetailRepository;

	@PostMapping("/test-search-criteria")
	public Object testSearchCriteria(@RequestBody List<SearchCriteria> criterias) {
		Specification<UserDetail> specification = buildSpecification(criterias);
		return userDetailRepository.findAll(specification);
	}

	private <T> Specification<T> buildSpecification(List<SearchCriteria> criterias) {
		Function<SearchCriteria, Specification<T>> converter = GenericSpecification::new;
		final List<Specification<T>> specs = criterias.stream().map(converter)
				.collect(Collectors.toCollection(ArrayList::new));
		Specification<T> result = specs.get(0);

		for (int idx = 1; idx < specs.size(); idx++) {
			result = criterias.get(idx).isOrPredicate() ? Specification.where(result).or(specs.get(idx))
					: Specification.where(result).and(specs.get(idx));
		}

		return result;
	}
	
	@PostConstruct
	public void testFindByFirstNameOrLastName() {
		List<SearchCriteria> criterias = Arrays.asList(
				new SearchCriteria("firstName", "sagar", SearchOperation.MATCH_IGNORE_CASE),
				new SearchCriteria(true, "lastName", "bhatia", SearchOperation.MATCH_IGNORE_CASE));
		Object o= testSearchCriteria(criterias);
//		Object o = WebClient.builder().build().post().uri("localhost:6767/test-search-criteria").bodyValue(criterias)
//				.retrieve().bodyToMono(Object.class).block();
		System.out.println(o);
	}

}
