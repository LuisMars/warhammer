package es.luismars;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Luis on 08/09/2014.
 */
public class Test extends JFrame {
    private JPanel TestPanel;
    public JProgressBar progressBar1;

    public Test() throws HeadlessException {
        super("Test");
        setContentPane(TestPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        progressBar1.setMaximum(222692);
    }
}
