package View;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class PlainGreyLayer extends GreyLayer {

    public PlainGreyLayer(JPanel jp){
        super(jp, jp.getWidth()/2, jp.getHeight()/2);
    }

    @Override
    void setupLayerUI() {
        LayerUI<JComponent> layerUI = new GreyLayerUI(x, y, width, height, 1, this);
        jLayer = new JLayer<>(jp, layerUI);
        jLayer.getGlassPane().setVisible(true);
    }
}
