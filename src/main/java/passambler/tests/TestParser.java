package passambler.tests;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestParser {
    private List<String> lines;

    private Map<String, StringBuilder> sections = new HashMap();

    public TestParser(File file) throws IOException {
        this.lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
    }

    public Test parse() {
        parseSections();

        return new Test(sections.get("desc").toString(), sections.get("input").toString(), sections.get("result").toString());
    }

    private void parseSections() {
        String section = null;

        for (String line : lines) {
            if (line.startsWith("--") && line.endsWith("--")) {
                section = line.substring(2, line.length() - 2);

                sections.put(section, new StringBuilder());
            } else {
                sections.get(section).append(line);

                if (!line.equals(lines.get(lines.size() - 1))) {
                    sections.get(section).append("\n");
                }
            }
        }
    }
}