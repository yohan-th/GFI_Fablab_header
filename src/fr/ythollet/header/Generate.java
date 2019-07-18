package fr.ythollet.header;

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
        String user = System.getenv("USER");
        String dte = dateFormat.format(new Date());

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
/*


# *************************************************************************** #
#   ___  ____  __    ____  __   ____  __     __   ____    tf_training.py      #
#  / __)(  __)(  )  (  __)/ _\ (  _ \(  )   / _\ (  _ \                       #
# ( (_ \ ) _)  )(    ) _)/    \ ) _ (/ (_/\/    \ ) _ (   Created by yohan    #
#  \___/(__)  (__)  (__) \_/\_/(____/\____/\_/\_/(____/   19/06/2019 11:34:42 #
#                                                                             #
#          Contact: yohan.thollet@gfi.world               Updated by yohan    #
#                                                         21/06/2019 15:39:42 #
# *************************************************************************** #

# **************************************************************************** #
#                                                           LE - /             #
#                                                               /              #
#    Makefile                                         .::    .:/ .      .::    #
#                                                  +:+:+   +:    +:  +:+:+     #
#    By: ythollet <ythollet@student.le-101.fr>      +:+   +:    +:    +:+      #
#                                                  #+#   #+    #+    #+#       #
#    Created: 2018/03/14 01:36:40 by ythollet     #+#   ##    ##    #+#        #
#    Updated: 2018/06/18 21:15:34 by ythollet    ###    #+. /#+    ###.fr      #
#                                                          /                   #
#                                                         /                    #
# **************************************************************************** #

*/
        String mail = "yohan.thollet@gfi.world";
        String st = sc;
        String et = ec + "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("*************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");
        sb.append(st).append(String.format("   ___  ____  __    ____  __   ____  __     __   ____    %-19s " + et, filename));
        sb.append(st).append("  / __)(  __)(  )  (  __)/ _\\ (  _ \\(  )   / _\\ (  _ \\                       ").append(et);
        sb.append(st).append(String.format(" ( (_ \\ ) _)  )(    ) _)/    \\ ) _ (/ (_/\\/    \\ ) _ (   Created by %-8s " + et, user));
        sb.append(st).append(String.format("  \\___/(__)  (__)  (__) \\_/\\_/(____/\\____/\\_/\\_/(____/   %s " + et, dte));
        sb.append(st).append("                                                                             ").append(et);
        sb.append(st).append(String.format("          Contact: %-38sUpdated by %-8s " + et, mail, user));
        sb.append(st).append(String.format("                                                         %s " + et, dte));
        sb.append(sc).append(" ").append(sc.length() == 1 ? "*" : "").append("*************************************************************************").append(ec.length() == 1 ? "*" : "").append(" ").append(ec).append("\n");

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().insertString(0, sb.toString());
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);
    }

}