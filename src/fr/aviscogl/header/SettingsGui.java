package fr.aviscogl.header;

import javax.swing.*;
import java.util.HashMap;

public class SettingsGui {
    public JPanel panel;
    public JTextField email;
    public JTextField username;

    public long lastModification = System.currentTimeMillis();
    public long applySetted = System.currentTimeMillis();
}
