/* ************************************************************************** /
/   ___  ____  __    ____  __   ____  __     __   ____    tools.java          /
/  / __)(  __)(  )  (  __)/ _\ (  _ \(  )   / _\ (  _ \                       /
/ ( (_ \ ) _)  )(    ) _)/    \ ) _ (/ (_/\/    \ ) _ (   Created by yohan    /
/  \___/(__)  (__)  (__) \_/\_/(____/\____/\_/\_/(____/   2019/07/23 16:07:14 /
/                                                                             /
/         Contact: yohanthollet@gfi.world                 Updated by yohan    /
/                                                         2019/07/23 16:07:14 /
/ ************************************************************************** */

package fr.ythollet.header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intellij.openapi.vfs.VirtualFile;

public class tools {
    public String get_user(String mail) {
            String user;
            Pattern p = Pattern.compile("([a-zA-Z0-9\\-]+)");
            Matcher m = p.matcher(mail);
            boolean find = m.find();
            if (find && m.group(0).length() > 8)
                user = m.group(0).substring(0, 8);
            else if (find)
                user = m.group(0);
            else
                user = "error";
            return (user);
    }
    public String get_start(VirtualFile file){

            String file_name = file.getName();
            String extension = file.getExtension();

            if ((extension != null && extension.equals("py")) || file_name.contains("Makefile"))
                return ("#");
            else
                return ("/*");
    }
    public String get_end(VirtualFile file){

        String file_name = file.getName();
        String extension = file.getExtension();

        if ((extension != null && extension.equals("py")) || file_name.contains("Makefile"))
            return ("#");
        else
            return ("*/");
    }
    public String get_frame_bgn(VirtualFile file){

        String file_name = file.getName();
        String extension = file.getExtension();

        if ((extension != null && extension.equals("py")) || file_name.contains("Makefile"))
            return ("#");
        else
            return ("/");
    }
}
