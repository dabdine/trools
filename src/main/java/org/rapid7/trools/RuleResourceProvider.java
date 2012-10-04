/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools;

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
     */
    public Reader getReader(String resourceURI);
}
