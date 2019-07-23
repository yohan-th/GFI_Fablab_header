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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class Save extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent AnActionEvent) {
        VirtualFile file = AnActionEvent.getData(VIRTUAL_FILE);
        assert file != null;
        StringBuilder filename = new StringBuilder(file.getName());
        String extension = file.getExtension();
        Document doc = FileDocumentManager.getInstance().getDocument(file);
        if (doc == null)
            return;
        String start = doc.getText(new TextRange(0, 5));
        if (!start.equals("/* **") && !start.equals("# ***"))
            return;
        String dte = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        String mail = System.getenv("MAIL");
        String user = null;
        if (mail == null)
            user = "MAIL=Ø";
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

        String ec;
        String st;
        if (extension != null && (extension.equals("c") || extension.equals("h") || extension.equals("php")))
        {
            ec = "*/";
            st = "/";
        }
        else if ((extension != null && extension.equals("py")) || filename.toString().contains("Makefile"))
        {
            ec = "#";
            st = "#";
        }
        else
        {
            ec = "//";
            st = "/";
        }

        String header = doc.getText(new TextRange(0, 538));

        String et = st + "\n";
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(header + "Updated by %-8s " + et, user));
        sb.append(st).append(String.format("                                                         %s " + et, dte));
        sb.append(st).append(" **************************************************************************").append(ec.length() == 1 ? "* " : " ").append(ec);

        Runnable runnable = () -> AnActionEvent.getData(LangDataKeys.EDITOR).getDocument().replaceString(0, 719, sb.toString());

        WriteCommandAction.runWriteCommandAction(getEventProject(AnActionEvent), runnable);

    }
}