/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 Oracle and/or its affiliates. All rights reserved.
 * Portions Copyright (c) 2011 Ken Paulsen
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above. However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.sun.jsft.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p> This class provides helper methods for logging messages. It uses standard J2SE logging. However, using these
 *     API's is abstracts this away from our code in case we want to go back to Apache commons logging or some other
 *     logging API in the future.</p>
 *
 * <p> The logging levels follow the J2SE log level names, they are as follows:</p>
 *     <ul><li>FINEST        -- Highly detailed tracing message</li>
 *         <li>FINER        -- Fairly detailed tracing message</li>
 *         <li>FINE        -- Coarse tracing message</li>
 *         <li>CONFIG        -- Static configuration messages</li>
 *         <li>INFO        -- Informational messages (logged by default)</li>
 *         <li>WARNING -- Potentially problematic messages</li>
 *         <li>SEVERE        -- Serious failure messages</li>
 *         </ul>
 *
 *  Created  March 29, 2011
 *  @author  Ken Paulsen (kenapaulsen@gmail.com)
 */
public final class LogUtil {

    /**
     * <p> Prevent direct instantiation.</p>
     */
    private LogUtil() {
        // Hide constructor
    }

    ////////////////////////////////////////////////////////////
    // FINEST LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return {@code true} if the log level is enabled, {@code false} otherwise.
     */
    public static boolean finestEnabled() {
        return getLogger().isLoggable(Level.FINEST);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean finestEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.FINEST);
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void finest(String msgId, Object... params) {
        getLogger().log(Level.FINEST, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability to
     *     specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void finest(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.FINEST, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg.</p>
     *
     * @param msg   The message (or <code>ResourceBundle</code> key).
     */
    public static void finest(String msg) {
        finest(DEFAULT_LOGGER, msg);
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message (or <code>ResourceBundle</code> key).
     */
    public static void finest(Object loggerId, String msg) {
        getLogger(loggerId).log(Level.FINEST, getMessage(msg, false));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message.</p>
     *
     * @param msg   The message.
     * @param ex    The <code>Throwable</code> to log.
     */
    public static void finest(String msg, Throwable ex) {
        getLogger().log(Level.FINEST,
                DEFAULT_LOG_KEY + LOG_KEY_MESSAGE_SEPARATOR + msg, ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message.
     * @param ex        The <code>Throwable</code> to log.
     */
    public static void finest(Object loggerId, String msg, Throwable ex) {
        getLogger(loggerId).log(Level.FINEST,
                DEFAULT_LOG_KEY + LOG_KEY_MESSAGE_SEPARATOR + msg, ex);
    }


    ////////////////////////////////////////////////////////////
    // FINER LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean finerEnabled() {
        return getLogger().isLoggable(Level.FINER);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean finerEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.FINER);
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId            The <code>ResourceBundle</code> key used to lookup
     *                            the message.
     *
     * @param params            Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void finer(String msgId, Object... params) {
        getLogger().log(Level.FINER, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability
     *     to specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void finer(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.FINER, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg.</p>
     *
     * @param msg   The message (or <code>ResourceBundle</code> key).
     */
    public static void finer(String msg) {
        finer(DEFAULT_LOGGER, msg);
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message (or <code>ResourceBundle</code> key).
     */
    public static void finer(Object loggerId, String msg) {
        getLogger(loggerId).log(Level.FINER, getMessage(msg, false));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message.</p>
     *
     * @param msg   The message.
     * @param ex    The <code>Throwable</code> to log.
     */
    public static void finer(String msg, Throwable ex) {
        getLogger().log(Level.FINER,
                DEFAULT_LOG_KEY + LOG_KEY_MESSAGE_SEPARATOR + msg, ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message.
     * @param ex        The <code>Throwable</code> to log.
     */
    public static void finer(Object loggerId, String msg, Throwable ex) {
        getLogger(loggerId).log(Level.FINER,
                DEFAULT_LOG_KEY + LOG_KEY_MESSAGE_SEPARATOR + msg, ex);
    }


    ////////////////////////////////////////////////////////////
    // FINE LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean fineEnabled() {
        return getLogger().isLoggable(Level.FINE);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean fineEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.FINE);
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use the
     *     default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void fine(String msgId, Object... params) {
        getLogger().log(Level.FINE, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability to
     *     specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void fine(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.FINE, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg.</p>
     *
     * @param msg   The message (or <code>ResourceBundle</code> key).
     */
    public static void fine(String msg) {
        fine(DEFAULT_LOGGER, msg);
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg. The specified Logger will
     *     be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message (or <code>ResourceBundle</code> key).
     */
    public static void fine(Object loggerId, String msg) {
        getLogger(loggerId).log(Level.FINE, getMessage(msg, false));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message.</p>
     *
     * @param msg   The message.
     * @param ex    The <code>Throwable</code> to log.
     */
    public static void fine(String msg, Throwable ex) {
        getLogger().log(Level.FINE, getMessage(msg, false), ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message.
     * @param ex        The <code>Throwable</code> to log.
     */
    public static void fine(Object loggerId, String msg, Throwable ex) {
        getLogger(loggerId).log(Level.FINE, getMessage(msg, false), ex);
    }

    ////////////////////////////////////////////////////////////
    // CONFIG LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean configEnabled() {
        return getLogger().isLoggable(Level.CONFIG);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean configEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.CONFIG);
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void config(String msgId, Object... params) {
        getLogger().log(Level.CONFIG, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability to
     *     specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void config(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.CONFIG, getMessage(msgId, params, false));
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg.</p>
     *
     * @param msg   The message (or <code>ResourceBundle</code> key).
     */
    public static void config(String msg) {
        config(DEFAULT_LOGGER, msg);
    }

    /**
     * <p> Logging method to log a simple localized or non-localized message. This method will first attempt to find
     *     <code>msg</code> in the properties file, if not found it will print the given msg. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message (or <code>ResourceBundle</code> key).
     */
    public static void config(Object loggerId, String msg) {
        getLogger(loggerId).log(Level.CONFIG, getMessage(msg, false));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message.</p>
     *
     * @param msg   The message.
     * @param ex    The <code>Throwable</code> to log.
     */
    public static void config(String msg, Throwable ex) {
        getLogger().log(Level.CONFIG, getMessage(msg, false), ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msg       The message.
     * @param ex        The <code>Throwable</code> to log.
     */
    public static void config(Object loggerId, String msg, Throwable ex) {
        getLogger(loggerId).log(Level.CONFIG, getMessage(msg, false), ex);
    }

    ////////////////////////////////////////////////////////////
    // INFO LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean infoEnabled() {
        return getLogger().isLoggable(Level.INFO);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean infoEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.INFO);
    }

    /**
     * <p> Logging method to log a simple localized message. The default Logger will be used.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(String msgId) {
        getLogger().log(Level.INFO, getMessage(msgId, true));
    }

    /**
     * <p> Logging method to log a simple localized message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(Object loggerId, String msgId) {
        getLogger(loggerId).log(Level.INFO, getMessage(msgId, true));
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(String msgId, Object... params) {
        getLogger().log(Level.INFO, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability to
     *     specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.INFO, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex    The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(String msgId, Throwable ex) {
        getLogger().log(Level.INFO, getMessage(msgId, false), ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex    The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void info(Object loggerId, String msgId, Throwable ex) {
        getLogger(loggerId).log(Level.INFO, getMessage(msgId, false), ex);
    }

    ////////////////////////////////////////////////////////////
    // WARNING LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean warningEnabled() {
        return getLogger().isLoggable(Level.WARNING);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean warningEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.WARNING);
    }

    /**
     * <p> Logging method to log a simple localized message. The default Logger will be used.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(String msgId) {
        getLogger().log(Level.WARNING, getMessage(msgId, true));
    }

    /**
     * <p> Logging method to log a simple localized message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(Object loggerId, String msgId) {
        getLogger(loggerId).log(Level.WARNING, getMessage(msgId, true));
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(String msgId, Object... params) {
        getLogger().log(Level.WARNING, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability
     *     to specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.WARNING, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex    The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(String msgId, Throwable ex) {
        getLogger().log(Level.WARNING, getMessage(msgId, false), ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex        The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void warning(Object loggerId, String msgId, Throwable ex) {
        getLogger(loggerId).log(Level.WARNING, getMessage(msgId, false), ex);
    }

    ////////////////////////////////////////////////////////////
    // SEVERE LOGGING METHODS
    ////////////////////////////////////////////////////////////

    /**
     * <p> Method to check if this log level is enabled for the default logger.</p>
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean severeEnabled() {
        return getLogger().isLoggable(Level.SEVERE);
    }

    /**
     * <p> Method to check if this log level is enabled for the given logger.</p>
     *
     * @param loggerId  The logger to check. This may be specified as a String or Class Object.
     *
     * @return true if the log level is enabled, false otherwise.
     */
    public static boolean severeEnabled(Object loggerId) {
        return getLogger(loggerId).isLoggable(Level.SEVERE);
    }

    /**
     * <p> Logging method to log a simple localized message. The default Logger will be used.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(String msgId) {
        getLogger().log(Level.SEVERE, getMessage(msgId, true));
    }

    /**
     * <p> Logging method to log a simple localized message. The specified Logger will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(Object loggerId, String msgId) {
        getLogger(loggerId).log(Level.SEVERE, getMessage(msgId, true));
    }

    /**
     * <p> Logging method supporting a localized message key and zero or more substitution parameters. It will use
     *     the default Logger.</p>
     *
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(String msgId, Object... params) {
        getLogger().log(Level.SEVERE, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method supporting a localized message key, zero or more substitution parameters, and the ability
     *     to specify the Logger.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param params    Value(s) to substitute into the message.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(Object loggerId, String msgId, Object... params) {
        getLogger(loggerId).log(Level.SEVERE, getMessage(msgId, params, true));
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message.</p>
     *
     * @param msgId The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex    The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(String msgId, Throwable ex) {
        getLogger().log(Level.SEVERE, getMessage(msgId, false), ex);
    }

    /**
     * <p> Logging method to log a <code>Throwable</code> with a localized message. The specified Logger
     *     will be used.</p>
     *
     * @param loggerId  The logger to use. This may be specified as a String or Class Object.
     * @param msgId     The <code>ResourceBundle</code> key used to lookup the message.
     * @param ex        The <code>Throwable</code> to log.
     *
     * @see LogUtil#BUNDLE_NAME
     */
    public static void severe(Object loggerId, String msgId, Throwable ex) {
        getLogger(loggerId).log(Level.SEVERE, getMessage(msgId, false), ex);
    }


    ////////////////////////////////////////////////////////////
    // Misc methods
    ////////////////////////////////////////////////////////////

    /**
     * <p> This method provides direct access to the default Logger. It is private because the internals of what type
     *     of logger is being used should not be exposed outside this class.</p>
     */
    private static Logger getLogger() {
        return DEFAULT_LOGGER;
    }

    /**
     * <p> This method provides direct access to the Logger. It is private because the internals of what type of
     *     logger is being used should not be exposed outside this class.</p>
     *
     * <p> This method will return the default logger (if INFO), or delegate to {@link #getLogger(String)} or
     *     {@link #getLogger(Class)}.</p>
     *
     * @param key   The logger to use as specified by the Object.
     */
    private static Logger getLogger(Object key) {
        // If null, use default
        if (key == null) {
            return getLogger();
        }

        // If string, use it as logger name
        if (key instanceof String) {
            return getLogger((String) key);
        }

        // If Log, return it
        if (key instanceof Logger) {
            return (Logger) key;
        }

        // If class, use it
        if (key instanceof Class) {
            return getLogger((Class) key);
        }

        // else, use the class name
        return getLogger(key.getClass());
    }

    /**
     * <p> This method provides direct access to the Logger. It is private because the internals of what type of
     *     logger is being used should not be exposed outside this class.</p>
     *
     * @param key   The logger to use as specified by the String.
     */
    private static Logger getLogger(String key) {
        if (key.length() == 0) {
            return DEFAULT_LOGGER;
        }
        return Logger.getLogger(key);
    }

    /**
     * <p> This method provides direct access to the Logger. It is private because the internals of what type of
     *     logger is being used should not be exposed outside this class.</p>
     *
     * @param key   The logger to use as specified by the Class.
     */
    private static Logger getLogger(Class key) {
        if (key == null) {
            return DEFAULT_LOGGER;
        }
        return Logger.getLogger(key.getName());
    }

    /**
     * <p> This method gets the appropriate message to display. It will attempt to resolve it from the
     *     <code>ResourceBundle</code>. If not found and it will either use the message id as the key, or it will
     *     return an error message depending on whether <code>strict</code> is true or false.</p>
     *
     * @param msgId     The message key.
     * @param strict    True if key not found should be an error.
     *
     * @return The message to write to the log file.
     */
    private static String getMessage(String msgId, boolean strict) {
        return getMessage(msgId, new Object[0], strict);
    }

    /**
     * <p> This method gets the appropriate message to display. It will attempt to resolve it from the
     *     <code>ResourceBundle</code>. If not found and it will either use the message id as the key, or it will
     *     return an error message depending on whether <code>strict</code> is true or false.</p>
     *
     * @param msgId     The message key.
     * @param params    The parameters.
     * @param strict    True if key not found should be an error.
     *
     * @return The message to write to the log file.
     */
    private static String getMessage(String msgId, Object[] params, boolean strict) {
        String result = MessageUtil.getInstance().
            getMessage(BUNDLE_NAME, msgId, params);
        if ((result == null) || result.equals(msgId)) {
            // We didn't find the key...
            if (strict) {
                // A key is required, return an error message
                if ((msgId != null) && msgId.equals(KEY_NOT_FOUND_KEY)) {
                    // This is here to prevent and infinite loop
                    result = KEY_NOT_FOUND_KEY + LOG_KEY_MESSAGE_SEPARATOR
                        + "'" + params[0] + "' not found in ResourceBundle: '"
                        + BUNDLE_NAME + "'";
                } else {
                    result = getMessage(
                            KEY_NOT_FOUND_KEY, new Object[] {msgId}, strict);
                }
            } else {
                // Use the msgId as the message, use the default key
                result = DEFAULT_LOG_KEY + LOG_KEY_MESSAGE_SEPARATOR + msgId;
            }
        } else {
            // We found the key, construct the log format...
            result = msgId + LOG_KEY_MESSAGE_SEPARATOR + result;
        }

        // Return the formatted result
        return result;
    }

    /**
     * <p> This is the bundle name for the <code>ResourceBundle</code> that contains all the message strings.</p>
     */
    public static final String        BUNDLE_NAME =
            "com.sun.jsft.LogMessages";

    /**
     * <p> This is the default log key.</p>
     */
    public static final String        DEFAULT_LOG_KEY = "JSFT0001";

    /**
     * <p> This is the default logger name.</p>
     */
    public static final String        DEFAULT_LOGGER_NAME =
            "com.sun.jsft";

    /**
     * <p> This key is used when the requested key is not found to inform the developer they forgot to add a key.</p>
     */
    public static final String KEY_NOT_FOUND_KEY    = "JSFT0002";


    /**
     * <p> The default Logger.</p>
     */
    private static final Logger DEFAULT_LOGGER = getLogger(DEFAULT_LOGGER_NAME);

    /**
     * <p> This is the separator between the the log key and log message.</p>
     */
    private static final String LOG_KEY_MESSAGE_SEPARATOR = ": ";
}
