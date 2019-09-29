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
			String[] filtersToBeApplied = $filter.split(" and ");
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
						String columns[] = subString[0].split(",");
						String value = subString[1];
						if(columns.length==1)
							predicates.add(builder.equal(r.get(columns[0]), value));
						else if (columns.length==2)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]), value));
						else if (columns.length==3)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]), value));
						else if (columns.length==4)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]).get(columns[3]), value));
						else if (columns.length==5)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]).get(columns[3]).get(columns[4]), value));
						else if (columns.length==6)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]).get(columns[3]).get(columns[4]).get(columns[5]), value));
						else if (columns.length==7)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]).get(columns[3]).get(columns[4]).get(columns[5]).get(columns[6]), value));
						else if (columns.length==8)
							predicates.add(builder.equal(r.get(columns[0]).get(columns[1]).get(columns[2]).get(columns[3]).get(columns[4]).get(columns[5]).get(columns[6]).get(columns[7]), value));
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
