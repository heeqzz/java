/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mavenproject2;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import java.io.*;
/**
 *
 * @author 1
 */

public class NewJFrame extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewJFrame.class.getName());

    public NewJFrame() {
        initComponents();
        
    }
    private static LinkedList<RecIntegral> collection = new LinkedList<>();
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        label4 = new java.awt.Label();
        textField1 = new java.awt.TextField();
        textField2 = new java.awt.TextField();
        textField3 = new java.awt.TextField();
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        button3 = new java.awt.Button();
        label1 = new java.awt.Label();
        write = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        butWrite = new javax.swing.JButton();
        butRead = new javax.swing.JButton();
        butWriteSer = new javax.swing.JButton();
        butReadSer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label2.setAlignment(java.awt.Label.CENTER);
        label2.setName(""); // NOI18N
        label2.setText("Верхний порог");

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label3.setName(""); // NOI18N
        label3.setText("Нижний порог");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(183, 186, 218));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Верхний порог", "Нижний порог", "Шаг", "Результат"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        label4.setText("sqrt(x)");

        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });

        textField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField2ActionPerformed(evt);
            }
        });

        textField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField3ActionPerformed(evt);
            }
        });

        button1.setActionCommand("Добавить");
        button1.setLabel("Добавить");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setLabel("Удалить");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setLabel("Рассчитать");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setName(""); // NOI18N
        label1.setText("Шаг");

        write.setText("записать");
        write.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                writeActionPerformed(evt);
            }
        });

        clear.setText("очистить");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        butWrite.setText("зап в файл");
        butWrite.setToolTipText("");
        butWrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butWriteActionPerformed(evt);
            }
        });

        butRead.setText("заг из файла");
        butRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butReadActionPerformed(evt);
            }
        });

        butWriteSer.setText("зап в сер файл");
        butWriteSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butWriteSerActionPerformed(evt);
            }
        });

        butReadSer.setText("заг из сер файла");
        butReadSer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butReadSerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(label3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(textField3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                        .addComponent(textField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(210, 210, 210))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(write))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(clear)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(butRead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(butWrite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(butReadSer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(butWriteSer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butWrite)
                            .addComponent(butWriteSer)
                            .addComponent(write))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butRead)
                            .addComponent(butReadSer)
                            .addComponent(clear))
                        .addGap(51, 51, 51)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField1ActionPerformed

    private void textField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField2ActionPerformed

    private void textField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField3ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
       try {
            double upper = Double.parseDouble(textField1.getText());
            double lower = Double.parseDouble(textField2.getText());
            double steps = Double.parseDouble(textField3.getText());
            
            RecIntegral record = new RecIntegral(upper, lower, steps);
            collection.add(record);

            // Добавляем строку в таблицу
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.addRow(new Object[]{upper, lower, steps, ""});
            
            // Очистка полей ввода
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            
        }catch (RecException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + " (" + ex.getNumber() + ")", 
                "Ошибка диапазона", JOptionPane.ERROR_MESSAGE);
        }catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Введите корректные числа!", "Ошибка формата", JOptionPane.ERROR_MESSAGE);
        }catch (RecException2 ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + " (" + ex.getNumber() + ")", 
                "Ошибка шага", JOptionPane.ERROR_MESSAGE);
        }catch (RecException3 ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + " (" + ex.getNumber()+'&' + ex.getNumber2()+ ")", 
                "Ошибка верхнего предела", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        
        int selectedRow = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        collection.remove(selectedRow);
        model.removeRow(selectedRow);
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed

        int selectedRow = jTable1.getSelectedRow();
        try {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // Читаем все значения из выделенной строки таблицы
        double upper = Double.parseDouble(model.getValueAt(selectedRow, 0).toString());
        double lower = Double.parseDouble(model.getValueAt(selectedRow, 1).toString());
        double step  = Double.parseDouble(model.getValueAt(selectedRow, 2).toString());
        
        // Создаём новые объект с обновлёнными данными
        RecIntegral newRecord = new RecIntegral(upper, lower, step);
        
        // Считаем результат
        double result = newRecord.calculateIntegralTrapezoidal();
        newRecord.setRes(result);
        
        // заменяем старый объект в коллекции на новый (по тому же индексу)
        collection.set(selectedRow, newRecord);
        
        // Обновляем ячейку результата в таблице
        model.setValueAt(String.format("%.6f", result), selectedRow, 3);
        
    } catch (Exception ex) {
        // При ошибке пишем "Ошибка" в ячейку результата выделенной строки
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setValueAt("Ошибка", selectedRow, 3);
    }
    }//GEN-LAST:event_button3ActionPerformed

    private void writeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_writeActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        //Очищаем таблицу перед загрузкой данных
        model.setRowCount(0);
        //Проходим по каждому объекту в коллекции
        for (RecIntegral record : collection) {
        // Порядок колонок: Верхний порог, Нижний порог, Шаг, Результат
        Object[] rowData = new Object[]{
            record.getUpp(), 
            record.getDown(), 
            record.getStep(), 
            record.getRes() != null ? String.format("%.6f", record.getRes()) : ""
        };
        
        // 5. Добавляем строку в таблицу
        model.addRow(rowData);
    }
    }//GEN-LAST:event_writeActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);  // Удаляет все строки сразу
    }//GEN-LAST:event_clearActionPerformed
    
    private void butWriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butWriteActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранение в текстовый файл");
        fileChooser.setSelectedFile(new File("integral_data.txt"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Текстовые файлы (*.txt)", "txt"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                // Заголовок (опционально)
                writer.println("#Верхний;Нижний;Шаг;Результат");

                for (RecIntegral record : collection) {
                    String res = (record.getRes() != null) 
                        ? String.format("%.6f", record.getRes()) 
                        : "";
                    writer.printf("%.6f;%.6f;%.6f;%s%n", 
                        record.getUpp(), record.getDown(), record.getStep(), res);
                }
                JOptionPane.showMessageDialog(this, "Данные успешно сохранены!");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Ошибка записи в файл: " + ex.getMessage(), 
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
                logger.log(java.util.logging.Level.SEVERE, "File write error", ex);
            }
        }
    }//GEN-LAST:event_butWriteActionPerformed

    private void butReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butReadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Загрузка из текстового файла");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "Текстовые файлы (*.txt)", "txt"));
    
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            collection.clear(); // Очищаем текущую коллекцию
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Очищаем таблицу
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Пропускаем комментарии
                
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    double upp = Double.parseDouble(parts[0]);
                    double down = Double.parseDouble(parts[1]);
                    double step = Double.parseDouble(parts[2]);
                    Double res = (parts.length > 3 && !parts[3].isEmpty()) 
                        ? Double.parseDouble(parts[3]) : null;
                    
                    RecIntegral record = new RecIntegral(upp, down, step);
                    if (res != null) record.setRes(res);
                    
                    collection.add(record);
                    model.addRow(new Object[]{upp, down, step, 
                        res != null ? String.format("%.6f", res) : ""});
                }
            }
            JOptionPane.showMessageDialog(this, "Данные успешно загружены!");
            
        } catch (IOException | NumberFormatException | RecException | RecException2 | RecException3 ex) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка чтения файла: " + ex.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "File read error", ex);
        }
    }
    }//GEN-LAST:event_butReadActionPerformed

    private void butWriteSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butWriteSerActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Сохранение в двоичный файл");
    fileChooser.setSelectedFile(new File("integral_data.ser"));
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "Сериализованные файлы (*.ser)", "ser"));
    
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(collection); // Сохраняем ВСЮ коллекцию одним объектом
            JOptionPane.showMessageDialog(this, "Данные успешно сериализованы!");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка сериализации: " + ex.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Serialization error", ex);
        }
    }
    }//GEN-LAST:event_butWriteSerActionPerformed

    private void butReadSerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butReadSerActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Загрузка из двоичного файла");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "Сериализованные файлы (*.ser)", "ser"));
    
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Загружаем коллекцию и приводим к нужному типу
            collection = (LinkedList<RecIntegral>) ois.readObject();
            
            // Обновляем таблицу
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            for (RecIntegral record : collection) {
                model.addRow(new Object[]{
                    record.getUpp(), 
                    record.getDown(), 
                    record.getStep(), 
                    record.getRes() != null ? String.format("%.6f", record.getRes()) : ""
                });
            }
            JOptionPane.showMessageDialog(this, "Данные успешно загружены!");
            
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка десериализации: " + ex.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.log(java.util.logging.Level.SEVERE, "Deserialization error", ex);
        }
    }
    }//GEN-LAST:event_butReadSerActionPerformed
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NewJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butRead;
    private javax.swing.JButton butReadSer;
    private javax.swing.JButton butWrite;
    private javax.swing.JButton butWriteSer;
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Button button3;
    private javax.swing.JButton clear;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.TextField textField1;
    private java.awt.TextField textField2;
    private java.awt.TextField textField3;
    private javax.swing.JButton write;
    // End of variables declaration//GEN-END:variables
}
