package com.pinwheel.anabel.service.notification.factory;

import com.pinwheel.anabel.service.notification.domain.NotificationMessage;
import com.pinwheel.anabel.service.notification.exception.IllegalNotificationStateException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Creates and returns ready-made notification message objects. The name of notification alert is determined based on
 * the name of the method. The method returning ready notifications should begin with {@code prefix} property of this
 * class. The name of the method should be in camel-case.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Getter
@Setter
public abstract class NotificationMessageFactory {
    /**
     * Logger.
     */
    private Logger logger = LoggerFactory.getLogger(EmailNotificationMessageFactory.class);

    /**
     * Prefix for finding factory methods.
     */
    protected String prefix = "create";

    /**
     * Map of factory methods. Key is name of method without prefix.
     */
    protected Map<String, Method> prototypes = new HashMap<>();

    /**
     * Returned notification messages will be cached by this class. After next calling factory method with the same
     * name, value will be returned from cache.
     */
    protected Map<String, Map<String, NotificationMessage>> cache = new HashMap<>();

    /**
     * Init method for population prototypes map. Finds all methods, which names start with {@code prefix} property of
     * this class.
     */
    @PostConstruct
    public void init() {
        this.prototypes = Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(this::isFactoryMethod)
                .filter(method -> method.getReturnType().equals(NotificationMessage.class))
                .collect(Collectors.toMap(
                        this::getFactoryMethodName,
                        method -> method
                ));
    }

    /**
     * Determines target factory method and invoke this. If name already has been user, notification message will be
     * returned from cache.
     *
     * @param name name of notification method.
     * @return Ready-made notification message.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public NotificationMessage create(String name, boolean useCache, Object... args) {
        name = name.toLowerCase();

        if (!prototypes.containsKey(name)) {
            throw new IllegalNotificationStateException();
        }

        String clazz = this.getClass().getName();
        Map<String, NotificationMessage> factoryCache;

        if (cache.containsKey(clazz)) {
            factoryCache = cache.get(clazz);
        } else {
            factoryCache = new HashMap<>();
            cache.put(clazz, factoryCache);
        }

        if (useCache && factoryCache.containsKey(name)) {
            return factoryCache.get(name);
        }

        Method method = prototypes.get(name);
        method.setAccessible(true);

        try {
            NotificationMessage message = (NotificationMessage) method.invoke(this, args);

            factoryCache.put(name, message);

            return message;
        } catch (ReflectiveOperationException e) {
            logger.error("An error occurred resolve factory create method", e);
            throw new IllegalNotificationStateException(e);
        }
    }

    public NotificationMessage create(String name, Object... args) {
        return create(name, false, args);
    }

    /**
     * Determines factory method name part, that follows after {@code prefix} property of this class.
     *
     * @param method factory method.
     * @return target method name without prefix in lower case.
     */
    protected String getFactoryMethodName(Method method) {
        return method.getName().substring(this.prefix.length()).toLowerCase();
    }

    /**
     * Determines whether method is factory method.
     *
     * @param method method for invoking.
     * @return whether method is factory method.
     */
    protected boolean isFactoryMethod(Method method) {
        return method.getName().length() > prefix.length() && method.getName().indexOf(prefix) == 0;
    }
}
