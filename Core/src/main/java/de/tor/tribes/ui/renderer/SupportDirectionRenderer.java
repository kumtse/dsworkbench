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
package de.tor.tribes.ui.renderer;

import de.tor.tribes.types.SupportType;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.renderer.DefaultTableRenderer;

/**
 *
 * @author Torridity
 */
public class SupportDirectionRenderer extends DefaultTableRenderer {

    private ImageIcon inc = null;
    private ImageIcon out = null;

    public SupportDirectionRenderer() {
        inc = new ImageIcon(SupportDirectionRenderer.class.getResource("/res/ui/move_in.png"));
        out = new ImageIcon(SupportDirectionRenderer.class.getResource("/res/ui/move_out.png"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JLabel label = ((JLabel) c);

        try {
            label.setText("");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            SupportType.DIRECTION dir = (SupportType.DIRECTION) value;
            switch (dir) {
                case INCOMING:
                    label.setIcon(inc);
                    label.setToolTipText("Eingehend");
                    break;
                case OUTGOING:
                    label.setIcon(out);
                    label.setToolTipText("Ausgehend");
                    break;
            }
        } catch (Exception e) {
        }
        return label;
    }
}
