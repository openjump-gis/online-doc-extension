/* Copyright (2017) Giuseppe Aruta
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package es.kosmo.desktop.plugins.help;

import com.vividsolutions.jump.I18N;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.plugin.Extension;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.MenuNames;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;
import es.kosmo.desktop.utils.DesktopUtils;

public class HelpExtension extends Extension {

    private static final I18N i18n = I18N.getInstance("es.kosmo.desktop.plugins.help");

    @Override
    public String getName() {
        return "Open Help from web (Giuseppe Aruta - adapted from of Kosmo SAIG 3.0)";
    }

    @Override
    public String getVersion() {
        return "2.0.0 (2022-01-15)";
    }

    @Override
    public void configure(PlugInContext context) throws Exception {
        WorkbenchContext workbenchContext = context.getWorkbenchContext();
        FeatureInstaller featureInstaller =
            FeatureInstaller.getInstance(workbenchContext);
        new OpenWebWikiPlugIn().initialize(context);
        featureInstaller.addMenuSeparator(new String[] { MenuNames.HELP,
                DesktopUtils.MenuName });
        new OpenWebDocumentationPlugIn().initialize(context);
        new OpenWebOnlineManualPlugIn().initialize(context);
        new OpenWebTutorialsPlugIn().initialize(context);
        new OpenWebDownloadablesPlugIn().initialize(context);
        featureInstaller.addMenuSeparator(new String[] { MenuNames.HELP,
                DesktopUtils.MenuName });
        new OpenWebTranslatePlugIn().initialize(context);

    }
}
