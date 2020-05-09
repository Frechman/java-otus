package ru.gavrilov.annotation.processors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * сперва удалить объявление пакета `package ru.gavrilov.annotation.processors;` из каждого класса в пакете
 * javac AnnotationProcessor.java
 * javac -processor AnnotationProcessor DataClass.java
 *
 * -XprintRounds для просмотра циклов обработки аннотаций
 */

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes({
        "ClassAnnotation"
})
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println(roundEnv.toString());
        System.out.println("Annotations size: " + annotations.size());
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ClassAnnotation.class);

        System.out.println(
                "Total elements annotated with " + ClassAnnotation.class.getCanonicalName() + ": " + elements.size()
        );

        for (Element element : elements) {
            System.out.println(element.getSimpleName().toString());
        }
        return false;
    }
}
