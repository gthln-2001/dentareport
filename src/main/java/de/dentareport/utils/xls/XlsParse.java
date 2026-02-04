package de.dentareport.utils.xls;

import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: TEST?
public class XlsParse {

    private final Pattern boldItalic = Pattern.compile(Pattern.quote("°°") + "(.*?)" + Pattern.quote("°°"));
    private final XlsFonts xlsFonts;
    private final SXSSFWorkbook workbook;

    public XlsParse(SXSSFWorkbook workbook) {
        this.xlsFonts = new XlsFonts(workbook);
        this.workbook = workbook;
    }

    public RichTextString parse(String value) {
        return parseMatches(value, locateMatchingChunks(value));
    }

    private Map<Integer, Integer> locateMatchingChunks(String value) {
        Map<Integer, Integer> matches = new TreeMap<>();
        Matcher matcher = boldItalic.matcher(value);
        int count = 0;
        while (matcher.find()) {
            int correction = count * 4;
            count++;
            matches.put(matcher.start() - correction,
                    matcher.end() - 4 - correction);
        }
        return matches;
    }

    private RichTextString parseMatches(String value,
                                        Map<Integer, Integer> matches) {
        RichTextString richString = removeFormatting(value);

        int len = richString.getString().length();

        matches.forEach((k, v) -> {
            int start = Math.max(0, k);
            int end = Math.min(v, len);
            if (start < end) {
                richString.applyFont(start, end, xlsFonts.font("bold_italic"));
            }
        });

        return richString;
    }

    private RichTextString removeFormatting(String value) {
        return workbook.getCreationHelper().createRichTextString(value.replace("°°", ""));
    }
}
