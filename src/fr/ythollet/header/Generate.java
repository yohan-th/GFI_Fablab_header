/* ************************************************************************** /
/   ___  ____  __    ____  __   ____  __     __   ____    Generate.java       /
/  / __)(  __)(  )  (  __)/ _\ (  _ \(  )   / _\ (  _ \                       /
/ ( (_ \ ) _)  )(    ) _)/    \ ) _ (/ (_/\/    \ ) _ (   Created by yohan    /
/  \___/(__)  (__)  (__) \_/\_/(____/\____/\_/\_/(____/   2019/07/23 15:11:02 /
/                                                                             /
/         Contact: yohanthollet@gfi.world                 Updated by yohan    /
/                                                         2019/07/23 16:06:43 /
/ ************************************************************************** */

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
        String dte = dateFormat.format(new Date());
        String mail = System.getenv("MAIL");

        tools obj = new tools();
        String user;
        if (mail == null)
        {
            user = "MAIL=Ø";
            mail = "Import_MAIL_in_ENV_variable_&_restart";
        }
        else
            user = obj.get_user(mail);

        String start = obj.get_start(file);
        String end = obj.get_end(file);
        String frame_bgn = obj.get_frame_bgn(file);
        String frame_end = frame_bgn + "\n";

        StringBuilder sb = new StringBuilder();
        sb.append(start).append(" **************************************************************************").append(start.length() == 1 ? "* " : " ").append(frame_end);
        sb.append(String.format(frame_bgn + "   ___  ____  __    ____  __   ____  __     __   ____    %-19s " + frame_end, filename));
        sb.append(frame_bgn + "  / __)(  __)(  )  (  __)/ _\\ (  _ \\(  )   / _\\ (  _ \\                       " + frame_end);
        sb.append(String.format(frame_bgn + " ( (_ \\ ) _)  )(    ) _)/    \\ ) _ (/ (_/\\/    \\ ) _ (   Created by %-8s " + frame_end, user));
        sb.append(String.format(frame_bgn + "  \\___/(__)  (__)  (__) \\_/\\_/(____/\\____/\\_/\\_/(____/   %s " + frame_end, dte));
        sb.append(frame_bgn + "                                                                             " + frame_end);
        sb.append(String.format(frame_bgn + "         Contact: %-39sUpdated by %-8s " + frame_end, mail, user));
        sb.append(String.format(frame_bgn + "                                                         %s " + frame_end, dte));
        sb.append(frame_bgn + " **************************************************************************").append(end.length() == 1 ? "* " : " ").append(end + "\n\n");

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().insertString(0, sb.toString());
        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);
    }
}
