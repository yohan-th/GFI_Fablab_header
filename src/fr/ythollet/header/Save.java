/* ************************************************************************** /
/   ___  ____  __    ____  __   ____  __     __   ____    Save.java           /
/  / __)(  __)(  )  (  __)/ _\ (  _ \(  )   / _\ (  _ \                       /
/ ( (_ \ ) _)  )(    ) _)/    \ ) _ (/ (_/\/    \ ) _ (   Created by yohan    /
/  \___/(__)  (__)  (__) \_/\_/(____/\____/\_/\_/(____/   2019/07/23 15:49:02 /
/                                                                             /
/         Contact: yohanthollet@gfi.world                 Updated by yohan    /
/                                                         2019/07/23 16:07:07 /
/ ************************************************************************** */

package fr.ythollet.header;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class Save extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent AnActionEvent) {
        VirtualFile file = AnActionEvent.getData(VIRTUAL_FILE);
        assert file != null;
        StringBuilder filename = new StringBuilder(file.getName());
        Document doc = FileDocumentManager.getInstance().getDocument(file);
        if (doc == null)
            return;
        String start = doc.getText(new TextRange(0, 5));
        if (!start.equals("/* **") && !start.equals("# ***"))
            return;
        String dte = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        String mail = System.getenv("MAIL");
        tools obj = new tools();
        String user;
        if (mail == null)
            user = "MAIL=Ø";
        else
            user = obj.get_user(mail);

        String end = obj.get_end(file);
        String frame_bgn = obj.get_frame_bgn(file);
        String frame_end = frame_bgn + "\n";
        String header = doc.getText(new TextRange(0, 538));

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(header + "Updated by %-8s " + frame_end, user));
        sb.append(String.format(frame_bgn + "                                                         %s " + frame_end, dte));
        sb.append(frame_bgn + " **************************************************************************").append(end.length() == 1 ? "* " : " ").append(end);

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().replaceString(0, 719, sb.toString());

        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);
    }
}
