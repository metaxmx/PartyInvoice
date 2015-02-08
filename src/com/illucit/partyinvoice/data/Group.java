package com.illucit.partyinvoice.data;

import java.util.List;

/**
 * Base data: group of {@link Person} entities.
 * 
 * @author Christian Simon
 *
 */
public interface Group extends BaseData {

	/**
	 * Get the name of the group.
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Get the list of {@link Person} IDs which belong to the group.
	 * 
	 * @return list of IDs
	 */
	public List<Integer> getPersons();

}
