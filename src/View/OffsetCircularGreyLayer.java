package View;

import javax.swing.*;
import javax.swing.plaf.LayerUI;

public class OffsetCircularGreyLayer extends GreyLayer {

    public OffsetCircularGreyLayer(JPanel jp, int x, int y){
        super(jp, x, y);
    }

    @Override
    void setupLayerUI() {
        LayerUI<JComponent> layerUI = new GreyLayerUI(x, y, width, height, width/3, this);
        jLayer = new JLayer<>(jp, layerUI);
        jLayer.getGlassPane().setVisible(true);
    }
}
