package ntu.dinhvu61131562.instagramclone.Model;

public class Cmt {

    private String Cmt;
    private String nguoiDang;
    private String cmtId;

    public Cmt(String cmt, String nguoiDang, String cmtId) {
        this.Cmt = cmt;
        this.nguoiDang = nguoiDang;
        this.cmtId = cmtId;
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

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }
}
