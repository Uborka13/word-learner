package soft.ubi.hu.sztanul.persistence.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word")
public class Word {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "color")
    private String color;

    public Word() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public Word withValue(String value) {
        this.value = value;
        return this;
    }

    public Word withColor(String color) {
        this.color = color;
        return this;
    }
}