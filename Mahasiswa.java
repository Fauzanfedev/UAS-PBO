public class Mahasiswa extends Anggota {
    private String npm;

    public Mahasiswa(String id, String nama, String npm) {
        super(id, nama);
        this.npm = npm;
    }

    public String getNpm() { return npm; }
    public void setNpm(String npm) { this.npm = npm; }
}
