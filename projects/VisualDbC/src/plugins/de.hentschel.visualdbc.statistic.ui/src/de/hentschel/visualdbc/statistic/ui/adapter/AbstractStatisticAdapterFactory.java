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

package de.hentschel.visualdbc.statistic.ui.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

import de.hentschel.visualdbc.statistic.ui.control.IStatisticProvider;

/**
 * Provides a basic implementation for {@link IAdapterFactory}s that
 * allows to convert to {@link IStatisticProvider}.
 * @author Martin Hentschel
 */
public abstract class AbstractStatisticAdapterFactory implements IAdapterFactory {
   @SuppressWarnings("rawtypes")
   @Override
   public Class[] getAdapterList() {
      return new Class[] {IStatisticProvider.class};
   }
}