package jig.domain.model.report;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Report<ROW> {

    ConvertibleItem<ROW>[] convertibleItems;

    String title;
    List<ROW> rows;

    public Report(String title, List<ROW> rows, ConvertibleItem<ROW>[] values) {
        this.title = title;
        this.convertibleItems = values;
        this.rows = rows;
    }

    public Title title() {
        return new Title(title);
    }

    public ReportRow headerRow() {
        return ReportRow.of(
                Arrays.stream(convertibleItems).map(ConvertibleItem::name).toArray(String[]::new));
    }

    public List<ReportRow> rows() {
        return rows.stream()
                .map(row -> ReportRow.of(
                        Arrays.stream(convertibleItems).map(converter -> converter.convert(row)).toArray(String[]::new)))
                .collect(Collectors.toList());
    }
}
