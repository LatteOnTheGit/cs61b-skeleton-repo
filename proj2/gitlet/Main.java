package gitlet;

import java.io.File;
import java.io.IOException;
import static gitlet.Utils.*;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Latte
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) throws IOException {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                if(Repository.GITLET_DIR.exists()) {
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    break;
                }
                RepoControl.init();
                break;
            case "add":
                String FileName = args[1];
                File theFile = join(Repository.CWD,FileName);
                if(!theFile.exists()) {
                    System.out.println("File does not exist.");
                    break;
                }
                RepoControl.add(FileName);
                break;
            case "commit":
                if (args.length == 1) {
                    System.out.println("Please enter a commit message.");
                    break;
                }
                String message = args[1];
                if (!RepoControl.commit(message)) {
                    System.out.println("No changes added to the commit.");
                    break;
                }
                break;
            case "log":
                RepoControl.log();
                break;
            case "checkout":
                int out = -1;
                if (args.length == 2) {
                    out = RepoControl.checkoutBranch(args[1]);
                }
                else if (args.length == 3) {
                    out = RepoControl.checkoutHEAD(args[2]);
                } else if (args.length == 4) {
                    out = RepoControl.checkout(args[1], args[3]);
                }
                switch (out) {
                    case -1:
                        System.out.println("wrong input, format: checkout [ID][Filename]");
                        break;
                    case 0:
                        break;
                    case 1:
                        System.out.println("File does not exist in that commit.");
                        break;
                    case 2:
                        System.out.println("No commit with that id exists.");
                        break;
                    case 3:
                        System.out.println("No such branch exists.");
                        break;
                    case 4:
                        System.out.println("No need to checkout the current branch.");
                        break;
                    case 5:
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        break;
                }
                break;
            case "branch":
                String branchName = args[1];
                if (!RepoControl.branch(branchName)) {
                    System.out.println("A branch with that name already exists.");
                    break;
                }
                break;
            case "rm-branch":
                String rmBranchName = args[1];
                int rmOut = RepoControl.rmBranch(rmBranchName);
                switch (rmOut) {
                    case 0:
                        System.out.println("A branch with that name does not exist.");
                        break;
                    case 1:
                        System.out.println("Cannot remove the current branch.");
                        break;
                    case 2:
                        break;
                }
                break;
            case "rm":
                String fileName = args[1];
                if (RepoControl.rm(fileName)) break;
                else System.out.println("No reason to remove the file.");
                break;
            case "status":
                RepoControl.status();
                break;
            case "merge":
                String branchNameMerge = args[1];
                int mergeOut = RepoControl.merge(branchNameMerge);
                switch (mergeOut) {
                    case 0:
                        break;
                    case 1:
                        System.out.println("A branch with that name does not exist.");
                        break;
                    case 2:
                        System.out.println("Cannot merge a branch with itself.");
                        break;
                    case 3:
                        System.out.println("Given branch is an ancestor of the current branch.");
                        break;
                    case 4:
                        System.out.println("Current branch fast-forwarded.");
                        break;
                    case -1:
                        System.out.println("You have uncommitted changes.");
                        break;
                    case -2:
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        break;
                }
                break;

        }
    }
}
