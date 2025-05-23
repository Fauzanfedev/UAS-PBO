public class User {
    private String id;
    private String nama;
    private String password;
    private String role; // "pengunjung" atau "pustakawan"

    public User(String id, String nama, String password, String role) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.role = role;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setId(String id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
