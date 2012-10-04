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
