package org.rapid7.trools;

import java.io.IOException;
import java.io.Reader;

/**
 * Provides access to a rule resource via a given URI.
 *
 * @author Daniel Akiva
 * @author Derek Abdine
 */
public interface RuleResourceProvider {
    /**
     * Gets a reader for a given URI.
     *
     * @param resourceURI
     *            The URI. Must not be {@code null}.
     *
     * @return A reader. The caller is responsible for closing the reader
     *         returned by this method.
     *
     * @throws IOException
     *             If the resource could not be loaded.
     */
    public Reader getReader(String resourceURI) throws IOException;
}
