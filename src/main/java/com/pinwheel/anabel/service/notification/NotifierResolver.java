package com.pinwheel.anabel.service.notification;

/**
 * Notifier resolver interface. Describes behavior of resolving target {@link Notifier notifier} by passed notifier
 * name.
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
interface NotifierResolver {
    /**
     * Resolvers target {@link Notifier notifier} by passed notifier
     * name.
     *
     * @param notifierName notifier name.
     * @return resolved notifier.
     */
    Notifier resolve(String notifierName);
}
