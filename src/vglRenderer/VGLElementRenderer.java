/* Generated by Together */

package vglRenderer;

import vglDocument.VGLElementVisitor;
import vglDocument.VGLPolygon;
import vglDocument.VGLPoints;
import vglDocument.VGLStyle;
import vglDocument.VGLCircle;
import vglDocument.VGLPoint;
import vglDocument.VGLFont;
import vglDocument.VGLText;
import vglDocument.VGLPath;

public class VGLElementRenderer implements VGLElementVisitor {
    public VGLElementRenderer(RenderContext renderContext, VGLRenderingStrategy strategy) {
        this.renderContext = renderContext;
        this.strategy = strategy;
    }

    public void setRenderStrategy(VGLRenderingStrategy renderStrategy) {
        this.strategy = renderStrategy;
    }

    public void visitPolygon(VGLPolygon polygon) {
        /** Map the polygon points from viewport-space to window-space */
        VGLPoints points = polygon.getPoints();
        long[] sx = points.getSX();
        long[] sy = points.getSY();

            /* Divide vertices
            */

       // VerticesSubDivider divider = VerticesSubDivider.getInstance();
      //  divider.subDivide(sx, sy, sx.length, 0.1);
     //   sx = divider.getSX();
     //   sy = divider.getSY();
     //   int nVerts = divider.getNumVerts();
		int nVerts = sx.length;
			/* Render vertices
			*/

        ensureTempPointCapacity(nVerts);
        for (int i = 0; i < nVerts; i++) {
            ix[i] = renderContext.mapXCoordVToW(sx[i]);
            iy[i] = renderContext.mapYCoordVToW(sy[i]);
          //  strategy.drawOval(ix[i], iy[i], 5, 5);
        }

        /** Render the window-space polygon in it's associated style */
        VGLStyle style = polygon.getProperties().getStyle();
        strategy.setColor(style.getFillColor());
        strategy.fillPolygon(ix, iy, nVerts);
        strategy.setColor(style.getStrokeColor());
        strategy.drawPolygon(ix, iy, nVerts);
    }

    /** Render VGL path element */
    public void visitPath(VGLPath path) {
        /** Map the path points from viewport-space to window-space */
        VGLPoints points = path.getPoints();
        long[] sx = points.getSX();
        long[] sy = points.getSY();
        ensureTempPointCapacity(sx.length);
        for (int i = 0; i < sx.length; i++) {
            ix[i] = renderContext.mapXCoordVToW(sx[i]);
            iy[i] = renderContext.mapYCoordVToW(sy[i]);
        }

        /** Render the window-space path in it's associated style */
        VGLStyle style = path.getProperties().getStyle();
        strategy.setColor(style.getStrokeColor());
        strategy.drawPath(ix, iy, sx.length);
    }

    /** Render VGL circle element */
    public void visitCircle(VGLCircle circle) {
        /** Map the circle centre and radius from viewport-space to window-space */
        VGLPoint centre = circle.getCenter();
        long xMap = renderContext.getXMap();
        long yMap = renderContext.getYMap();
        long radius = circle.getRadius() * xMap;
        int width = (int)((radius * xMap) / Constants.SHIFT);
        int height = (int)((radius * yMap) / Constants.SHIFT);
        int x = renderContext.mapXCoordVToW(centre.x);
        int y = renderContext.mapYCoordVToW(centre.y);

        /** Render the window-space circle in it's associated style */
        VGLStyle style = circle.getProperties().getStyle();
        strategy.setColor(style.getFillColor());
        strategy.fillOval(x, y, width, height);
        strategy.setColor(style.getStrokeColor());
        strategy.drawOval(x, y, width, height);
    }

    /** Render VGL text element */
    public void visitText(VGLText text) {
        VGLPoint origin = text.getOrigin();
        int x = renderContext.mapXCoordVToW(origin.x);
        int y = renderContext.mapYCoordVToW(origin.y);
        VGLStyle style = text.getProperties().getStyle();
        strategy.setColor(style.getStrokeColor());
        VGLFont font = style.getFont();
        strategy.setFont(font.getFamily(), font.getStyle(), renderContext.mapXScalarVToW((long)(font.getSize())));
        strategy.drawString(text.getValue(), x, y);
    }

    private void ensureTempPointCapacity(int nRequired) {
        if (nRequired > ix.length) {
            ix = new int[nRequired];
            iy = new int[nRequired];
        }
    }

    /**
     * @supplierCardinality 1
     * @clientCardinality 1 
     */
    private VGLRenderingStrategy strategy;

    /**
     * Storage for window-space vertices which have been mapped from viewport-space, as a property so as to avoid any overhead
     * with allocation and garbage collection every time we render a polygon or path. These will expand if they are
     * ever needed to be larger.
     */
    private int[] ix = new int[200];
    private int[] iy = new int[200];

    /**
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private RenderContext renderContext;
}
