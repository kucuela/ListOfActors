package studio.kucuela.listofactors.objects;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = Film.TABLE_NAME)
public class Film {

    public static final String TABLE_NAME = "film";
    public static final String ID_FILM = "id";
    public static final String NASLOV = "naslov";
    public static final String GODINA = "godina";
    public static final String ZANR = "zanr";




    @DatabaseField(generatedId = true, columnName = ID_FILM)
    int id;

    @DatabaseField(columnName = NASLOV)
    String naslov;

    @DatabaseField(columnName = GODINA)
    String godina;


    @DatabaseField(columnName = ZANR)
    String zanr;








    public Film() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String opis) {
        this.godina = godina;
    }



    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }


    @Override
    public String toString() {
        return naslov;
    }

}