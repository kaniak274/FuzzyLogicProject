package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import db.Repository;
import gui.views.MainWindow;
import gui.views.UserWindow;

public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                UserWindow window = new UserWindow(repo);
                window.createWindow();
            }
        });
    }
}
