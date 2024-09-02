package worksheet1.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "letters")
public class Letters {

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField(columnName = "letter", defaultValue = "Unknown")
    private String letter;


    /*
     * No-arguments constructor required by the OR-Mapper
     */
    public Letters() {}

    public Letters(Integer id, String letter) {
        this.id = id;
        this.letter = letter;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
