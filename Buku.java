public class Buku {
    private String id;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String status; 

    public Buku(String id, String judul, String penulis, int tahunTerbit, String status) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.status = status;
    }

    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getTahunTerbit() { return tahunTerbit; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setJudul(String judul) { this.judul = judul; }
    public void setPenulis(String penulis) { this.penulis = penulis; }
    public void setTahunTerbit(int tahunTerbit) { this.tahunTerbit = tahunTerbit; }
    public void setStatus(String status) { this.status = status; }
}