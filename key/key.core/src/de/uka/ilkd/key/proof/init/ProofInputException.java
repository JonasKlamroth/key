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
package de.uka.ilkd.key.proof.init;

/**
 * Reading prover input failed
 */
public class ProofInputException extends Exception {

    public ProofInputException(Exception e) {
        super(e);
    }

    public ProofInputException(String s) {
        super(s);
    }

    public ProofInputException(String message, Throwable cause) {
        super(message, cause);
    }

}