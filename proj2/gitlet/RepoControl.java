package gitlet;

import java.io.File;
import java.io.IOException;
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
        String theID = Repo.fileVersion.get(fileName);
        if (Repo.checkout(commitID, fileName)) return 0;
        else return 1;
    }

    public static int checkout(String fileName) {
        Repository Repo = reloadRepo();
        return checkout(Repo.HEAD, fileName);
    }

    private static Repository reloadRepo() {
        Repository Repo = Utils.readObject(commitRelation, Repository.class);
        return Repo;
    }
}
