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
package es.kosmo.desktop.plugins.help;

import java.net.MalformedURLException;
import java.net.URL;

import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.workbench.JUMPWorkbench;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheck;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import es.kosmo.desktop.utils.DesktopUtils;

/**
 * Base class for opening a web site from a plugin
 * <p>
 * </p>
 * 
 * @author Sergio Ba&ntilde;os Calvo - sbc@saig.es
 * @since 2.0 [Giuseppe Aruta 2017_11_28] Adapted to OpenJUMP Version 20171022
 *        snapshot rev.5522
 */
public abstract class AbstractOpenWebSitePlugIn extends AbstractPlugIn {

    private static final I18N i18n = I18N.getInstance("es.kosmo.desktop.plugins.help");

    @Override
    public boolean execute(PlugInContext context) throws Exception {
        if (isValid(getURL())) {
            DesktopUtils.browse(getURL());
        } else {
            JUMPWorkbench
                .getInstance()
                .getFrame()
                .warnUser(
                    i18n.get("help.invalid-URL", getURL())
                );
        }

        return true;
    }

    /**
     * Returns the URL to open in the system configured browser
     * 
     * @throws MalformedURLException if URL is malformed
     */
    public abstract URL getURL() throws MalformedURLException;

    /**
     * Checks that the URL is correct
     * 
     * @param url an URL
     * @return true if url is valid
     */
    protected boolean isValid(URL url) {
        return true;
    }

    /**
     * Devuelve la comprobacion que debe cumplirse para poder activar el plugin
     * 
     * @param workbenchContext
     *            Contexto de la aplicacion
     * @return EnableCheck - Comprobacion que debe cumplir el plugin para poder
     *         activarse
     */
    public static EnableCheck createEnableCheck(
            WorkbenchContext workbenchContext) {
        EnableCheckFactory checkFactory =
            EnableCheckFactory.getInstance(workbenchContext);

        return new MultiEnableCheck().add(checkFactory
                .createWindowWithLayerViewPanelMustBeActiveCheck())
        // .add(checkFactory.createAtLeastNLayersMustBeEditableCheck(1))
        ;
    }

}
