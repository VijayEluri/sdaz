/* Generated by Together */

package com.neocoders.vglviewer.sdaz;

abstract class SDAZState {
    public abstract SDAZState mouseDown(SDAZPoint p);

    public abstract SDAZState mouseDragged(SDAZPoint p);

    public abstract SDAZState mouseReleased(SDAZPoint p);

    public abstract SDAZState update();
}