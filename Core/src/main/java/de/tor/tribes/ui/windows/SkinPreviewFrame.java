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
package de.tor.tribes.ui.windows;

import de.tor.tribes.util.Skin;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author  Torridity
 */
public class SkinPreviewFrame extends javax.swing.JDialog {

    /** Creates new form SkinPreviewFrame */
    public SkinPreviewFrame(Skin pSkin) throws Exception {
        initComponents();
        File preview = new File(pSkin.getPreviewFile());
        if (!preview.exists()) {
            throw new Exception("Preview file does not exist");
        }
        ImageIcon icon = new ImageIcon(preview.toURI().toURL());
        setSize(icon.getIconWidth(), icon.getIconHeight());
        jLabel1.setSize(icon.getIconWidth(), icon.getIconHeight());
        jLabel1.setIcon(new ImageIcon(pSkin.getPreviewFile()));
        setAlwaysOnTop(true);
        pack();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setTitle("Vorschau");
        setAlwaysOnTop(true);
        setUndecorated(true);

        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fireHidePreviewEvent(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void fireHidePreviewEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fireHidePreviewEvent
    dispose();
}//GEN-LAST:event_fireHidePreviewEvent
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
