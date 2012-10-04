package org.rapid7.trools.jess;

import org.junit.runners.model.InitializationError;
import org.rapid7.trools.RulesJUnit4ClassRunner;

/**
 * Registers and provides information about the Jess JSR-94 implementation to the JUnit framework.
 *
 * @author Derek Abdine
 */
public class JessRulesJUnit4ClassRunner extends RulesJUnit4ClassRunner
{
   public JessRulesJUnit4ClassRunner(Class<?> klass)
      throws InitializationError
   {
      super(klass);
   }

   @Override
   public void registerProvider()
      throws ClassNotFoundException
   {
      Class.forName(getProviderName() + ".RuleServiceProviderImpl");
   }

   @Override
   public String getProviderName()
   {
      return "jess.jsr94";
   }
}
