/* Generated by Together */

package vglDocument;

import java.util.Iterator;
import boundaryMap.BoundaryMap;
import boundaryMap.Boundary;

/** A VGL document */
public class VGLDocument {
    /** Holds elements in a bounding volume hierarchy for fast lookup */
    private BoundaryMap boundaryMap;



   

    /** Create new empty document. */
    public VGLDocument() {
        boundaryMap = new BoundaryMap();
    }

    /** Clear elements from document */
    public void clear() {
        boundaryMap = new BoundaryMap();
    }

    /** Add element to document */
    public void addElement(VGLElement element) {
        boundaryMap.putElement(element);
    }

    /** Visit each element intersecting a given boundary within the document */
    public void visitElements(VGLBoundary v, VGLElementVisitor visitor) {
      //  System.out.println("VGLDocument::visitElements():" + v.xmin + "," + v.ymin + "," + v.xmax + "," + v.ymax);
        Iterator i = boundaryMap.getElements(new Boundary(v.xmin, v.ymin, v.xmax, v.ymax));
        while (i.hasNext()) {
            ((VGLElement)i.next()).acceptVisitor(visitor);
        }
    }

    /** Get boundary which encloses all elements in document */
    public VGLBoundary getBoundary() {
        Boundary b = boundaryMap.getBoundary();
        return new VGLBoundary(b.xmin, b.ymin, b.xmax, b.ymax);
    }
}
