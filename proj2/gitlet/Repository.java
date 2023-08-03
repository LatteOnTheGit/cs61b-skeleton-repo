package gitlet;

import java.io.File;
import static gitlet.Utils.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
// TODO: any imports you need here

/** Represents a gitlet repository.
 *  will apply all functionalities list below:
 *
 * Init repo; create directory .gitlet, and automatic start with one commit contains no file
 *
 * check status; return info of branchs, staging area, Removed files, `Modifications Not Staged For Commit`, `Untracked Files`
 *
 * addFile (for commit) to staging area;
 *
 * rmFile to staging area;
 *
 * commitFile create a commit and clear staging area; move HEAD and branchName to current commit
 *
 * log: return all commit from current HEAD;
 *
 * global log: returns every commits(un-ordered)
 *
 * find[message]: find uid use message, if multiple commits exist print separate lines
 *
 * checkout: take file/files in given commit to workingDir;
 *
 * create branch; point branchName to Current HEAD Branch
 *
 * rm branch;
 *
 * resetCommit; rm all file in workingDir that not tracked in specific commit; Move HEAD to that commit
 *
 * Merge
 *
 *
 *  @author Latte
 */
public class Repository implements Serializable {
    /** invariant:

     all failure cases dealt within main, consider all possible cases that not a failure

     Pointer[0] is HEAD

     Pointer[1] is curBranch(default master)

     Pointer[2] is master

     Notice Save REPO should be done in above layer(Main)

     beside first init repo create instance of REPO;

     each time change status, use persistence retrieve REPO instance; And save after use;
     */

    HashMap<String, String> commitsRela = new HashMap<>(), fileVersion = new HashMap<>();
    ArrayList<String> Pointers;
    String HEAD, master;

    /** The current working directory. */

    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static final File Blobs = join(GITLET_DIR, "blobs");

    public static final File commits = join(GITLET_DIR, "commits");

    public static final File Staging = join(GITLET_DIR, "staging");

    public static final File addition = join(Staging, "addition");

    public static final File removal = join(Staging, "removal");


    public Repository() {

    };

    public void init() throws IOException {
        addition.mkdirs();
        removal.mkdir();
        Blobs.mkdir();
        commits.mkdir();
        Commit curCommit = new Commit();
        String curSHA = getSHA1(curCommit);
        commitsRela.put(curSHA,"-1");
        HEAD = curSHA;
        master = curSHA;
        // save to commits Named SHA1, content serialized
//        File thisCommit = join(commits, curSHA);
//        thisCommit.createNewFile();
//        writeObject(thisCommit, curCommit);
        saveCommit(curCommit, curSHA);
    }

    public void addFile(String fileName) throws IOException {
        File theFile = join(CWD,fileName);
        byte[] fileContent = Utils.readContents(theFile);
        String SHA1File = Utils.sha1(fileContent);
        String latestVersionCommitID = fileVersion.get(fileName);
        // test use
//        System.out.println("sha1 when add is :" + SHA1File);
        if(latestVersionCommitID != null) {
            Commit latestVersionCommit = readCommit(latestVersionCommitID);
            String latestVersion = latestVersionCommit.monitoredFiles.get(fileName);
            if(latestVersion.equals(SHA1File)) return;
        }
        File saveFile = join(addition,fileName);
        saveFile.createNewFile();
        writeContents(saveFile, fileContent);
    }

    public void commitFiles(String message) throws IOException {
        // ignore rm
        Commit curCommit = new Commit();
        curCommit.addFromStaging(message);
        String SHA1Curcommit = getSHA1(curCommit);
        Set<String> filesOnTrack = curCommit.monitoredFiles.keySet();
        for(String file:filesOnTrack) {
            fileVersion.put(file, SHA1Curcommit);
            File deleteFile = join(addition, file);
            String SHA1File = curCommit.monitoredFiles.get(file);
            File blobFile = join(Blobs, SHA1File);
            byte[] fileContent = readContents(deleteFile);
            // to be add serialized content there or on add file
            blobFile.createNewFile();
            writeContents(blobFile, fileContent);
//            restrictedDelete(deleteFile);
            // use io delete for test
            deleteFile.delete();
        }
        commitsRela.put(SHA1Curcommit, HEAD);
        HEAD = SHA1Curcommit;
        // to be changed to branch name pluger
        master = HEAD;
        saveCommit(curCommit, SHA1Curcommit);
    }

    //without worrying about merge situation
    public StringBuilder log() {
        String curTempPointer = HEAD;
        StringBuilder res = new StringBuilder();
        boolean firstIteration = true;
        while (!curTempPointer.equals("-1")) {
            if (!firstIteration) {
                res.append("\n");
            } else {
                firstIteration = false;
            }
            res.append("===\n");
            res.append("commit ").append(curTempPointer).append("\n");
            Commit Curcommit = readCommit(curTempPointer);
            res.append("Date: ").append(Curcommit.getTimestamp()).append("\n");
            res.append(Curcommit.getMessage()).append("\n");
            curTempPointer = commitsRela.get(curTempPointer);
        }
        return res;
    }

    public boolean checkout(String commitID, String fileName) {
        Commit checkCommit = readCommit(commitID);
        String SHA1File = checkCommit.monitoredFiles.get(fileName);
        if (SHA1File == null) return false;
        File blobsFile = join(Blobs, SHA1File);
        byte[] content = readContents(blobsFile);
        File toWrite = join(CWD, fileName);
        writeContents(toWrite, content);
        return true;
    }

    public boolean checkout(String fileName) {
        return checkout(HEAD, fileName);
    }

    private static Commit readCommit(String SHA1Code) {
        File commitAddress = join(commits, SHA1Code);
        Commit curCommit = readObject(commitAddress, Commit.class);
        return curCommit;
    }

//    public void rmFiles()

    private static void saveCommit(Commit curCommit, String SHA1Commit) throws IOException {
        File thisCommit = join(commits, SHA1Commit);
        thisCommit.createNewFile();
        writeObject(thisCommit, curCommit);

    }

    private static String getSHA1(Serializable obj) {
        byte[] content = Utils.serialize(obj);
        return Utils.sha1(content);
    }
}
