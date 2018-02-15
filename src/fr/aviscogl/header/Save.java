package fr.aviscogl.header;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Save implements ApplicationComponent {
    public Save() {
    }

    @Override
    public void initComponent() {
        VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileListener() {
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                VirtualFile file = event.getFile();
                StringBuilder filename = new StringBuilder(file.getName());
                String extension = file.getExtension();
                if (extension == null && !filename.toString().contains("Makefile"))
                    return;
                if (extension != null && !filename.toString().contains("Makefile") &&
                    !extension.equals("c") && !extension.equals("h"))
                    return;
                Document doc = FileDocumentManager.getInstance().getDocument(file);
                if (doc == null)
                    return;
                String start = doc.getText(new TextRange(0, 5));
                if (!start.equals("/* **") && !start.equals("# ***"))
                    return;
                String dte = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                String header;
                if (filename.toString().contains("Makefile"))
                    header = String.format("#   %-42s  ###    #+. /#+    ###.fr     #", String.format("Updated: %s by %s", dte, System.getenv("USER").equals("alexis") ? "aviscogl" : System.getenv("USER")));
                else
                    header = String.format("/*   %-42s  ###    #+. /#+    ###.fr     */", String.format("Updated: %s by %s", dte, System.getenv("USER").equals("alexis") ? "aviscogl" : System.getenv("USER")));

                Runnable runnable = () -> doc.replaceString(648, 648 + header.length(), header);

                if (doc.getModificationStamp() > 0) {
                    System.out.println("Saved doc: " + filename);
                    WriteCommandAction.runWriteCommandAction(ProjectManager.getInstance().getOpenProjects()[0], runnable);
                }
            }
        });
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Save";
    }
}