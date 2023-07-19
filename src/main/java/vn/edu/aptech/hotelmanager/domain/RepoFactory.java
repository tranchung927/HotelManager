package vn.edu.aptech.hotelmanager.domain;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.repo.RoomRepoImpl;
import vn.edu.aptech.hotelmanager.repo.UserRepoImpl;

public class RepoFactory {
    private static RepoFactory repoFactory;
    private RepoFactory() {}
    public static RepoFactory getInstance() {
        return (repoFactory == null) ? repoFactory = new RepoFactory() : repoFactory;
    }

    public <T extends IRepo> T getRepo(REPO_TYPE type) {
        return switch (type) {
            case USER -> (T) new UserRepoImpl();
            case ROOM -> (T) new RoomRepoImpl();
        };
    }
}
