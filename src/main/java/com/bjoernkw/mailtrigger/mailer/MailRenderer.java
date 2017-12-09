package com.bjoernkw.mailtrigger.mailer;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class MailRenderer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String execute(String text) {
        if (text == null || text.length() == 0)
            return text;
        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(text);
            HtmlRenderer renderer = HtmlRenderer.builder().build();

            return renderer.render(document);
        } catch (Exception e) {
            logger.error("Problem while rendering email: {}", e);

            return text;
        }
    }
}
