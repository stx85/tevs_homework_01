package fh.bswe.statusserver;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

@AnalyzeClasses(packages = "fh.bswe.statusserver")
public class ArchitectureTest {
    @ArchTest
    public static final ArchRule repositoryShouldEndWithRepository =
            ArchRuleDefinition.classes()
                    .that()
                    .resideInAPackage("..repository..")
                    .and().areNotAnonymousClasses()
                    .should()
                    .haveSimpleNameEndingWith("Repository");

    @ArchTest
    public static final ArchRule repositoryShouldBeInRepositoryPackage =
            ArchRuleDefinition.classes()
                    .that()
                    .haveSimpleNameEndingWith("Repository")
                    .should()
                    .resideInAPackage("..repository..");

    @ArchTest
    public static final ArchRule serviceShouldEndWithService =
            ArchRuleDefinition.classes()
                    .that()
                    .resideInAPackage("..service..")
                    .and().areNotAnonymousClasses()
                    .should()
                    .haveSimpleNameEndingWith("Service");

    @ArchTest
    public static final ArchRule serviceShouldBeInServicePackage =
            ArchRuleDefinition.classes()
                    .that()
                    .haveSimpleNameEndingWith("Service")
                    .should()
                    .resideInAPackage("..service..");

    @ArchTest
    public static final ArchRule controllerShouldEndWithController =
            ArchRuleDefinition.classes()
                    .that()
                    .resideInAPackage("..controller..")
                    .and().areNotAnonymousClasses()
                    .should()
                    .haveSimpleNameEndingWith("Controller");

    @ArchTest
    public static final ArchRule controllerShouldBeInControllerPackage =
            ArchRuleDefinition.classes()
                    .that()
                    .haveSimpleNameEndingWith("Controller")
                    .should()
                    .resideInAPackage("..controller..");

    @ArchTest
    public static final ArchRule controllerNotAllowedToDependOnRepository =
            ArchRuleDefinition.noClasses()
                    .that()
                    .resideInAPackage("..controller..")
                    .should()
                    .dependOnClassesThat()
                    .resideInAPackage("..repository..");

    @ArchTest
    public static final ArchRule serviceOnlyHaveDepententInControllerAndService =
            ArchRuleDefinition.classes()
                    .that()
                    .resideInAPackage("..service..")
                    .should()
                    .onlyHaveDependentClassesThat()
                    .resideInAnyPackage("..controller..", "..service..", "..kafka.consumer..");

    @ArchTest
    public static final ArchRule noCyclesBetweenPackages =
            SlicesRuleDefinition.slices().matching("fh.bswe.statusserver.(*)..").should().beFreeOfCycles();


}
