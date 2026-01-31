package de.dentareport.utils.xls;

import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: TEST?
public class XlsParse {

    private Pattern boldItalic = Pattern.compile(Pattern.quote("°°") + "(.*?)" + Pattern.quote("°°"));
    private final XlsFonts xlsFonts;

    public XlsParse(XSSFWorkbook workbook) {
        this.xlsFonts = new XlsFonts(workbook);
    }

    public RichTextString parse(String value) {
        return parseMatches(value, locateMatchingChunks(value));
    }

    private Map<Integer, Integer> locateMatchingChunks(String value) {
        Map<Integer, Integer> matches = new HashMap<>();
        Matcher matcher = boldItalic.matcher(value);
        int count = 0;
        while (matcher.find()) {
            int correction = count * 4;
            count++;
            matches.put(matcher.start() - correction, matcher.end() - 4 - correction);
        }
        return matches;
    }

    private RichTextString parseMatches(String value,
                                        Map<Integer, Integer> matches) {
        RichTextString richString = removeFormatting(value);
        matches.forEach((k, v) -> richString.applyFont(k, v, xlsFonts.font("bold_italic")));
        return richString;
    }

    private RichTextString removeFormatting(String value) {
        return new XSSFRichTextString(value.replace("°°", ""));
    }
}
