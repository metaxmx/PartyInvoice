package com.illucit.partyinvoice.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class for combined JavaFX property bindings.
 * 
 * @author Christian Simon
 *
 */
public class ExtendedBindings {

	/**
	 * Create an unidirectional boolean binding that resolves a given
	 * {@link ObservableStringValue} by a given predicate. Whenever the String
	 * value changes, the predicate is reevaluated and all observers of the
	 * returned binding are notified of the predicate change.
	 * 
	 * @param op
	 *            observable String
	 * @param predicate
	 *            String predicate
	 * @return boolean binding that reflects the predicate on the String
	 */
	public static BooleanBinding conditionBinding(final ObservableStringValue op, final Predicate<String> predicate) {
		if (op == null) {
			throw new NullPointerException("Operand cannot be null");
		}
		if (predicate == null) {
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
				return predicate.test(getStringSafe(op.get()));
			}

			@Override
			public ObservableList<?> getDependencies() {
				return FXCollections.singletonObservableList(op);
			}
		};
	}

	/**
	 * Create an unidirectional String binding that resolves a given
	 * {@link ObservableStringValue} by a given String-to-String mapping.
	 * Whenever the String value changes, the mapping is computed again and all
	 * observers of the returned binding are notified of the change.
	 * 
	 * @param op
	 *            observable String
	 * @param mapping
	 *            String-to-String mapping
	 * @return String binding that reflects the mapping on the String
	 */
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

	/**
	 * Create an unidirectional String binding that resolves two given
	 * {@link ObservableStringValue}s by a {@link BiFunction} that creates a
	 * combined String value from both provied inputs. Whenever one of the
	 * String values change, the resolving function is computed again and all
	 * observers of the returned binding are notified of the change.
	 * 
	 * @param op1
	 *            first observable String (will be put as first parameter of
	 *            resolving function)
	 * @param op2
	 *            second observable String (will be put as second parameter of
	 *            resolving function)
	 * @param resolving
	 *            resolving Bifunction
	 * @return String binding that reflects the combination of the Strings
	 */
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

	/**
	 * Get String value. If input is null, return empty String.
	 * 
	 * @param value
	 *            input value
	 * @return same as input value, or "" if input was <code>null</code>
	 */
	private static String getStringSafe(String value) {
		return value == null ? "" : value;
	}

}
