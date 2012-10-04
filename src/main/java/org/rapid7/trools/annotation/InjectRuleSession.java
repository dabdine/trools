/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates fields in a trools test class to let the trools framework know
 * which field will receive a RuleSession.
 *
 * @author Daniel Akiva
 * @author Derek Abdine
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRuleSession {

}
