package ru.otus.sua.L12.appSecure.hashing;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;

@Slf4j
public class AlgorithmProducer {

    @Produces
    @HashServiceType(HashType.SHA)
    @Sha
    public HashGenerator produceHashGenerator(InjectionPoint ip) {
        HashGenerator hashGenerator = null;
        for (Annotation annotation : ip.getAnnotated().getAnnotations()) {
            log.info(">> producer injection point annotation: {}",annotation.toString());
            if (annotation instanceof Sha) {
                Sha shaAnnotation = (Sha) annotation;
                hashGenerator = new GeneratorSHA(shaAnnotation.algorithm().getAlgorithmName());
                log.info(">> produced hash-generator for: {}",((GeneratorSHA) hashGenerator).algorithmName);
            }
        }
        return hashGenerator;
    }
}
