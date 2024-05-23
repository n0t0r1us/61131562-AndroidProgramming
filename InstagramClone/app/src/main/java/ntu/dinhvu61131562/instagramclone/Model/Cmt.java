package ntu.dinhvu61131562.instagramclone.Model;

public class Cmt {

    private String Cmt;
    private String nguoiDang;

    public Cmt(String cmt, String nguoiDang) {
        this.Cmt = cmt;
        this.nguoiDang = nguoiDang;
    }

    public Cmt() {
    }

    public String getCmt() {
        return Cmt;
    }

    public void setCmt(String cmt) {
        this.Cmt = cmt;
    }

    public String getNguoiDang() {
        return nguoiDang;
    }

    public void setNguoiDang(String nguoiDang) {
        this.nguoiDang = nguoiDang;
    }
}
