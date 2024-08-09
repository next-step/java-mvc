package camp.nextstep.domain;

public class TestUser {
	private String userId;
	private String password;
	private long id;
	private int age;

	public TestUser() {
	}

	public TestUser(String userId, String password, long id, int age) {
		this.userId = userId;
		this.password = password;
		this.id = id;
		this.age = age;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestUser{" +
			"userId='" + userId + '\'' +
			", password='" + password + '\'' +
			", id=" + id +
			", age=" + age +
			'}';
	}
}
