/*******************************************************************************
 * Copyright (c) 2011 Martin Hentschel.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Hentschel - initial API and implementation
 *******************************************************************************/

package de.hentschel.visualdbc.dbcmodel.diagram.custom.util;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Sets the default custom layout preferences.
 * @author Martin Hentschel
 * @see CustomPreferences
 */
public class CustomPreferencesInitializer extends AbstractPreferenceInitializer {
   /**
    * {@inheritDoc}
    */
   @Override
   public void initializeDefaultPreferences() {
      // Layout preferences
      CustomPreferences.setDefaultUseCustomLayout(true);
      CustomPreferences.setDefaultVerticalSpacing(20);
   }
}