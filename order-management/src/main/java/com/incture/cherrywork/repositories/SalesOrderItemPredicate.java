package com.incture.cherrywork.repositories;
import com.incture.cherrywork.entitiesSalesOrderItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class SalesOrderItemPredicate{

private SearchCriteria criteria;

public SalesOrderItemPredicate(final SearchCriteria criteria) {
this.criteria = criteria;
}

public BooleanExpression getPredicate() {
final PathBuilder<SalesOrderItem> entityPath = new PathBuilder<>(SalesOrderItem.class, "salesOrderItem");
if (isNumeric(criteria.getValue().toString())) {
final NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
final int value = Integer.parseInt(criteria.getValue().toString());
switch (criteria.getOperation()) {
	case ":":
		return path.eq(value);
	case ">":
		return path.goe(value);
	case "<":
		return path.loe(value);
}
} else {
	final StringPath path = entityPath.getString(criteria.getKey());
	if (criteria.getOperation().equalsIgnoreCase(":")) {
		return path.containsIgnoreCase(criteria.getValue().toString());
	}
}
return null;
}
public SearchCriteria getCriteria() {
	return criteria;
}

public void setCriteria(final SearchCriteria criteria) {
	this.criteria = criteria;
}

public static boolean isNumeric(final String str) {
	try {
Integer.parseInt(str);
	} catch (final NumberFormatException e) {
	return false;
}
return true;
}
}