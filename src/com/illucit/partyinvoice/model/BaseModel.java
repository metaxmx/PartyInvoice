package com.illucit.partyinvoice.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import com.illucit.partyinvoice.data.BaseData;

/**
 * Abstract base class for all models that are directly bound to a
 * {@link BaseData} instance.
 * 
 * @author Christian Simon
 *
 * @param <D>
 *            base data type
 */
public abstract class BaseModel<D extends BaseData> {

	private final int id;

	private final ReadOnlyIntegerProperty idProperty;

	/**
	 * Create base model.
	 * 
	 * @param data
	 *            initial value
	 */
	public BaseModel(D data) {
		this.id = data.getId();
		this.idProperty = new ReadOnlyIntegerWrapper(id);
	}

	/**
	 * Update value of model with new value.
	 * 
	 * @param data
	 *            updated data
	 */
	public abstract void update(D data);

	/**
	 * Get the ID of the base data, this model is bound to.
	 * 
	 * @return base data ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get an {@link IntegerProperty} that is bound to the given (fixed) base
	 * data ID.
	 * 
	 * @return property for ID
	 */
	public ReadOnlyIntegerProperty idProperty() {
		return idProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseModel))
			return false;
		BaseModel<?> other = (BaseModel<?>) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
