package com.exalink.hrmsdatabaseapi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.exalink.hrmsfabric.common.CommonConstants;
import com.exalink.hrmsfabric.common.Utils;

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
			String[] filtersToBeApplied = $filter.split(",");
			for (String filter : filtersToBeApplied) {
				if (filter.contains(" eq ")) {
					String[] subString = filter.split(" eq ");
					String columnName = subString[0];
					String columnValue = subString[1];
					Boolean uuidStringColumnValue = Utils.checkIfUUID(columnValue);
					Object value = uuidStringColumnValue ? UUID.fromString(subString[1]) : subString[1];
					Class<?> attributeClassType = r.getModel().getAttribute(columnName).getJavaType();
					if(attributeClassType.getName().startsWith("java.lang")) {
						predicates.add(builder.like(r.get(columnName), "%"+value+"%"));
					} else {
						predicates.add(builder.like(filterColumnExpressionIdentifier(columnName, uuidStringColumnValue, r), "%"+value+"%"));
					}
				} else if (filter.contains(" con ")) {
					String[] subString = filter.split(" con ");
					String columnName = subString[0];
					String columnValue = subString[1];
					Boolean uuidStringColumnValue = Utils.checkIfUUID(columnValue);
					Object value = uuidStringColumnValue ? UUID.fromString(subString[1]) : subString[1];
					Class<?> attributeClassType = r.getModel().getAttribute(columnName).getJavaType();
					if(attributeClassType.getName().startsWith("java.lang")) {
						predicates.add(builder.like(r.get(columnName), "%"+value+"%"));
					} else {
						predicates.add(builder.like(filterColumnExpressionIdentifier(columnName, uuidStringColumnValue, r), "%"+value+"%"));
					}
				}
			}
		}
		return predicates;
	}
	
	private static Expression<String> filterColumnExpressionIdentifier(String columnName, Boolean uuidValueSpecified, Root<?> r) {
		Expression<String> fieldExpression = null;
		switch (columnName) {
		case CommonConstants.FINANCIAL_YEAR:
			fieldExpression = expressionBuilder(CommonConstants.FINANCIAL_YEAR, uuidValueSpecified, r);
			break;
		case CommonConstants.CANDIDATE_SOURCE:
			fieldExpression = expressionBuilder(CommonConstants.CANDIDATE_SOURCE, uuidValueSpecified, r);
			break;
		case CommonConstants.COMPETENCY:
			fieldExpression = expressionBuilder(CommonConstants.COMPETENCY, uuidValueSpecified, r);
			break;
		case CommonConstants.SUB_COMPETENCY:
			fieldExpression = expressionBuilder(CommonConstants.SUB_COMPETENCY, uuidValueSpecified, r);
			break;
		case CommonConstants.MARKET_OFFERING:
			fieldExpression = expressionBuilder(CommonConstants.MARKET_OFFERING, uuidValueSpecified, r);
			break;
		case CommonConstants.SUB_BUSINESS_LINE:
			fieldExpression = expressionBuilder(CommonConstants.SUB_BUSINESS_LINE, uuidValueSpecified, r);
			break;
		default:
			fieldExpression = r.get(columnName);
			break;
		}
		return fieldExpression;
	}
	
	private static Expression<String> expressionBuilder(String field, Boolean uuidValueSpecified, Root<?> r) {
		if(uuidValueSpecified) {
			return r.get(field).get(CommonConstants.ID);
		} else {
			return r.get(field).get(field);
		}
	}
	
}
