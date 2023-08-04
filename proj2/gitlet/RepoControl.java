package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static gitlet.Utils.*;


public class RepoControl {
    public static final File commitRelation = join(Repository.GITLET_DIR, "commitRelation");
    public static void init() throws IOException {
        Repository REPO = new Repository();
        REPO.init();
//        File commitRelation = join(Repository.GITLET_DIR, "commitRelation");
        commitRelation.createNewFile();
        Utils.writeObject(commitRelation, REPO);
    }

    public static void add(String fileName) throws IOException {
        Repository Repo = reloadRepo();
        Repo.addFile(fileName);
    }

    public static void commit(String message) throws IOException {
        Repository Repo = reloadRepo();
        Repo.commitFiles(message);
        writeObject(commitRelation, Repo);
    }

    public static void log() {
        Repository Repo = reloadRepo();
        System.out.println(Repo.log());
    }

    public static int checkout(String commitID, String fileName) {
        Repository Repo = reloadRepo();
        if (!Repo.commitsRela.containsKey(commitID)) {
            return 2;
        }
//        String theID = Repo.fileVersion.get(fileName);
        if (Repo.checkout(commitID, fileName)) return 0;
        else return 1;
    }

    public static int checkoutHEAD(String fileName) {
        Repository Repo = reloadRepo();
        return checkout(Repo.Pointer.get(Repo.HEAD), fileName);
    }

    public static int checkoutBranch(String branchName) {
        Repository Repo = reloadRepo();
        if(!Repo.Pointer.containsKey(branchName)) return 3;
        if(Repo.HEAD.equals(branchName)) return 4;
        int out = Repo.checkout(branchName) ? 0 : 5;
        writeObject(commitRelation, Repo);
        return out;

    }

    public static boolean branch(String branchName) {
        Repository Repo = reloadRepo();
        if (Repo.Pointer.containsKey(branchName)) return false;
        Repo.branch(branchName);
        writeObject(commitRelation, Repo);
        return true;
    }

    public static int rmBranch(String branchName) {
        Repository Repo = reloadRepo();
        if (!Repo.Pointer.containsKey(branchName)) return 0;
        if (Repo.HEAD.equals(branchName)) return 1;
        Repo.rmBranch(branchName);
        writeObject(commitRelation, Repo);
        return 2;
    }

    private static Repository reloadRepo() {
        Repository Repo = Utils.readObject(commitRelation, Repository.class);
        return Repo;
    }
}
