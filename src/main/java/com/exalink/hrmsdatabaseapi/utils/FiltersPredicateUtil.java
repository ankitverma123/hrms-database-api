package com.exalink.hrmsdatabaseapi.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author ankitkverma
 *
 */
public class FiltersPredicateUtil {

	private FiltersPredicateUtil() {
		throw new IllegalStateException("Utility class");
	}

	static List<Predicate> predicates = new ArrayList<Predicate>();

	public static List<Predicate> generatePredicatesFilters(CriteriaBuilder builder, Root<?> r, String $filter) {
		predicates.clear();
		if ($filter != null) {
			String filtersToBeApplied[] = $filter.split(" and ");
			for (String filter : filtersToBeApplied) {
				if (filter.contains("(") && filter.contains("")) {
					String subString = filter.substring(filter.indexOf("(") + 1, filter.indexOf(")"));
					String subStringProperties[] = subString.split(",");
					String key = subStringProperties[0];
					String value = subStringProperties[1].substring(1, subStringProperties[1].length() - 1);
					if (filter.contains("startswith")) {
						if (r.get(key).getJavaType().getName().equals("java.lang.String")) {
							predicates.add(builder.like(r.get(key), value + "%"));
						} else if (r.get(key).getJavaType().getName().equals("java.lang.Long")) {
							predicates.add(builder.equal(r.get(key), value));
						}
					} else if (filter.contains("endswith")) {
						if (r.get(key).getJavaType().getName().equals("java.lang.String")) {
							predicates.add(builder.like(r.get(key), "%" + value));
						} else if (r.get(key).getJavaType().getName().equals("java.lang.Long")) {
							predicates.add(builder.equal(r.get(key), value));
						}
					} else if (filter.contains("indexof") && filter.contains("ge 0")) {
						if (r.get(key).getJavaType().getName().equals("java.lang.String")) {
							predicates.add(builder.like(r.get(key), "%" + value + "%"));
						} else if (r.get(key).getJavaType().getName().equals("java.lang.Long")) {
							predicates.add(builder.equal(r.get(key), value));
						}
					} else if (filter.contains("indexof") && filter.contains("ge -1")) {
						if (r.get(key).getJavaType().getName().equals("java.lang.String")) {
							predicates.add(builder.notLike(r.get(key), "%" + value + "%"));
						} else if (r.get(key).getJavaType().getName().equals("java.lang.Long")) {
							predicates.add(builder.notEqual(r.get(key), value));
						}
					}
				} else {
					if (filter.contains(" eq ")) {
						String subString[] = filter.split(" eq ");
						String key = subString[0];
						subString[1] = subString[1].trim();
						String value = subString[1].substring(1, subString[1].length() - 1);
						predicates.add(builder.equal(r.get(key), value));
					} else if (filter.contains(" ne ")) {
						String subString[] = filter.split(" ne ");
						String key = subString[0];
						subString[1] = subString[1].trim();
						String value = subString[1].substring(1, subString[1].length() - 1);
						predicates.add(builder.notEqual(r.get(key), value));
					}
				}
			}
		}

		return predicates;

	}

}
