/***************************************************************************
 * COPYRIGHT (C) 2012, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package org.rapid7.trools;

/**
 *
 * @author Daniel Akiva
 */
public interface RulesEngineAware
{

   //TODO inject JSR-94 RulesEngine reference here
   public void setRulesEngine();
}
