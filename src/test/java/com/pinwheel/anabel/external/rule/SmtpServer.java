package com.pinwheel.anabel.external.rule;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import lombok.Getter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import javax.mail.internet.MimeMessage;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Getter
public class SmtpServer implements BeforeEachCallback, AfterEachCallback {
    private GreenMail server;
    private int port;

    public SmtpServer(int port) {
        this.port = port;
    }

    public MimeMessage[] getMessages() {
        return server.getReceivedMessages();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        server = new GreenMail(new ServerSetup(port, null, "smtp"));
        server.start();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        server.stop();
    }
}
