package ru.torchikov;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by sergei on 29.06.17.
 */
public class PageGenerator {
    private static PageGenerator ourInstance = new PageGenerator();
    private final Configuration configuration;

    public static PageGenerator getInstance() {
        return ourInstance;
    }

    private PageGenerator() {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    }

    public String getPage(String fileName, Map<String, Object> parameters) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate("HW12/templates" + File.separator + fileName);
            template.process(parameters, stream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream.toString();
    }
}
