/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatefulRuleSession;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.rapid7.trools.annotation.InjectRuleSession;
import org.rapid7.trools.annotation.RuleContext;

/**
 * Configures a test class with rules entities, based on trools annotations.
 *
 * @author Daniel Akiva
 * @author Derek Abdine
 */
public abstract class RulesJUnit4ClassRunner extends BlockJUnit4ClassRunner
{
   public RulesJUnit4ClassRunner(Class<?> klass)
      throws InitializationError
   {
      super(klass);
   }

   /**
    * Registers the JSR-94 provider.
    * @throws ClassNotFoundException
    */
   public abstract void registerProvider()
      throws ClassNotFoundException;

   /**
    * Gets the JSR-94 provider name.
    *
    * @return The non-{@code null} provider name.
    */
   public abstract String getProviderName();

   @Override
   protected Object createTest()
      throws Exception
   {
      Object testObject = super.createTest();
      Class<? extends Object> klass = testObject.getClass();

      // First process top-level RuleExcutionSet annotations on the class.
      RuleContext ruleContextAnnotation = klass.getAnnotation(RuleContext.class);
      if (ruleContextAnnotation == null)
         throw new IllegalArgumentException("Test class must have @" + RuleContext.class.getName() + " annotation");

      registerProvider();
      RuleServiceProvider serviceProvider = RuleServiceProviderManager.getRuleServiceProvider(getProviderName());
      RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();

      // First try to preload all the vendor properties as specified in the @RuleContext annotation.
      Map vendorProps = new HashMap();
      if (!"".equals(ruleContextAnnotation.vendorPropsURI())) {
         try (Reader propReader = new FileReader(ruleContextAnnotation.vendorPropsURI());) {
            Properties properties = new Properties();
            properties.load(propReader);
            for (String keyName : properties.stringPropertyNames()) {
               vendorProps.put(keyName, properties.get(keyName));
            }
         } catch (Exception e) {
            throw new IllegalArgumentException("Could not load vendor properties file", e);
         }
      }

      // parse the rule set
      try
      {
         // Create the rule execution set
         IRuleProvider resourceProvider = ruleContextAnnotation.resourceProvider().newInstance();
         try (Reader resourceReader = resourceProvider.getReader(ruleContextAnnotation.resourceURI());) {
            RuleExecutionSet executionSet = ruleAdministrator.getLocalRuleExecutionSetProvider(vendorProps)
               .createRuleExecutionSet(resourceReader, vendorProps);

            // Register the rule execution set with the rule administrator
            ruleAdministrator.registerRuleExecutionSet(ruleContextAnnotation.resourceURI(), executionSet, vendorProps);
         } catch (Exception e) {
            throw new IllegalArgumentException("Could not load rules resource", e);
         }
      } catch (Exception e) {
         throw new IllegalArgumentException("Failed to initialize rules engine", e);
      }


      // Now find the method @InjectRuleSession annotation and inject the rule session.
      boolean processedInectField = false;
      for (Field field : klass.getDeclaredFields()) {
         if (field.getAnnotation(InjectRuleSession.class) != null) {
            // Check to see if we are encountering a duplicate field...
            if (processedInectField) {
               throw new IllegalArgumentException("Only one @" + InjectRuleSession.class.getName() + " annotation can exist");
            }

            // Set the flag just in case we trip over another field that is annotated.
            processedInectField = true;

            // Inject into the field. We will inspect the annotated field and inject a stateful/stateless rules session
            // object depending on what the user is asking for.
            Integer type = null;
            if (StatefulRuleSession.class.isAssignableFrom(field.getType())) {
               type = RuleRuntime.STATEFUL_SESSION_TYPE;
            } else if (StatelessRuleSession.class.isAssignableFrom(field.getType())) {
               type = RuleRuntime.STATELESS_SESSION_TYPE;
            }
            if (type == null)
               throw new IllegalArgumentException("Encountered @" + InjectRuleSession.class.getName() + " on field " + field.getName() + " of type " + field.getType() + " which needs to be of either type javax.rules.StatefulRuleSession or javax.rules.StatelessRuleSession");
            field.setAccessible(true);
            field.set(testObject, serviceProvider.getRuleRuntime().createRuleSession(ruleContextAnnotation.resourceURI(), vendorProps, type));
         }
      }

      return testObject;
   }
}
