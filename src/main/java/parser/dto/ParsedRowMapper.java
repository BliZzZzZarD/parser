package parser.dto;

import lombok.extern.slf4j.Slf4j;
import utils.DateAdapter;

import java.math.BigDecimal;

@Slf4j
public abstract class ParsedRowMapper {
    public static ParsedRow getParserRow(String rowString) {
        String[] splittedRow = rowString.split(",");

        return new ParsedRow()
                .setName(splittedRow[0])
                .setDate(DateAdapter.parse(splittedRow[1]))
                .setPrice(new BigDecimal(splittedRow[2]));
    }
}
