package org.dddjava.jig.application.service;

import org.dddjava.jig.application.repository.JigSourceRepository;
import org.dddjava.jig.domain.model.jigdocument.implementation.ServiceMethodCallHierarchyDiagram;
import org.dddjava.jig.domain.model.jigdocument.implementation.StringComparingMethodList;
import org.dddjava.jig.domain.model.jigdocument.specification.RoundingPackageRelations;
import org.dddjava.jig.domain.model.jigdocument.stationery.JigLogger;
import org.dddjava.jig.domain.model.jigdocument.stationery.Warning;
import org.dddjava.jig.domain.model.jigmodel.controllers.ControllerMethods;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.relation.class_.ClassRelations;
import org.dddjava.jig.domain.model.jigmodel.repositories.DatasourceAngles;
import org.dddjava.jig.domain.model.jigmodel.repositories.DatasourceMethods;
import org.dddjava.jig.domain.model.jigmodel.services.ServiceAngles;
import org.dddjava.jig.domain.model.jigmodel.services.ServiceMethods;
import org.dddjava.jig.domain.model.jigsource.jigloader.MethodFactory;
import org.dddjava.jig.domain.model.jigsource.jigloader.analyzed.AnalyzedImplementation;
import org.dddjava.jig.domain.model.jigsource.jigloader.analyzed.TypeFacts;
import org.dddjava.jig.domain.model.jigsource.jigloader.architecture.Architecture;
import org.springframework.stereotype.Service;

/**
 * 機能の分析サービス
 */
@Service
public class ApplicationService {

    final Architecture architecture;
    final JigLogger jigLogger;
    final JigSourceRepository jigSourceRepository;

    public ApplicationService(Architecture architecture, JigLogger jigLogger, JigSourceRepository jigSourceRepository) {
        this.architecture = architecture;
        this.jigLogger = jigLogger;
        this.jigSourceRepository = jigSourceRepository;
    }

    /**
     * コントローラーを分析する
     */
    public ControllerMethods controllerAngles() {
        TypeFacts typeFacts = jigSourceRepository.allTypeFacts();
        ControllerMethods controllerMethods = MethodFactory.createControllerMethods(typeFacts, architecture);

        if (controllerMethods.empty()) {
            jigLogger.warn(Warning.ハンドラメソッドが見つからないので出力されない通知);
        }

        return controllerMethods;
    }

    public ServiceMethodCallHierarchyDiagram serviceMethodCallHierarchy() {
        ServiceAngles serviceAngles = serviceAngles();
        return new ServiceMethodCallHierarchyDiagram(serviceAngles.list());
    }

    /**
     * サービスを分析する
     */
    public ServiceAngles serviceAngles() {
        TypeFacts typeFacts = jigSourceRepository.allTypeFacts();
        ServiceMethods serviceMethods = MethodFactory.createServiceMethods(typeFacts, architecture);

        if (serviceMethods.empty()) {
            jigLogger.warn(Warning.サービスメソッドが見つからないので出力されない通知);
        }

        ControllerMethods controllerMethods = MethodFactory.createControllerMethods(typeFacts, architecture);
        DatasourceMethods datasourceMethods = MethodFactory.createDatasourceMethods(typeFacts, architecture);

        return new ServiceAngles(
                serviceMethods,
                typeFacts.toMethodRelations(),
                controllerMethods,
                datasourceMethods);
    }

    /**
     * データソースを分析する
     */
    public DatasourceAngles datasourceAngles() {
        TypeFacts typeFacts = jigSourceRepository.allTypeFacts();
        DatasourceMethods datasourceMethods = MethodFactory.createDatasourceMethods(typeFacts, architecture);

        if (datasourceMethods.empty()) {
            jigLogger.warn(Warning.リポジトリが見つからないので出力されない通知);
        }

        return new DatasourceAngles(datasourceMethods, jigSourceRepository.sqls());
    }

    /**
     * 文字列比較を分析する
     */
    public StringComparingMethodList stringComparing() {
        TypeFacts typeFacts = jigSourceRepository.allTypeFacts();
        ControllerMethods controllerMethods = MethodFactory.createControllerMethods(typeFacts, architecture);
        ServiceMethods serviceMethods = MethodFactory.createServiceMethods(typeFacts, architecture);

        return StringComparingMethodList.createFrom(controllerMethods, serviceMethods);
    }

    public RoundingPackageRelations buildingBlockRelations() {
        TypeFacts typeFacts = jigSourceRepository.allTypeFacts();
        ClassRelations classRelations = typeFacts.toClassRelations();

        return RoundingPackageRelations.getRoundingPackageRelations(classRelations);
    }
}
