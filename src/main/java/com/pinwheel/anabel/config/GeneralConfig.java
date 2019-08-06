package com.pinwheel.anabel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * General configuration.
 *
 * @version 1.0.0
 */
@Configuration
public class GeneralConfig {
    /**
     * Returns instance of {@link DocumentBuilder} bean.
     *
     * @return {@link DocumentBuilder} bean.
     * @throws ParserConfigurationException
     */
    @Bean
    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Returns instance of {@link XPath} bean.
     *
     * @return {@link XPath} bean.
     */
    @Bean
    public XPath getXPath() {
        return XPathFactory.newInstance().newXPath();
    }
}
