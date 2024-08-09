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
        database.put(user.getAccount(), user);
    }

    public static User findByAccount(String account) {
        return database.get(account);
    }

    private InMemoryUserDao() {}
}
