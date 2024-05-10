package ntu.dinhvu61131562.exsqllitebookdb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Buoc 0. Tạo file CSDL
//        SQLiteDatabase db = openOrCreateDatabase("QLSach.db", //tên file, //giới hạn truy cập, // con trỏ
//                MODE_PRIVATE,
//                 null);
//        //b1 Tạo bảng
//        //câu lệnh tạo bảng
//        String sqlXoaBangNeuDaCo = "DROP TABLE IF EXISTS Books;";
//        String sqlTaoBang = "CREATE TABLE Books( BookID integer PRIMARY KEY, " +
//                                                "BookName text, " +
//                                                "Page integer, " +
//                                                "Price Float, " +
//                                                "Description text);";
//        //thực hiện lệnh sql
//        db.execSQL(sqlXoaBangNeuDaCo);
//        db.execSQL(sqlTaoBang);
//        //thêm 1 số dòng dữ liệu vào bảng
//        String sqlThem1 = "INSERT INTO Books VALUES(1, 'Java', 100, 9.99, 'sách về java');";
//        String sqlThem2 = "INSERT INTO Books VALUES(2, 'Android', 320, 19.00, 'Android cơ bản');";
//        String sqlThem3 = "INSERT INTO Books VALUES(3, 'Học làm giàu', 120, 0.99, 'sách đọc cho vui');";
//        String sqlThem4 = "INSERT INTO Books VALUES(4, 'Tử điển Anh-Việt', 1000, 29.50, 'Từ điển 100.000 từ');";
//        String sqlThem5 = "INSERT INTO Books VALUES(5, 'CNXH', 1, 1, 'chuyện cổ tích');";
//        db.execSQL(sqlThem1);
//        db.execSQL(sqlThem2);
//        db.execSQL(sqlThem3);
//        db.execSQL(sqlThem4);
//        db.execSQL(sqlThem5);
//        //để quan sát trực quan file .db? => dùng ứng dụng ...
        // để mở đc, ta save file từ đt ra đĩa cứng
//
//
//        //ta đóng lại để check
//        db.close();
        //truy vấn select
        //b1. mở csdl
        SQLiteDatabase db = openOrCreateDatabase("QLSach.db", //tên file, //giới hạn truy cập, // con trỏ
                MODE_PRIVATE,
                null);
        //b2 thực thi câu lệnh select
        String sqlSelect = "Select * from Books;";
        Cursor cs = db.rawQuery(sqlSelect, null);
        cs.moveToFirst(); //đưa con trỏ bản ghi về hàng đầu tiên
        // bước 3 đổ vào biến nào đó để xử lý
        //3.1 xây dựng model/class cho bảng books , ví dụ: Book.java
        //3.2 tạo biến arraylist<Book> dsSach;
        ArrayList<Book> dsSach = new ArrayList<Book>();
        //3.3 Duyệt qua lần lượt từng bản ghi và thêm vào danhSach
        while (cs.moveToNext()) { //còn bản ghi để chuyển tới
            //lấy dữ liệu từng cột ở dòng hiện tại
            int idSach = cs.getInt(0); // lấy dữ liệu ở cột 0, kiểu int
            String tenSach = cs.getString(1);
            int soTrang = cs.getInt(2);
            float gia = cs.getFloat(3);
            String moTa = cs.getString(4);
            //tạo 1 đối tượng sách và thêm vào danh sách
            Book b = new Book(idSach, tenSach, soTrang, gia, moTa);
            dsSach.add(b);
        }
        //B4 hiện lên listview, recycleview
        // để cho nhanh , ở đây chỉ hiện tên sách
        ArrayList<String> dsTenSach = new ArrayList<String>();
        for (int i = 0; i < dsSach.size(); i++)
            dsTenSach.add(dsSach.get(i).getBookName());
        //phần việc hiện lên là của sv
    }
//
//    ArrayList<Book> getDataForRV() {
//        //truy vấn select
//        //b1. mở csdl
//        SQLiteDatabase db = openOrCreateDatabase("QLSach.db", //tên file, //giới hạn truy cập, // con trỏ
//                MODE_PRIVATE,
//                null);
//        //b2 thực thi câu lệnh select
//        String sqlSelect = "Select * from Books;";
//        Cursor cs = db.rawQuery(sqlSelect, null);
//        cs.moveToFirst(); //đưa con trỏ bản ghi về hàng đầu tiên
//        // bước 3 đổ vào biến nào đó để xử lý
//        //3.1 xây dựng model/class cho bảng books , ví dụ: Book.java
//        //3.2 tạo biến arraylist<Book> dsSach;
//        ArrayList<Book> dsSach = new ArrayList<Book>();
//        //3.3 Duyệt qua lần lượt từng bản ghi và thêm vào danhSach
//        while (cs.moveToNext()) { //còn bản ghi để chuyển tới
//            //lấy dữ liệu từng cột ở dòng hiện tại
//            int idSach = cs.getInt(0); // lấy dữ liệu ở cột 0, kiểu int
//            String tenSach = cs.getString(1);
//            int soTrang = cs.getInt(2);
//            float gia = cs.getFloat(3);
//            String moTa = cs.getString(4);
//            //tạo 1 đối tượng sách và thêm vào danh sách
//            Book b = new Book(idSach, tenSach, soTrang, gia, moTa);
//            dsSach.add(b);
//        }
//        return dsSach;
//    }
//    ArrayList<String> getTenSachForRY(ArrayList<Book> dsSach) {
//        ArrayList<String> dsTenSach = new ArrayList<String>();
//        for (int i=0; i<dsSach.size(); i++ )
//            dsTenSach.add(dsSach.get(i).getBookName());
//        return dsTenSach;
//}
}
