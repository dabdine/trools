/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author Daniel Akiva
 */
public class ClasspathRuleProvider implements IRuleProvider
{
   @Override
   public Reader getReader(String resourceURI)
   {
      return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(resourceURI));
   }
}
