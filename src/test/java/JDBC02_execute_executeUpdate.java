import java.sql.*;

public class JDBC02_execute_executeUpdate {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        /*
 	A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
 	   dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.

 	    1) execute() metodu - boolean dondurur.
 	    2) executeUpdate() metodu - int deger dondurur.

 	B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur.
 	   - execute(), Boolean bir deger dondurur. DDL islemlerinde false dondururken,
 	     DML islemlerinde true deger dondurur.
 	   - Ozellikle, hangi tip SQL ifadesine hangi metodun uygun oldugunun bilinemedigi
 	     durumlarda tercih edilmektedir.

 	C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
 	   - bu islemlerde islemden etkilenen satir sayisini dondurur.
 	   - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.
    */

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        /*======================================================================
		  ORNEK1: isciler tablosunu siliniz.
 	    ========================================================================*/

        String dropQuery = "DROP TABLE isciler";

        // System.out.println(st.execute(dropQuery));

         if (!st.execute(dropQuery)){
             System.out.println("Isciler tablosu silindi!");
         }

        /*=======================================================================
          ORNEK2: isciler adinda bir tablo olusturunuz id int,
          birim VARCHAR(10), maas int
	    ========================================================================*/

        String createTable = "CREATE TABLE isciler" +
                             "(id INT, " +
                             "birim VARCHAR(10), " +
                             "maas INT)";

        if (!st.execute(createTable)){
            System.out.println("Isciler tablosu olusturuldu!");
        }

        /*=======================================================================
		  ORNEK3: isciler tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/

        String insertData = "INSERT INTO isciler VALUES(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));

        /*=======================================================================
	      ORNEK4: isciler tablosuna birden fazla yeni kayıt ekleyelim.

	        INSERT INTO isciler VALUES(70, 'HR', 5000)
            INSERT INTO isciler VALUES(60, 'LAB', 3000)
            INSERT INTO isciler VALUES(50, 'ARGE', 4000)
	     ========================================================================*/

        String [] queries = {"INSERT INTO isciler VALUES(70, 'HR', 5000)",
                             "INSERT INTO isciler VALUES(60, 'LAB', 3000)",
                             "INSERT INTO isciler VALUES(50, 'ARGE', 4000)"};

        int count = 0;
        for (String each : queries) {
           count += st.executeUpdate(each);
        }
        System.out.println(count + " satir eklendi!");

        /*=======================================================================
	      ORNEK5: isciler tablosuna goruntuleyin.
	     ========================================================================*/

        System.out.println("================ Isciler Tablosu ================");

        String selectQuery = "SELECT * FROM isciler";

        ResultSet iscilerTablosu = st.executeQuery(selectQuery);

        while(iscilerTablosu.next()){
            System.out.println(iscilerTablosu.getInt(1) + " " +
                                iscilerTablosu.getString(2) + " " +
                                iscilerTablosu.getInt(3));
        }

    }

}
