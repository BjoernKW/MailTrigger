package com.bjoernkw.mailtrigger.mailer;

public class MailTemplate {

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String format = "html";

    private StringBuilder bodyText = new StringBuilder();

    private StringBuilder attachment = new StringBuilder();

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

    public void clearBodyText() {
        this.bodyText = new StringBuilder();
    }

    public void appendTextToBody(String line) {
        if (this.bodyText.length() > 0) {
            this.bodyText.append("\n");
        }
        this.bodyText.append(line);
    }

    public int getBodyTextLength() {
        return this.bodyText.length();
    }

    public String getBodyTextAsString() {
        return this.bodyText.toString();
    }

    public void clearAttachment() {
        this.attachment = new StringBuilder();
    }

    public void appendToAttachment(String line) {
        if (this.attachment.length() > 0) {
            this.attachment.append("\n");
        }
        this.attachment.append(line);
    }

    public int getAttachmentLength() {
        return this.attachment.length();
    }

    public String getAttachmentAsString() {
        return this.attachment.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "MailTemplate[from=%s, to=%s, cc=%s, bcc=%s, subject=%s, bodyText=%s]",
                from, to, cc, bcc, subject, bodyText
        );
    }
}
