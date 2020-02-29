package parser.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Accessors(chain = true)
public class ParsedRow {
    private String name;
    private LocalDate date;
    private BigDecimal price;

    @Override
    public String toString() {
        return name + ", " + DateUtils.format(date) + ", " + price;
    }
}
