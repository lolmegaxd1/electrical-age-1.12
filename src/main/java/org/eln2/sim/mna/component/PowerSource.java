package org.eln2.sim.mna.component;

/*
NOTE: DO NOT IMPORT MINECRAFT CODE IN THIS CLASS
EXTEND IT INSTEAD IN THE org.eln.nbt DIRECTORY
 */

import org.eln2.sim.mna.SubSystem;
import org.eln2.sim.mna.misc.IRootSystemPreStepProcess;
import org.eln2.sim.mna.state.State;

public class PowerSource extends VoltageSource implements IRootSystemPreStepProcess {//}, INBTTReady {

    String name;

    double P, Umax, Imax;

    public PowerSource(String name, State aPin) {
        super(name, aPin, null);
        this.name = name;
    }

    public void setP(double P) {
        this.P = P;
    }

    void setMax(double Umax, double Imax) {
        this.Umax = Umax;
        this.Imax = Imax;
    }

    public void setImax(double imax) {
        Imax = imax;
    }

    public void setUmax(double umax) {
        Umax = umax;
    }

    public double getP() {
        return P;
    }

    @Override
    public void quitSubSystem() {
        getSubSystem().getRoot().removeProcess(this);
        super.quitSubSystem();
    }

    @Override
    public void setSubSystem(SubSystem s) {
        super.setSubSystem(s);
        getSubSystem().getRoot().addProcess(this);
        s.addProcess(this);
    }

    @Override
    public void rootSystemPreStepProcess() {
        SubSystem.Th t = getAPin().getSubSystem().getTh(getAPin(), this);

        double U = (Math.sqrt(t.U * t.U + 4 * P * t.R) + t.U) / 2;
        U = Math.min(Math.min(U, Umax), t.U + t.R * Imax);
        if (Double.isNaN(U)) U = 0;
        if (U < t.U) U = t.U;

        setU(U);
    }

    public double getEffectiveP() {
        return getU() * getCurrent();
    }

    /*
    @Override
    public void readFromNBT(NBTTagCompound nbt, String str) {
        super.readFromNBT(nbt, str);

        str += name;

        setP(nbt.getDouble(str + "P"));
        setUmax(nbt.getDouble(str + "Umax"));
        setImax(nbt.getDouble(str + "Imax"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt, String str) {
        super.writeToNBT(nbt, str);

        str += name;

        nbt.setDouble(str + "P", getP());
        nbt.setDouble(str + "Umax", Umax);
        nbt.setDouble(str + "Imax", Imax);
    }

     */
}
