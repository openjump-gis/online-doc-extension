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

import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MenuNames;
import es.kosmo.desktop.utils.DesktopUtils;

/**
 * Opens the Kosmo Desktop manuals web page
 * <p>
 * </p>
 * 
 * @author Sergio Ba&ntilde;os Calvo - sbc@saig.es
 * @since 2.0 [Giuseppe Aruta 2017_11_28] Adapted to OpenJUMP Version 20171022
 *        snapshot rev.5522
 */
public class OpenWebDownloadablesPlugIn extends AbstractOpenWebSitePlugIn {

    @Override
    public void initialize(PlugInContext context) {
        context.getFeatureInstaller().addMainMenuPlugin(this,
                new String[] { MenuNames.HELP, DesktopUtils.MenuName },
                getName(), false, null,
                createEnableCheck(context.getWorkbenchContext()));
    }

    /** Plugin name */
    public final static String NAME = DesktopUtils.Downloadables;

    @Override
    public URL getURL() throws MalformedURLException {

        String url = "http://ojwiki.soldin.de/index.php?title=Downloadable_Tutorials_and_Guides";
        // String url = "http://www.opengis.es/index.php?option=com_docman&task=cat_view&gid=19&Itemid=42"; //$NON-NLS-1$

        return new URL(url);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
