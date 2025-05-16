package fh.bswe.statusserver.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class StatusDto {
    private String info;
    private String name;
    private LocalDateTime date;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatusDto statusDto = (StatusDto) o;
        return Objects.equals(info, statusDto.info) && Objects.equals(name, statusDto.name) && Objects.equals(date, statusDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, name, date);
    }
}
