package backend.sheets.response;

import java.util.Objects;

public class TotalHoursSheetsModel {
    private int completedHours;
    private int requiredHours;
    private String name;

    public TotalHoursSheetsModel(String name, int completedHours, int requiredHours) {
        this.name = name;
        this.completedHours = completedHours;
        this.requiredHours = requiredHours;
    }

    public static TotalHoursSheetsModel empty() {
        return new TotalHoursSheetsModel("Error", -1, -1);
    }

    public int getCompletedHours() {
        return completedHours;
    }

    public int getRequiredHours() {
        return requiredHours;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return empty().equals(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalHoursSheetsModel that = (TotalHoursSheetsModel) o;

        if (completedHours != that.completedHours) return false;
        if (requiredHours != that.requiredHours) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = completedHours;
        result = 31 * result + requiredHours;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
