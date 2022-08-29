package ivanK;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * GridLayoutTest
 *
 * @author Eugene Matyushkin
 */
public class GridLayoutTest extends JFrame{

    public GridLayoutTest(){
        super("GridLayout");
        final JPanel content = new JPanel(new GridLayout());
        for(int i=0; i<8; i++){
            content.add(createComponent(i));
        }
        final JCheckBox chkOrientation = new JCheckBox("Right-to-left orientation");
        chkOrientation.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                content.setComponentOrientation( chkOrientation.isSelected() ?
                        ComponentOrientation.RIGHT_TO_LEFT :
                        ComponentOrientation.LEFT_TO_RIGHT);
                content.doLayout();
            }
        });
        content.setBorder(BorderFactory.createLineBorder(Color.red));
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(chkOrientation, BorderLayout.SOUTH);
        setSize(410, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    private JComponent createComponent(int number){
        JLabel lbl = new JLabel("Label "+number);
        lbl.setPreferredSize(new Dimension(100, 50));
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
        return lbl;
    }

    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new GridLayoutTest().setVisible(true);
    }
}