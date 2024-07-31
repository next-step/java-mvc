package camp.nextstep.domain;

public class TestUSer {

    private String userId;
    private String password;
    private int age;

    private TestUSer(final String userId, final String password, final int age) {
        this.userId = userId;
        this.password = password;
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }
}
