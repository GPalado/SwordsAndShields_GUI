package View;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class CenterCircularGreyLayer extends GreyLayer {

    public CenterCircularGreyLayer(JPanel jp){
        super(jp, jp.getWidth()/2, jp.getHeight()/2);
    }

    @Override
    void setupLayerUI() {
        LayerUI<JComponent> layerUI = new GreyLayerUI(x, y, width, height, width/2, this);
        jLayer = new JLayer<>(jp, layerUI);
        jLayer.getGlassPane().setVisible(true);
    }
}
