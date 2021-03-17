package com.incture.cherrywork.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

public class OutBoundDeliveryItemPredicateBuilder {

	private final List<SearchCriteria> params;
	
	public OutBoundDeliveryItemPredicateBuilder() {
		params = new ArrayList<>();
		}
		public OutBoundDeliveryItemPredicateBuilder with(final String key, final String operation, final Object value) {
			params.add(new SearchCriteria(key, operation, value));
			return this;
		}
		
		public BooleanExpression build() {
			if (params.size() == 0) {
				return null;
			}
			final List<BooleanExpression> predicates = params.stream().map(param -> {
				OutBoundDeliveryItemPredicate predicate = new OutBoundDeliveryItemPredicate(param);
				return predicate.getPredicate();
			}).filter(Objects::nonNull).collect(Collectors.toList());
			BooleanExpression result = Expressions.asBoolean(true).isTrue();
			for (BooleanExpression predicate : predicates) {
				result = result.and(predicate);
			}
			return result;
		}
		
		static class BooleanExpressionWrapper {
			private BooleanExpression result;
			public BooleanExpressionWrapper(final BooleanExpression result) {
				super();
				this.result = result;
			}
			public BooleanExpression getResult() {
				return result;
			}
			public void setResult(BooleanExpression result) {
				this.result = result;
			}
		}
}
