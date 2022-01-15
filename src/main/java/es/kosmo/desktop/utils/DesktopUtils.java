/* 
 * Sistema Abierto de Información Geográfica
 * Kosmo - Open Geographical Information System
 *
 * http://www.saig.es
 * (C) 2013, SAIG S.L.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * For more information, contact:
 *
 * Sistemas Abiertos de Información Geográfica, S.L.
 * Avnda. República Argentina, 28
 * Edificio Domocenter Planta 2ª Oficina 7
 * C.P.: 41930 - Bormujos (Sevilla)
 * España / Spain
 *
 * Teléfono / Phone Number
 * +34 954 788876
 *
 * Correo electrónico / Email
 * info@saig.es
 *
 */
package es.kosmo.desktop.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.vividsolutions.jump.I18N;
import es.kosmo.desktop.plugins.help.HelpExtension;
import org.apache.log4j.Logger;

/**
 * Utility class for opening programs / urls
 * <p>
 * </p>
 * 
 * @author Sergio Ba&ntilde;os Calvo - sbc@saig.es
 * @since 2.1 [Giuseppe Aruta 2017_11_28] Adapted to OpenJUMP Version 20171022
 *        snapshot rev.5522
 */
public class DesktopUtils {

    private static final I18N i18n =
        I18N.getInstance("es.kosmo.desktop.plugins.help");

    /** Log */
    private final static Logger LOGGER = Logger.getLogger(DesktopUtils.class);

    private final static String OS_WINDOWS = "Windows";

    private final static String OS_X = "Mac OS X";//$NON-NLS-1$

    private static final String[] UNIX_BROWSE_CMDS = { "firefox", "opera",
            "konqueror", "epiphany", "mozilla", "netscape", "w3m", "lynx",
            "www-browser" };

    private static final String[] UNIX_OPEN_CMDS = { "run-mailcap", "pager",
            "less", "more" };

    /**
     * Open the given file using OS default application
     * 
     * @param file
     * @throws Exception
     */

    public static String MenuName = i18n.get("help.OnlineHelp");
    public static String OnlineHelp = i18n.get("help.OnlineHelp");
    public static String Documentation = i18n.get("help.Documentation");
    public static String Downloadables = i18n.get("help.Downloadables");
    public static String OnlineManual = i18n.get("help.OnlineManual");
    public static String Translate = i18n.get("help.Translate");
    public static String Tutorials = i18n.get("help.Tutorials");
    public static String Wiki = i18n.get("help.Wiki");

    public static void open(File file) throws Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        } else {
            final String osName = System.getProperty("os.name");
            // final String osName = System.getProperty("os.name");
            LOGGER.debug("Opening " + file + " for OS " + osName);

            if (osName.startsWith(OS_WINDOWS)) {
                openWindows(file);
            } else if (osName.startsWith(OS_X)) {
                openOSX(file);
            } else {
                // Assume we're in Linux
                openLinux(file);
            }
        }
    }

    /**
     * Browse the given url using OS default web browser
     * 
     * @param url
     * @throws Exception
     */
    public static void browse(URL url) throws Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(url.toURI());
        } else {
            final String osName = System.getProperty("os.name");
            LOGGER.debug("Launching " + url + " for OS " + osName);

            if (osName.startsWith(OS_WINDOWS)) {
                browseWindows(url);
            } else if (osName.startsWith(OS_X)) {
                browserOSX(url);

            } else {
                // Assume we're in Linux
                browseLinux(url);
            }
            /*
             * if (StringUtils.startsWithIgnoreCase(osName, OS_WINDOWS)) {
             * browseWindows(url); } else if
             * (StringUtils.startsWithIgnoreCase(osName, OS_X)) {
             * browserOSX(url);
             * 
             * } else { // Assume we're in Linux browseLinux(url); }
             */
        }
    }

    /**
     * Uses url.dll to browse to a URL under Windows.
     * 
     * @param url
     *            the URL to launch
     * @throws IOException
     *             if the launch failed
     */
    private static void browseWindows(final URL url) throws IOException {
        LOGGER.debug("Windows - Invoking rundll32");
        Runtime.getRuntime().exec(
                new String[] { "rundll32", "url.dll,FileProtocolHandler",
                        url.toString() });
    }

    private static void browserOSX(final URL url) throws IOException {

        LOGGER.debug("MacOSX opening web page");
        Runtime.getRuntime().exec(
                new String[] { "open", "-a", "Safari", url.toString() });
        return;

    }

    /**
     * Attempts to locate a browser from a predefined list under Unix.
     * 
     * @param url
     *            the URL to launch
     * @throws IOException
     *             if the launch failed
     */
    private static void browseLinux(final URL url) throws IOException {
        for (final String cmd : UNIX_BROWSE_CMDS) {
            LOGGER.debug("Linux -  Looking for " + cmd); //$NON-NLS-1$
            if (unixCommandExists(cmd)) {
                LOGGER.debug("Linux - Found " + cmd); //$NON-NLS-1$
                Runtime.getRuntime().exec(new String[] { cmd, url.toString() });
                return;
            }
        }
        throw new IOException("Could not find a suitable web browser"); //$NON-NLS-1$
    }

    /**
     * Checks whether a given executable exists, by means of the "which"
     * command.
     * 
     * @param cmd
     *            the executable to locate
     * @return true if the executable was found
     * @throws IOException
     *             if Runtime.exec() throws an IOException
     */
    private static boolean unixCommandExists(final String cmd)
            throws IOException {
        final Process whichProcess = Runtime.getRuntime().exec(
                new String[] { "which", cmd });

        boolean finished = false;
        do {
            try {
                whichProcess.waitFor();
                finished = true;
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted waiting for which to complete", e); //$NON-NLS-1$
            }
        } while (!finished);

        return whichProcess.exitValue() == 0;
    }

    /**
     * Uses shell32.dll to open a file under Windows.
     * 
     * @param file
     *            the File to open
     * @throws IOException
     *             if the open failed
     */
    private static void openWindows(final File file) throws IOException {
        LOGGER.debug("Windows invoking rundll32");
        Runtime.getRuntime().exec(
                new String[] { "rundll32", "shell32.dll,ShellExec_RunDLL",
                        file.getAbsolutePath() });
    }

    /**
     * Attempts to locate a viewer from a predefined list under Linux
     * 
     * @param file
     *            the File to open
     * @throws IOException
     *             if the open failed
     */
    private static void openLinux(final File file) throws IOException {
        for (final String cmd : UNIX_OPEN_CMDS) {
            LOGGER.debug("Linux - Looking for " + cmd); //$NON-NLS-1$
            if (unixCommandExists(cmd)) {
                LOGGER.debug("Linux - Found " + cmd);
                Runtime.getRuntime().exec(
                        new String[] { cmd, file.getAbsolutePath() });
                return;
            }
        }
        throw new IOException("Could not find a suitable viewer");
    }

    private static void openOSX(final File file) throws IOException {

        LOGGER.debug("MacOSX opening web page");
        Runtime.getRuntime()
                .exec(new String[] { "open", "-a", "Safari",
                        file.getAbsolutePath() });
        return;

    }

}
