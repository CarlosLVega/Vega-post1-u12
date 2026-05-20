package com.empresa.pedidos.arquitectura;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

class ArquitecturaTest {

    private final JavaClasses clases = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.empresa.pedidos");

    @Test
    void capaAplicacionNoDependeDeInfraestructuraNiAdaptadores() {
        noClasses()
                .that().resideInAPackage("..aplicacion..")
                .should().dependOnClassesThat().resideInAnyPackage("..infraestructura..", "..adaptadores..")
                .check(clases);
    }

    @Test
    void controladorSoloDependeDeFacadeComoServicioDePedidos() {
        classes()
                .that().resideInAPackage("..adaptadores.rest..")
                .should().onlyDependOnClassesThat().resideInAnyPackage(
                        "..adaptadores.rest..",
                        "..adaptadores.facade..",
                        "..dominio..",
                        "java..",
                        "org.springframework..")
                .check(clases);
    }
}
