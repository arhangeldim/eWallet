package arhangel.dim.ewallet.gui;

import arhangel.dim.ewallet.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
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
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        if (isSelected) {
            //panel.setBackground(Color.BLUE);
        }

        Font tinyFont = new Font("Arial", Font.PLAIN, 12);
        if (value != null) {
            ImageIcon icon = (value.isPut()) ? putIcon : callIcon;
            JLabel descrLabel = new JLabel(value.getDescription());
            descrLabel.setFont(tinyFont);
            panel.add(new JLabel(icon), BorderLayout.WEST);
            panel.add(new JLabel(value.getSum().toString() + " rub, " + value.getCategory().getName()), BorderLayout.CENTER);
            panel.add(descrLabel, BorderLayout.SOUTH);
        }

        return panel;
    }
}
