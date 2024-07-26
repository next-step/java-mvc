package camp.nextstep.dao;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import camp.nextstep.domain.User;

public class InMemoryUserDao {

    private static final Map<String, User> database = new ConcurrentHashMap<>();

    static {
        final var user = new User(1, "gugu", "password", "hkkang@woowahan.com");
        database.put(user.getAccount(), user);
    }

    public static void save(User user) {
        database.put(user.getAccount(), user);
    }

    public static Optional<User> findByAccount(String account) {
        return Optional.ofNullable(database.get(account));
    }

    private InMemoryUserDao() {}
}
