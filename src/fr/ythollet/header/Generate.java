package fr.ythollet.header;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.command.WriteCommandAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class Generate extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent AnActionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        VirtualFile file = AnActionEvent.getData(VIRTUAL_FILE);
        assert file != null;
        String filename = file.getName();
        String dte = dateFormat.format(new Date());
        String mail = System.getenv("MAIL");
        String user = null;
        if (mail == null)
        {
            user = "MAIL = Ø";
            mail = "Import_MAIL_in_ENV_variable_&_restart";
        }
        else
        {
            Pattern p = Pattern.compile("([a-zA-Z0-9\\-]+)");
            Matcher m = p.matcher(mail);
            boolean find = m.find();
            if (find && m.group(0).length() > 8)
                user = m.group(0).substring(0, 8);
            else if (find)
                user = m.group(0);
            else
                user = "error";
        }

        String sc;
        String ec;
        String extension = file.getExtension();
        if (extension != null && (extension.equals("c") || extension.equals("h")))
        {
            sc = "/*";
            ec = "*/";
        }
        else if ((extension != null && extension.equals("py")) || filename.contains("Makefile"))
        {
            sc = "#";
            ec = "#";
        }
        else
        {
            sc = "//";
            ec = "// ";
        }

        String st = sc;
        String et = ec + "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("*************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");
        sb.append(st).append(String.format("   ___  ____  __    ____  __   ____  __     __   ____    %-19s " + et, filename));
        sb.append(st).append("  / __)(  __)(  )  (  __)/ _\\ (  _ \\(  )   / _\\ (  _ \\                       ").append(et);
        sb.append(st).append(String.format(" ( (_ \\ ) _)  )(    ) _)/    \\ ) _ (/ (_/\\/    \\ ) _ (   Created by %-8s " + et, user));
        sb.append(st).append(String.format("  \\___/(__)  (__)  (__) \\_/\\_/(____/\\____/\\_/\\_/(____/   %s " + et, dte));
        sb.append(st).append("                                                                             ").append(et);
        sb.append(st).append(String.format("         Contact: %-39sUpdated by %-8s " + et, mail, user));
        sb.append(st).append(String.format("                                                         %s " + et, dte));
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("*************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().insertString(0, sb.toString());
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);
    }

}