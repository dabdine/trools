package org.rapid7.trools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Provides a reader for a rules resource from the Java classpath.
 *
 * @author Daniel Akiva
 */
public class ClasspathRuleResourceProvider implements RuleResourceProvider {
    @Override
    public Reader getReader(String resourceURI) throws IOException {
	InputStream inStream = getClass().getClassLoader().getResourceAsStream(resourceURI);
	if (inStream == null)
	    throw new IOException("The resource \"" + resourceURI + "\" could not be loaded.");
	return new InputStreamReader(inStream);
    }
}
