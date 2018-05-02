package View;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

public class GreyLayerUI extends LayerUI<JComponent> {
    private int x, y, width, height, radius;
    private GreyLayer layer;

    public GreyLayerUI(int x, int y, int width, int height, int radius, GreyLayer layer){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.layer=layer;
        this.radius=radius;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layer.getAlpha()));
        g2.setPaint(new RadialGradientPaint(x, y, radius, new float[]{0.2f, 0.3f, 0.4f}, new Color[]{Color.lightGray, Color.gray, Color.darkGray}));
        g2.fillRect(0, 0, width, height);
        g2.dispose();
    }
}
