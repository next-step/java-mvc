package samples;

import java.util.Objects;

public class TestUser {
    private String userId;
    private String password;
    private int age;

    public TestUser(String userId, String password, int age) {
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

    @Override
    public final boolean equals(Object object) {

        if (this == object) return true;
        if (!(object instanceof TestUser testUser)) return false;

        return age == testUser.age
                && Objects.equals(userId, testUser.userId)
                && Objects.equals(password, testUser.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "TestUser{"
                + "userId='"
                + userId
                + '\''
                + ", password='"
                + password
                + '\''
                + ", age="
                + age
                + '}';
    }
}
