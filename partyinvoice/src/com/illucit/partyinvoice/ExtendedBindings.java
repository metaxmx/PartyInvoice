package com.illucit.partyinvoice;

import java.util.function.BiFunction;
import java.util.function.Function;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExtendedBindings {

	public static BooleanBinding conditionBinding(final ObservableStringValue op,
			final Function<String, Boolean> condition) {
		if (op == null) {
			throw new NullPointerException("Operand cannot be null");
		}
		if (condition == null) {
			throw new NullPointerException("Operand cannot be null");
		}

		return new BooleanBinding() {
			{
				super.bind(op);
			}

			@Override
			public void dispose() {
				super.unbind(op);
			}

			@Override
			protected boolean computeValue() {
				return condition.apply(getStringSafe(op.get()));
			}

			@Override
			public ObservableList<?> getDependencies() {
				return FXCollections.singletonObservableList(op);
			}
		};
	}

	public static StringBinding mappingBinding(final ObservableStringValue op, final Function<String, String> mapping) {
		if (op == null) {
			throw new NullPointerException("Operand cannot be null");
		}
		if (mapping == null) {
			throw new NullPointerException("Operand cannot be null");
		}

		return new StringBinding() {
			{
				super.bind(op);
			}

			@Override
			public void dispose() {
				super.unbind(op);
			}

			@Override
			protected String computeValue() {
				return mapping.apply(getStringSafe(op.get()));
			}

			@Override
			public ObservableList<?> getDependencies() {
				return FXCollections.singletonObservableList(op);
			}
		};
	}

	public static StringBinding resolvingBinding(final ObservableStringValue op1, final ObservableStringValue op2,
			final BiFunction<String, String, String> resolving) {
		if (op1 == null) {
			throw new NullPointerException("Operand cannot be null");
		}
		if (op2 == null) {
			throw new NullPointerException("Operand cannot be null");
		}
		if (resolving == null) {
			throw new NullPointerException("Operand cannot be null");
		}

		return new StringBinding() {
			{
				super.bind(op1, op2);
			}

			@Override
			public void dispose() {
				super.unbind(op1, op2);
			}

			@Override
			protected String computeValue() {
				return resolving.apply(getStringSafe(op1.get()), getStringSafe(op2.get()));
			}

			@Override
			public ObservableList<?> getDependencies() {
				return FXCollections.observableArrayList(op1, op2);
			}
		};
	}

	private static String getStringSafe(String value) {
		return value == null ? "" : value;
	}

}
