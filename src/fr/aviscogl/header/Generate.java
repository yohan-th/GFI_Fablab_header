package fr.aviscogl.header;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.command.WriteCommandAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class Generate extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent AnActionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        VirtualFile file = AnActionEvent.getData(VIRTUAL_FILE);
        assert file != null;
        String filename = file.getName();
        String user = System.getenv("USER").equals("alexis") ? "aviscogl" : System.getenv("USER");
        String dte = dateFormat.format(new Date());

        String sc = filename.contains("Makefile") ? "# " : "/*";
        String ec = filename.contains("Makefile") ? "# " :  "*/";

        String st = sc + (sc.length() == 1 ? " " : "");
        String et = " " + (ec.length() == 1 ? " " : "") + ec + "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("**************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");
        sb.append(st).append("                                                          LE - /           ").append(et);
        sb.append(st).append("                                                              /            ").append(et);
        sb.append(st).append(String.format("   %-42s       .::    .:/ .      .::  " + et, filename));
        sb.append(st).append("                                                 +:+:+   +:    +:  +:+:+   ").append(et);
        sb.append(st).append(String.format("   %-42s     +:+   +:    +:    +:+    " + et, String.format("By: %s <%s@student.le-101.fr>", user, user)));
        sb.append(st).append("                                                 #+#   #+    #+    #+#     ").append(et);
        sb.append(st).append(String.format("   %-42s   #+#   ##    ##    #+#      " + et, String.format("Created: %s by %s", dte, user)));
        sb.append(st).append(String.format("   %-42s  ###    #+. /#+    ###.fr    " + et, String.format("Updated: %s by %s", dte, user)));
        sb.append(st).append("                                                         /                 ").append(et);
        sb.append(st).append("                                                        /                  ").append(et);
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("**************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().insertString(0, sb.toString());
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);
    }

}