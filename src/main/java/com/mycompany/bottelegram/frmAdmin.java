/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bottelegram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author sapyy
 */
public class frmAdmin extends javax.swing.JFrame {

    /**
     * Creates new form frmAdmin
     */
    Connection Con;
    ResultSet RsCommand,RsLog,RsUser;
    Statement stm;
    Boolean ada = false;
    Boolean edit=false;
    private Object[][] dataTable = null;
    private String[] header ={"id","Command","Response","Deskripsi"};
    private String[] headerUser ={"user_id","username","role"};
    private String[] headerLog ={"user_id","message","datetime","status"};
    public frmAdmin() throws TelegramApiException {
        initComponents();
        run_bot();
        open_db();
        baca_data();
        baca_dataUser();
        baca_dataLog();
        aktif(false);
        setTombol(true);
        startDataUpdateTimer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCommand = new javax.swing.JTextField();
        txtResponse = new javax.swing.JTextField();
        addCommand = new javax.swing.JButton();
        saveCommand = new javax.swing.JButton();
        deleteCommand = new javax.swing.JButton();
        updateCommand = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCommand = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBroadcast = new javax.swing.JTextField();
        btnBroadcast = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ListPesan = new javax.swing.JList<>();
        txtDesc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Response");

        jLabel2.setText("Command");

        txtCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCommandActionPerformed(evt);
            }
        });

        addCommand.setBackground(new java.awt.Color(0, 255, 51));
        addCommand.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        addCommand.setForeground(new java.awt.Color(255, 255, 255));
        addCommand.setText("Add Command");
        addCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCommandActionPerformed(evt);
            }
        });

        saveCommand.setBackground(new java.awt.Color(0, 204, 204));
        saveCommand.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        saveCommand.setForeground(new java.awt.Color(255, 255, 255));
        saveCommand.setText("Save");
        saveCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCommandActionPerformed(evt);
            }
        });

        deleteCommand.setBackground(new java.awt.Color(255, 51, 51));
        deleteCommand.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        deleteCommand.setForeground(new java.awt.Color(255, 255, 255));
        deleteCommand.setText("Hapus ");
        deleteCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCommandActionPerformed(evt);
            }
        });

        updateCommand.setBackground(new java.awt.Color(255, 204, 51));
        updateCommand.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        updateCommand.setForeground(new java.awt.Color(255, 255, 255));
        updateCommand.setText("Update");
        updateCommand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCommandActionPerformed(evt);
            }
        });

        cancel.setBackground(new java.awt.Color(255, 0, 51));
        cancel.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        cancel.setForeground(new java.awt.Color(242, 242, 242));
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel3.setText("Id");

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "username", "role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblUser);
        if (tblUser.getColumnModel().getColumnCount() > 0) {
            tblUser.getColumnModel().getColumn(0).setHeaderValue("id");
        }

        tblCommand.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Command", "Response", "Deskripsi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblCommand.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCommandMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCommand);

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Log Pesan");

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Broadcast");

        txtBroadcast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBroadcastActionPerformed(evt);
            }
        });

        btnBroadcast.setBackground(new java.awt.Color(0, 204, 0));
        btnBroadcast.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        btnBroadcast.setForeground(new java.awt.Color(255, 255, 255));
        btnBroadcast.setText("send");
        btnBroadcast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBroadcastActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Data Command");

        ListPesan.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(ListPesan);

        jLabel7.setText("Deskripsi");

        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel8.setText("Search Log");

        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Data Member");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBroadcast, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(68, 68, 68)
                                        .addComponent(btnBroadcast)))
                                .addGap(91, 91, 91))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtResponse, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCommand, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(77, 77, 77))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updateCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(deleteCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 917, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(241, 241, 241)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(416, 416, 416))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtResponse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCommand)
                            .addComponent(updateCommand))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveCommand)
                            .addComponent(deleteCommand))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancel)
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBroadcast, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBroadcast))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCommandActionPerformed
        // TODO add your handling code here:
         // TODO add your handling code here:
        edit=false;
        aktif(true);
        setTombol(false);
        kosong();
        
    }//GEN-LAST:event_addCommandActionPerformed

    private void saveCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCommandActionPerformed
        // TODO add your handling code here:
        String tRespon=txtResponse.getText();
        String tCommand=txtCommand.getText();
        String tDesc=txtDesc.getText();
        String tId=txtId.getText();
        
        try{
        if (edit==true)
        {
            stm.executeUpdate("update command set command='"+tCommand+"',response='"+tRespon+"',deskripsi='"+tDesc+"' where id='" + tId + "'");
        }else
        {
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            RsCommand = stm.executeQuery("select * from command where command='"+tCommand+"'");
            int baris = 0;
            while(RsCommand.next()) {
                baris++;
            }
            if(baris>0){
                JOptionPane.showMessageDialog(frmAdmin.this, "Command Sudah Ada","Gagal Login",JOptionPane.WARNING_MESSAGE);
            }
            else{
                stm.executeUpdate("INSERT into command VALUES(NULL,'"+tCommand+"','"+tRespon+"','"+tDesc+"')");
            }
            
        }
        tblCommand.setModel(new DefaultTableModel(dataTable,header));

        baca_data();
        aktif(false);
        setTombol(true);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_saveCommandActionPerformed

    private void deleteCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCommandActionPerformed
        // TODO add your handling code here:
        try{
            String sql="delete from command where id='" + txtId.getText()+ "'";
            stm.executeUpdate(sql);
            baca_data();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_deleteCommandActionPerformed

    private void updateCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCommandActionPerformed
        // TODO add your handling code here:
        edit=true;
        aktif(true);
        setTombol(false);
        txtId.setEditable(false);
    }//GEN-LAST:event_updateCommandActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        kosong();
        aktif(false);
        setTombol(true);
    }//GEN-LAST:event_cancelActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUserMouseClicked

    private void tblCommandMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCommandMouseClicked
        // TODO add your handling code here:
        setField();
    }//GEN-LAST:event_tblCommandMouseClicked

    private void txtBroadcastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBroadcastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBroadcastActionPerformed

    private void btnBroadcastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBroadcastActionPerformed
        // TODO add your handling code here:
        String broadcast = txtBroadcast.getText();
        BotTelegram bot = new BotTelegram();
        bot.broadcast(broadcast);
        txtBroadcast.setText("");
        
    }//GEN-LAST:event_btnBroadcastActionPerformed

    private void txtCommandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCommandActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommandActionPerformed

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased
        // TODO add your handling code here:
