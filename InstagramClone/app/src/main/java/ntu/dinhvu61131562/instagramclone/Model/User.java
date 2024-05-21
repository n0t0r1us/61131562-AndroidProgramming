package ntu.dinhvu61131562.instagramclone.Model;

public class User {

    private String id;
    private String taiKhoan;
    private String hoTen;
    private String imageUrl;
    private String bio;

    public User(String id, String taiKhoan, String hoTen, String imageUrl, String bio) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.hoTen = hoTen;
        this.imageUrl = imageUrl;
        this.bio = bio;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
