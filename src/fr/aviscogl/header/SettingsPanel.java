package fr.aviscogl.header;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsPanel implements SearchableConfigurable {

    SettingsGui panel;

    @NotNull
    @Override
    public String getId() {
        return "preference.Header101";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "preference.Header101";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Header 101 plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        panel = new SettingsGui();
        setEventFor(panel.email, panel.username);
        panel.username.setText(System.getenv("USER"));
        panel.email.setText(System.getenv("USER") + "@student.le-101.fr");
        panel.panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                panel.lastModification = System.currentTimeMillis();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                panel.lastModification = System.currentTimeMillis();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        return panel.panel;
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }

    @Override
    public boolean isModified() {
        return panel.lastModification > panel.applySetted;
    }

    @Override
    public void apply() throws ConfigurationException {
        panel.applySetted = System.currentTimeMillis();
    }

    private void setEventFor(JTextField... components)
    {
        for (JTextField component : components) {
            ActionListener actionListener = e -> {
                System.out.println("Modification: " + panel.lastModification);
                System.out.println("Apply: " + panel.applySetted);
                panel.lastModification = System.currentTimeMillis();
            };
            component.addActionListener(actionListener);
        }
    }
}
