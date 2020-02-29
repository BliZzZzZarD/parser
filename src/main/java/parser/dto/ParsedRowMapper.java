package parser.dto;

import lombok.extern.slf4j.Slf4j;
import utils.DateUtils;

import java.math.BigDecimal;

@Slf4j
public abstract class ParsedRowMapper {
    public static ParsedRow getParserRow(String rowString) {
        String[] splittedRow = rowString.split(",");

        return new ParsedRow()
                .setName(splittedRow[0])
                .setDate(DateUtils.parse(splittedRow[1]))
                .setPrice(new BigDecimal(splittedRow[2]));
    }
}
