package org.dddjava.jig.gradle;

import org.dddjava.jig.application.service.ClassFindFailException;
import org.dddjava.jig.application.service.ImplementationService;
import org.dddjava.jig.domain.model.implementation.bytecode.TypeByteCodes;
import org.dddjava.jig.domain.model.implementation.datasource.Sqls;
import org.dddjava.jig.domain.model.implementation.raw.RawSource;
import org.dddjava.jig.domain.model.implementation.raw.RawSourceLocations;
import org.dddjava.jig.infrastructure.LocalProject;
import org.dddjava.jig.infrastructure.configuration.Configuration;
import org.dddjava.jig.infrastructure.configuration.JigProperties;
import org.dddjava.jig.presentation.view.JigDocument;
import org.dddjava.jig.presentation.view.handler.HandlerMethodArgumentResolver;
import org.dddjava.jig.presentation.view.handler.JigDocumentHandlers;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskAction;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JigReportsTask extends DefaultTask {

    @TaskAction
    void outputReports() {
        Project project = getProject();

        ExtensionContainer extensions = project.getExtensions();
        JigConfig config = extensions.findByType(JigConfig.class);

        JigProperties jigProperties = config.asProperties();
        RawSourceLocations rawSourceLocations = new GradleProject(project).allDependencyJavaProjects();
        JigConfigurationContext configurationContext = new JigConfigurationContext(config);
        Configuration configuration = new Configuration(jigProperties, configurationContext);

        JigDocumentHandlers jigDocumentHandlers = configuration.documentHandlers();

        List<JigDocument> jigDocuments = config.documentTypes();

        LocalProject localProject = configuration.localProject();
        ImplementationService implementationService = configuration.implementationService();
        Path outputDirectory = outputDirectory(config);

        long startTime = System.currentTimeMillis();

        getLogger().quiet("プロジェクト情報の取り込みをはじめます");
        try {
            RawSource source = localProject.createSource(rawSourceLocations);
            TypeByteCodes typeByteCodes = implementationService.readProjectData(source);
            Sqls sqls = implementationService.readSql(source.sqlSources());

            for (JigDocument jigDocument : jigDocuments) {
                getLogger().quiet("{} を出力します。", jigDocument);
                jigDocumentHandlers.handle(jigDocument, new HandlerMethodArgumentResolver(typeByteCodes, sqls), outputDirectory);
            }
        } catch (ClassFindFailException e) {
            getLogger().quiet(e.warning().text());
        }

        getLogger().quiet("合計時間: {} ms", System.currentTimeMillis() - startTime);
    }

    Path outputDirectory(JigConfig config) {
        Project project = getProject();
        Path path = Paths.get(config.getOutputDirectory());
        if (path.isAbsolute()) return path;

        return project.getBuildDir().toPath().resolve("jig");
    }
}
