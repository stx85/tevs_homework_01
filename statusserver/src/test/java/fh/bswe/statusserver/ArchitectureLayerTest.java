package fh.bswe.statusserver;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "fh.bswe.statusserver")
public class ArchitectureLayerTest {

    @ArchTest
    public static final Architectures.LayeredArchitecture layeredArchitecture =
            Architectures.layeredArchitecture()
                    .consideringOnlyDependenciesInLayers()

                    .layer("Controller").definedBy("..controller..")
                    .layer("Service").definedBy("..service..")
                    .layer("Repository").definedBy("..repository..")

                    .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                    .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

}
