package io.foodtechlab.rcore.spring;

import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.event.driven.EventHandler;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Setter
@Log
@SuppressWarnings("SpellCheckingInspection")
class RCoreRegistar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware, PriorityOrdered {
    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = Objects.requireNonNull(metadata.getAnnotationAttributes(EnableRCoreSpring.class.getName()));

        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner(attrs);
        long start = System.currentTimeMillis();
        for (String basePackage : getBasePackages(attrs, metadata.getClassName())) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        log.info("Scanning taking " + (System.currentTimeMillis() - start) + "");
        start = System.currentTimeMillis();
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (alreadyExist(registry, candidateComponent)) // Не будем регать кейс если он у нас где-то в конфиге прописан
                continue;

            String beanClassName = getDefinitionClassName(candidateComponent);
            registry.registerBeanDefinition(beanClassName, candidateComponent);
        }
        log.info("Register definition taking " + (System.currentTimeMillis() - start));
    }


    protected ClassPathScanningCandidateComponentProvider getScanner(Map<String, Object> attrs) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false, this.environment);

        scanner.addIncludeFilter(new AssignableTypeFilter(UseCase.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(EventHandler.class));

        for (Class<?> toBean : ((Class<?>[]) attrs.get("anotherDomainBeans"))) {
            scanner.addIncludeFilter(new AssignableTypeFilter(toBean));
        }

        scanner.setResourceLoader(this.resourceLoader);

        return scanner;
    }


    protected Set<String> getBasePackages(Map<String, Object> attributes, String className) {
        Set<String> basePackages = new HashSet<>();
        for (String pkg : (String[]) attributes.get("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(className));
        }
        return basePackages;
    }

    /**
     * Небольшой костыль, не будет регистрировать бины, которые уже определенны в спринге
     */
    private boolean alreadyExist(BeanDefinitionRegistry registry, BeanDefinition definition) {
        String[] candidates = registry.getBeanDefinitionNames();
        for (String candidate : candidates) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
            if (isSameClass(beanDefinition, definition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameClass(BeanDefinition d1, BeanDefinition d2) {
        return Objects.equals(getDefinitionClassName(d1), getDefinitionClassName(d2));
    }

    private String getDefinitionClassName(BeanDefinition definition) {
        String className = definition.getBeanClassName();

        if (className != null)
            return className;

        if (definition instanceof AnnotatedBeanDefinition) { // definition with @Bean Annotation cause this issue
            MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) definition).getFactoryMethodMetadata();
            if (factoryMethodMetadata != null) {
                className = factoryMethodMetadata.getReturnTypeName();
            }
        }

        return className;
    }

    // Выполним после того, как прочекаем все остальные конфиги
    // Чтобы этот регистер импортировал бины, которые НЕ ОПРЕДЕЛЕННЫ вручную
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
