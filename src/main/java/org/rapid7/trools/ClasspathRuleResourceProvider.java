/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Provides a reader for a rules resource from the Java classpath.
 *
 * @author Daniel Akiva
 */
public class ClasspathRuleResourceProvider implements RuleResourceProvider {
    @Override
    public Reader getReader(String resourceURI) {
	return new InputStreamReader(getClass().getClassLoader()
		.getResourceAsStream(resourceURI));
    }
}
