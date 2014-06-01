package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public class RecordCellRenderer implements ListCellRenderer<Record> {

    private static Logger logger = LoggerFactory.getLogger(RecordCellRenderer.class);
    private ImageIcon callIcon;
    private ImageIcon putIcon;

    public RecordCellRenderer() {
        try {
            BufferedImage callImage = ImageIO.read(this.getClass().getResource("call.png"));
            callIcon = new ImageIcon(callImage);
            BufferedImage putImage = ImageIO.read(this.getClass().getResource("put.png"));
            putIcon = new ImageIcon(putImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Record> list, Record value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        if (isSelected) {
            //panel.setBackground(Color.BLUE);
        }
        if (value != null) {
            ImageIcon icon = (value.isPut()) ? putIcon : callIcon;
            panel.add(new JLabel(icon), BorderLayout.WEST);
            panel.add(new JLabel(value.getSum().toString() + " rub"), BorderLayout.CENTER);
            panel.add(new JLabel(value.getCategory().getName()), BorderLayout.SOUTH);
        }

        return panel;
    }
}
