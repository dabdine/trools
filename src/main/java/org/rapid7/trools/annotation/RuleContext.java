package org.rapid7.trools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.rapid7.trools.ClasspathRuleResourceProvider;
import org.rapid7.trools.RuleResourceProvider;

/**
 * Defines the rule provider, such as "jess.jsr94" for the Jess JSR-94 compliant
 * rules implementation.
 *
 * @author Derek Abdine
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RuleContext {
    /** The execution set resource provider */
    Class<? extends RuleResourceProvider> resourceProvider() default ClasspathRuleResourceProvider.class;

    /** The rule execution set resource */
    String resourceURI();

    /**
     * A java properties file to pass to the vendor properties parameter when
     * creating new rule execution sets.
     */
    String vendorPropsURI() default "";
}
