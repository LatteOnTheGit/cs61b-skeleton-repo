package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import static gitlet.Utils.*;
import java.util.Locale;

/** Represents a gitlet commit object.
 *  Should contains: SHA1 uid(of the folder contains every thing inside the staging area),
 *  file to its version map, timestamp, and also message.
 *
 *  Persistence: should create a Folder named uid,
 *  and save commit object that contains All info of latest pre-commit(maybe inside map),
 *  does at a high level.
 *
 *  @author Latte
 */
public class Commit implements Serializable {
    /** refer FilesName to its SHA1; */
    public HashMap<String, String> monitoredFiles= new HashMap<>();

    private String message, timestamp;

    public Commit() {
        Date date = new Date(0);
        timestamp = dateSolver(date);
        message = "initial commit";
    }

    public void addFromStaging(String message) {
        List<String> files = Utils.plainFilenamesIn(Repository.addition);
        for(String file:files) {
            File curFile = join(Repository.addition, file);
            byte[] content = readContents(curFile);
            String SHA1File = sha1(content);
            monitoredFiles.put(file, SHA1File);

        }
        this.timestamp = dateSolver(new Date());
        this.message = message;
    }


    public String getMessage() {
        return this.message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    private static String getSHA1(Serializable obj) {
        byte[] content = Utils.serialize(obj);
        return Utils.sha1(content);
    }

    private String dateSolver(Date time) {
        Formatter formatter = new Formatter(Locale.US);
        formatter.format("%ta %tb %td %tT %tY %tz", time, time, time, time, time, time);
        return formatter.toString();
    }

}
