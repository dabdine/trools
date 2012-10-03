/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.rule;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 *
 * @author Daniel Akiva
 */
public abstract class RulesEngineRunner extends BlockJUnit4ClassRunner
{

   public RulesEngineRunner(Class<?> klass)
      throws InitializationError
   {
      super(klass);
   }

   @Override
   public void run(final RunNotifier notifier)
   {

   }
}
