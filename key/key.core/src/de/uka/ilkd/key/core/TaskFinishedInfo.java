// This file is part of KeY - Integrated Deductive Software Design
//
// Copyright (C) 2001-2011 Universitaet Karlsruhe (TH), Germany
//                         Universitaet Koblenz-Landau, Germany
//                         Chalmers University of Technology, Sweden
// Copyright (C) 2011-2014 Karlsruhe Institute of Technology, Germany
//                         Technical University Darmstadt, Germany
//                         Chalmers University of Technology, Sweden
//
// The KeY system is protected by the GNU General
// Public License. See LICENSE.TXT for details.
//

package de.uka.ilkd.key.core;

import de.uka.ilkd.key.proof.Proof;

/**
 * An information object with additional information about the 
 * finished task.
 */
public interface TaskFinishedInfo {
    Object getSource();

    Object getResult();

    long getTime();
    
    int getAppliedRules();

    int getClosedGoals();

    Proof getProof();
    
}