//        listSearch();
        if(txtsearch.getText().isEmpty()){
            ada = false;
        }else{
            ada = true;
        }
    }//GEN-LAST:event_txtsearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frmAdmin().setVisible(true);
                } catch (TelegramApiException ex) {
                    Logger.getLogger(frmAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListPesan;
    private javax.swing.JButton addCommand;
    private javax.swing.JButton btnBroadcast;
    private javax.swing.JButton cancel;
    private javax.swing.JButton deleteCommand;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton saveCommand;
    private javax.swing.JTable tblCommand;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtBroadcast;
    private javax.swing.JTextField txtCommand;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtResponse;
    private javax.swing.JTextField txtsearch;
    private javax.swing.JButton updateCommand;
    // End of variables declaration//GEN-END:variables

    private void run_bot(){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotTelegram bot = new BotTelegram();
            botsApi.registerBot(bot);
        } catch (TelegramApiException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void startDataUpdateTimer() {
        int delay = 1000; // Delay in milliseconds (adjust as needed)
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                baca_dataUser();
                baca_dataLog();
                baca_data();
            }
        };
        new Timer(delay, taskPerformer).start();
    }
    private void setField()
    {
        int row=tblCommand.getSelectedRow();
        txtId.setText((String)tblCommand.getValueAt(row,0));
        txtCommand.setText((String)tblCommand.getValueAt(row,1));
        txtResponse.setText((String)tblCommand.getValueAt(row,2));
        txtDesc.setText((String)tblCommand.getValueAt(row,3));
        
    }
    private void open_db() {
        try{
            KoneksiMysql kon = new KoneksiMysql("localhost","root","","chatbot");
            Con = kon.getConnection();
        //System.out.println("Berhasil ");
        }catch (Exception e) {
            System.out.println("Error : "+e);
        }
    }

    private void baca_data() {
        try{
            
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            RsCommand = stm.executeQuery("select * from command");
            ResultSetMetaData meta = RsCommand.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while(RsCommand.next()) {
                baris = RsCommand.getRow();
            }
            dataTable = new Object[baris][col];
            int x = 0;
            RsCommand.beforeFirst();
            while(RsCommand.next()) {
                dataTable[x][0] = RsCommand.getString("id");
                dataTable[x][1] = RsCommand.getString("command");
                dataTable[x][2] = RsCommand.getString("response");
                dataTable[x][3] = RsCommand.getString("deskripsi");
                
                x++;
            }
            
            
            tblCommand.setModel(new DefaultTableModel(dataTable,header));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void baca_dataUser() {
        try{
            
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            RsCommand = stm.executeQuery("select * from users where role='member'");
            ResultSetMetaData meta = RsCommand.getMetaData();
            int col = meta.getColumnCount();
            int baris = 0;
            while(RsCommand.next()) {
                baris = RsCommand.getRow();
            }
            dataTable = new Object[baris][col];
            int x = 0;
            RsCommand.beforeFirst();
            while(RsCommand.next()) {
                dataTable[x][0] = RsCommand.getInt("user_id");
                dataTable[x][1] = RsCommand.getString("username");
                dataTable[x][2] = RsCommand.getString("role");
                
                x++;
            }
            tblUser.setModel(new DefaultTableModel(dataTable,headerUser));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void baca_dataLog() {
        try{
            
            DefaultListModel<String> listModel = new DefaultListModel<>();
            String search = txtsearch.getText();
            stm = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            if(!ada){
                RsLog = stm.executeQuery("select m.id_user,m.message,m.datetime,m.status,u.user_id,u.username from message m join users u on u.user_id=m.id_user where u.role='member' order by m.datetime desc");
            }else{
                RsLog= stm.executeQuery("select m.id_user,m.message,m.datetime,m.status,u.user_id,u.username from message m join users u on u.user_id=m.id_user where u.role='member' and (m.id_user LIKE '%"+search+"%' OR m.message LIKE '%"+search+"%' OR u.username LIKE '%"+search+"%'  OR u.username LIKE '%"+search+"%')  order by m.datetime desc");
            }
            int x = 0;
            RsLog.beforeFirst();
            while(RsLog.next()) {

                String Name = "";
                if(RsLog.getString("status").equals("masuk")){
                    Name = RsLog.getString("username");
                }   
                else{
                    Name = "Lonely Bot";
                }
                listModel.addElement(RsLog.getString("datetime")+" || "+Name+" : "+RsLog.getString("message"));
                x++;
            }
    // 
                ListPesan.setModel(listModel);      
//            tblLog.setModel(new DefaultTableModel(dataTable,headerLog));
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        


    private void kosong(){
        txtId.setText("");
        txtCommand.setText("");
        txtResponse.setText("");
        txtDesc.setText("");
    }
    //mengset aktif tidak isian data
    private void aktif(boolean x)
    {
        txtCommand.setEditable(x);
        txtResponse.setEditable(x);
        txtDesc.setEditable(x);
        
    }
    //mengset tombol on/off
    private void setTombol(boolean t)
    {
        addCommand.setEnabled(t);
        saveCommand.setEnabled(!t);
        deleteCommand.setEnabled(t);
        updateCommand.setEnabled(t);
        cancel.setEnabled(!t);
    }

    private void execute(SendMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
