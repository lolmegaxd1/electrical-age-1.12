package org.eln2.sim.mna.component

import org.eln2.sim.mna.state.State

/*
NOTE: DO NOT IMPORT MINECRAFT CODE IN THIS CLASS
EXTEND IT INSTEAD IN THE org.eln.nbt DIRECTORY
 */

open class InterSystem: Resistor {

    constructor()
    constructor(aPin: State?, bPin: State?): super(aPin, bPin)

    override fun canBeReplacedByInterSystem(): Boolean {
        return true
    }
}