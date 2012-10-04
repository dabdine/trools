package org.rapid7.trools.jess.example.fact;

/**
 * A simple person fact used to demonstrate trools.
 *
 * @author Derek Abdine
 */
public class Person {
    /**
     * Sets the fact X value.
     *
     * @param data
     *            The name, can be {@code null}.
     *
     * @return The current fact object. Never {@code null}.
     */
    public Person setName(String name) {
	m_name = name;
	return this;
    }

    /**
     * Gets the name.
     *
     * @return The name. Can be {@code null}.
     */
    public String getName() {
	return m_name;
    }

    private String m_name;
}
