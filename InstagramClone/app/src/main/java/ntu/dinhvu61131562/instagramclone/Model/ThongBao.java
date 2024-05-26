package ntu.dinhvu61131562.instagramclone.Model;

public class ThongBao {
    private String userId;
    private String text;
    private String postId;
    private boolean daDang;

    public ThongBao(String userId, String text, String postId, boolean daDang) {
        this.userId = userId;
        this.text = text;
        this.postId = postId;
        this.daDang = daDang;
    }

    public ThongBao() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isDaDang() {
        return daDang;
    }

    public void setDaDang(boolean daDang) {
        this.daDang = daDang;
    }
}
