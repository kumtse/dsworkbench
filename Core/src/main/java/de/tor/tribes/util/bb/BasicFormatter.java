/* 
 * Copyright 2015 Torridity.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tor.tribes.util.bb;

import de.tor.tribes.util.interfaces.BBFormatterInterface;
import de.tor.tribes.util.BBSupport;
import de.tor.tribes.util.GlobalOptions;
import java.text.NumberFormat;
import java.util.List;

/**
 *
 * @author Torridity
 */
public abstract class BasicFormatter<C extends BBSupport> implements BBFormatterInterface {

    private String sCustomTemplate = null;

    public BasicFormatter() {
        sCustomTemplate = getTemplate();
    }

    @Override
    public final void storeProperty() {
        GlobalOptions.addProperty(getPropertyKey(), sCustomTemplate);
    }

    public abstract String formatElements(List<C> pElements, boolean pExtended);

    @Override
    public final String getTemplate() {
        sCustomTemplate = GlobalOptions.getProperty(getPropertyKey());
        if (sCustomTemplate == null) {
            sCustomTemplate = getStandardTemplate();
        }
        return sCustomTemplate;
    }

    public boolean hasHeaderAndFooter() {
        String template = getTemplate();
        return template.indexOf(LIST_START) >= 0 && template.indexOf(LIST_END) >= 0;
    }

    public String getHeader() {
        String template = getTemplate();
        int listStart = template.indexOf(LIST_START);
        if (listStart < 0) {
            return "";
        }
        return template.substring(0, listStart);
    }

    /**
     * 
     * @return
     */
    public String getLineTemplate() {
        String template = getTemplate();
        if (!hasHeaderAndFooter()) {
            return template;
        }
        int listStart = template.indexOf(LIST_START);
        int listEnd = template.indexOf(LIST_END);
        return template.substring(listStart + LIST_START.length(), listEnd);
    }

    public String getFooter() {
        String template = getTemplate();
        int listEnd = template.indexOf(LIST_END);
        if (listEnd < 0) {
            return "";
        }
        return template.substring(listEnd + LIST_END.length());

    }

    public NumberFormat getNumberFormatter(int pDigits) {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(0);
        f.setMinimumFractionDigits(0);
        if (pDigits < 10) {
            f.setMaximumIntegerDigits(1);
        } else if (pDigits < 100) {
            f.setMaximumIntegerDigits(2);
        } else if (pDigits < 1000) {
            f.setMaximumIntegerDigits(3);
        } else if (pDigits < 10000) {
            f.setMaximumIntegerDigits(4);
        } else {
            f.setMaximumIntegerDigits(5);
        }
        return f;
    }

    @Override
    public final void setCustomTemplate(String pTemplate) {
        sCustomTemplate = pTemplate;
        storeProperty();
    }
}
