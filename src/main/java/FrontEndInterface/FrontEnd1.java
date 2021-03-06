package FrontEndInterface;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FrontEnd1 {

    private static Frame frame;
    private static TextField usrnme;
    private static TextField pswd;
    private static Label entrusrnme;
    private static Label entrpsswd;
    private static Button submit;
    private static GridLayout layout;

    static {
        frame = new Frame("Work time tracker");
        usrnme = new TextField();
        pswd = new TextField();
        entrusrnme = new Label("Please enter your username: ");
        entrpsswd = new Label("Please enter your password: ");
        submit = new Button("Submit");
        layout = new GridLayout(3,2);
        frame.setLayout(layout);
        frame.add(entrusrnme);
        frame.add(usrnme);
        frame.add(entrpsswd);
        frame.add(pswd);
        frame.add(new Label());
        frame.add(submit);
        frame.setSize(500,500);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public static void start(){
        frame.setVisible(true);
    }

}