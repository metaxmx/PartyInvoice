package com.illucit.partyinvoice.data;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Commentary Annotation for an XML property, that it is transient and won't be
 * exported to the XML file.
 * 
 * @author Christian Simon
 *
 */
@Documented
@Retention(SOURCE)
@Target(value = { FIELD, METHOD })
public @interface TransientValue {

}
