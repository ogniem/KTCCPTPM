package Bai1;

public class Account implements Comparable<Account>{
    private long id;
    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String createAt;

    public void output() {
        System.out.format("%-10d %-20s %-20s ", id , fullName, userName);
        System.out.format("%-30s %-16s %20s\n", email, phone, createAt);
    }

    public Account() {
    }

    public Account(long id, String fullName, String userName,
                   String password, String email,
                   String phone, String createAt) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateAt() {
        return createAt;
    }
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public int compareTo(Account o) {
        return this.getUserName().compareTo(o.getUserName());
    }

}