package com.bjoernkw.mailtrigger.mailer;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.Renderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class MailRenderer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String render(String text, String format) {
        if (text == null || text.length() == 0) {
            return text;
        }

        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(text);

            Renderer renderer;
            if (format.equals("html")) {
                renderer = HtmlRenderer.builder().build();
            } else {
                renderer = TextContentRenderer.builder().build();
            }

            return renderer.render(document);
        } catch (Exception e) {
            logger.error("A problem occurred while rendering an email: {}", e);

            return text;
        }
    }
}
