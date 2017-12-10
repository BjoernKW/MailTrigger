package com.bjoernkw.mailtrigger.mailer;

public class MailTemplate {

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String format = "html";

    private StringBuilder text = new StringBuilder();

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void clearText() {
        this.text = new StringBuilder();
    }

    public void appendText(String textLine) {
        if (this.text.length() > 0) {
            this.text.append("\n");
        }
        this.text.append(textLine);
    }

    public int getTextLength() {
        return this.text.length();
    }

    public String getTextAsString() {
        return this.text.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "MailTemplate[from=%s, to=%s, cc=%s, bcc=%s, subject=%s, text=%s]",
                from, to, cc, bcc, subject, text);
    }
}
