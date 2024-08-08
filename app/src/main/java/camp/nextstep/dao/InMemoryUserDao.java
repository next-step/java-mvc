package camp.nextstep.dao;

import camp.nextstep.domain.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDao {

    private static final Map<String, User> database = new ConcurrentHashMap<>();

    static {
        final var user = new User(1, "gugu", "password", "hkkang@woowahan.com");
        database.put(user.getAccount(), user);
    }

    public static void save(User user) {
        assert user.getId() == 0L;

        long id = getNewId();
        User newUser = new User(id, user);
        database.put(newUser.getAccount(), newUser);
    }

    private static long getNewId() {
        long maxId = database.values().stream()
                         .mapToLong(User::getId)
                         .max()
                         .orElse(0L);
        return maxId + 1;
    }

    public static User findByAccount(String account) {
        return database.get(account);
    }

    private InMemoryUserDao() {}
}
