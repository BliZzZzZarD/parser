package parser.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ParsedRow {
    private String name;
    private LocalDate date;
    private BigDecimal price;
}
