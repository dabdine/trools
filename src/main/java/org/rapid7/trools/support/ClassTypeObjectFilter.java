package org.rapid7.trools.support;

import javax.rules.ObjectFilter;

/**
 * Support for filtering objects in a rules engine that match a specific type of
 * class.
 *
 * @author Derek Abdine
 */
public class ClassTypeObjectFilter implements ObjectFilter {
    /**
     * Creates a new object filter which filters by Class#equals.
     *
     * @param klass
     *            The class. Must not be {@code null}.
     */
    public ClassTypeObjectFilter(Class klass) {
	if (klass == null) {
	    throw new IllegalArgumentException("The class must not be null");
	}
	m_class = klass;
    }

    @Override
    public Object filter(Object fact) {
	return fact.getClass().equals(m_class) ? fact : null;
    }

    @Override
    public void reset() {
	// no-op
    }

    private Class m_class;
}
