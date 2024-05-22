package ntu.dinhvu61131562.instagramclone.Model;

public class Post {
    private String postId;
    private String postImage;
    private String moTa;
    private String nguoiDang;

    public Post(String postId, String postImage, String moTa, String nguoiDang) {
        this.postId = postId;
        this.postImage = postImage;
        this.moTa = moTa;
        this.nguoiDang = nguoiDang;
    }

    public Post() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNguoiDang() {
        return nguoiDang;
    }

    public void setNguoiDang(String nguoiDang) {
        this.nguoiDang = nguoiDang;
    }
}
