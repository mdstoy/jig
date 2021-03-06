package org.dddjava.jig.gradle;

import org.dddjava.jig.domain.model.jigdocument.documentformat.JigDiagramFormat;
import org.dddjava.jig.domain.model.jigdocument.documentformat.JigDocument;
import org.dddjava.jig.domain.model.jigdocument.stationery.LinkPrefix;
import org.dddjava.jig.infrastructure.configuration.JigProperties;
import org.dddjava.jig.infrastructure.configuration.OutputOmitPrefix;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class JigConfig {

    String modelPattern = "";

    List<String> documentTypes = new ArrayList<>();

    String outputDirectory = "";

    String outputOmitPrefix = ".+\\.(service|domain\\.(model|type))\\.";

    boolean enableDebugDocument = false;

    String linkPrefix = LinkPrefix.disable().textValue();

    List<JigDocument> documentTypes() {
        if (documentTypes.isEmpty()) return JigDocument.canonical();
        return documentTypes.stream()
                .map(JigDocument::valueOf)
                .collect(Collectors.toList());
    }

    public JigProperties asProperties() {
        return new JigProperties(
                new OutputOmitPrefix(outputOmitPrefix),
                modelPattern,
                new LinkPrefix(linkPrefix),
                Paths.get(this.outputDirectory),
                JigDiagramFormat.SVG
        );
    }

    public String getModelPattern() {
        return modelPattern;
    }

    public void setModelPattern(String modelPattern) {
        this.modelPattern = modelPattern;
    }

    public List<String> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<String> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        if (!Paths.get(outputDirectory).isAbsolute()) {
            throw new IllegalArgumentException("outputDirectoryは絶対パスを指定してください");
        }
        this.outputDirectory = outputDirectory;
    }

    public String getOutputOmitPrefix() {
        return outputOmitPrefix;
    }

    public void setOutputOmitPrefix(String outputOmitPrefix) {
        this.outputOmitPrefix = outputOmitPrefix;
    }

    public boolean isEnableDebugDocument() {
        return enableDebugDocument;
    }

    public void setEnableDebugDocument(boolean enableDebugDocument) {
        this.enableDebugDocument = enableDebugDocument;
    }

    public String getLinkPrefix() {
        return linkPrefix;
    }

    public void setLinkPrefix(String linkPrefix) {
        this.linkPrefix = linkPrefix;
    }

    public String propertiesText() {
        return new StringJoiner("\n\t", "jig {\n\t", "\n}")
                .add("modelPattern = '" + modelPattern + '\'')
                .add("documentTypes = '" + documentTypes + '\'')
                .add("outputDirectory = '" + outputDirectory + '\'')
                .add("outputOmitPrefix = '" + outputOmitPrefix + '\'')
                .add("enableDebugDocument = " + enableDebugDocument)
                .add("linkPrefix = '" + linkPrefix + '\'')
                .toString();
    }
}
