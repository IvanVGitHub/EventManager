package ivanK;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * CardLayoutTest
 *
 * @author Eugene Matyushkin
 */
public class TESTAppWithTrayTest extends JFrame {

    private static final Color[] COLORS = new Color[]{Color.red, Color.green, Color.blue, Color.black, Color.orange,
            Color.cyan, Color.darkGray, Color.magenta, Color.lightGray,
            Color.pink, Color.white, Color.yellow};

    private CardLayout layout;

    public TESTAppWithTrayTest(){
        super("CardLayout");
        layout = new CardLayout();
        final JPanel content = new JPanel(layout);
        String[] names = new String[COLORS.length];
        for(int i=0; i<COLORS.length; i++){
            String name = "Component"+i;
            content.add(name, createComponent(i));
            names[i] = name;
        }
        JPanel btnPanel = new JPanel(new GridLayout(1,4));
        JButton btnFirst = new JButton("<<");
        JButton btnPrevious = new JButton("<");
        JButton btnNext = new JButton(">");
        JButton btnLast = new JButton(">>");
        btnPanel.add(btnFirst);
        btnPanel.add(btnPrevious);
        btnPanel.add(btnNext);
        btnPanel.add(btnLast);
        btnFirst.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                layout.first(content);
            }
        });
        btnPrevious.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                layout.previous(content);
            }
        });
        btnNext.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                layout.next(content);
            }
        });
        btnLast.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                layout.last(content);
            }
        });
        final JComboBox cbxNames = new JComboBox(names);
        cbxNames.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    layout.show(content, (String)cbxNames.getSelectedItem());
            }
        });
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(cbxNames, BorderLayout.SOUTH);
        getContentPane().add(btnPanel, BorderLayout.NORTH);
        setSize(410, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JComponent createComponent(int number){
        JLabel lbl = new JLabel("Label "+number);
        lbl.setPreferredSize(new Dimension(100, 50));
//        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setBorder(BorderFactory.createLineBorder(COLORS[number], 3));
//        lbl.setForeground(COLORS[number]);
        return lbl;
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new TESTAppWithTrayTest().setVisible(true);
    }
}