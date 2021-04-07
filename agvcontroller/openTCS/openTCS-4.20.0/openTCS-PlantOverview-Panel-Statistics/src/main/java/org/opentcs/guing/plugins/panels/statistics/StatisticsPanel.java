/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.guing.plugins.panels.statistics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.opentcs.components.plantoverview.PluggablePanel;
import static org.opentcs.guing.plugins.panels.statistics.I18nPlantOverviewPanelStatistics.BUNDLE_PATH;
import org.opentcs.util.statistics.StatisticsLogParser;
import org.opentcs.util.statistics.StatisticsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A panel for displaying statistics data.
 *
 * @author Stefan Walter (Fraunhofer IML)
 */
public class StatisticsPanel
    extends PluggablePanel {

  /**
   * This class's Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(StatisticsPanel.class);
  /**
   * This class's resources bundle.
   */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_PATH);
  /**
   * A file chooser for selecting the input file.
   */
  private final JFileChooser inputFileChooser = new JFileChooser(".");
  /**
   * The input file to be parsed.
   */
  private File inputFile;
  /**
   * The records read from the input file.
   */
  private List<StatisticsRecord> inputRecords;
  /**
   * The parsed analysis data.
   */
  private AnalysisData analysisData;
  /**
   * Indicates whether this component is enabled.
   */
  private boolean initialized;

  /**
   * Creates a new StatisticsPanel.
   */
  public StatisticsPanel() {
    initComponents();

    inputFileChooser.setMultiSelectionEnabled(false);
    inputFileChooser.setFileFilter(new FileNameExtensionFilter(BUNDLE.getString("statisticsPanel.fileChooser_inputFile.fileFilter.description"),
                                                               "txt"));
  }

  @Override
  public void initialize() {
    initialized = true;
  }

  @Override
  public boolean isInitialized() {
    return initialized;
  }

  @Override
  public void terminate() {
    initialized = false;
  }

  /**
   * Updates the panel's contents.
   */
  private void updatePanel() {
    if (inputFile == null) {
      analysisInputFileTxt.setText("-");
    }
    else {
      analysisInputFileTxt.setText(inputFile.getAbsolutePath());
    }
    if (inputRecords == null) {
      parsedRecordsTxt.setText("0");
    }
    else {
      parsedRecordsTxt.setText(Integer.toString(inputRecords.size()));
    }
    VehiclesTableModel vehiclesModel = new VehiclesTableModel();
    PointsTableModel pointsModel = new PointsTableModel();
    OrdersTableModel ordersModel = new OrdersTableModel();
    int successfulOrders = 0;
    int lateOrders = 0;
    totalRuntimeTxt.setText("-");
    ordersSuccRateTxt.setText("-");
    ordersDeadlineRateTxt.setText("-");
    if (analysisData != null) {
      totalRuntimeTxt.setText(
          TimePeriodFormat.formatHumanReadable(analysisData.getTotalRuntime()));
      for (VehicleStats vehicle : analysisData.getVehicles()) {
        vehiclesModel.addData(vehicle);
      }
      for (PointStats point : analysisData.getPoints()) {
        pointsModel.addData(point);
      }
      for (OrderStats order : analysisData.getOrders()) {
        ordersModel.addData(order);
        if (order.isFinishedSuccessfully()) {
          successfulOrders++;
        }
        if (order.hasCrossedDeadline()) {
          lateOrders++;
        }
      }

      ordersSuccRateTxt.setText(successfulOrders + "/" + ordersModel.getRowCount());
      ordersDeadlineRateTxt.setText(lateOrders + "/" + ordersModel.getRowCount());
    }
    analysisVehiclesTable.setModel(vehiclesModel);
    analysisCourseTable.setModel(pointsModel);
    analysisOrdersTable.setModel(ordersModel);
  }

  /**
   * Selects and parses an input file.
   */
  private void selectInputFile() {
    int choice = inputFileChooser.showOpenDialog(this);
    if (choice == JFileChooser.APPROVE_OPTION) {
      inputFile = inputFileChooser.getSelectedFile();
      try {
        inputRecords = StatisticsLogParser.parseLog(inputFile);
        analysisData = AnalysisData.analyzeRecords(inputRecords);
        if (analysisData == null) {
          inputFile = null;
          inputRecords = null;
        }
      }
      catch (IOException exc) {
        LOG.warn("Exception parsing input file " + inputFile, exc);
        inputFile = null;
        inputRecords = null;
        analysisData = null;
      }
      updatePanel();
    }
  }

  // CHECKSTYLE:OFF
  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jPanel1 = new javax.swing.JPanel();
    analysisInputFileLbl = new javax.swing.JLabel();
    analysisInputFileTxt = new javax.swing.JTextField();
    parsedRecordsLbl = new javax.swing.JLabel();
    parsedRecordsTxt = new javax.swing.JTextField();
    startInputParsingBtn = new javax.swing.JButton();
    totalRuntimeLbl = new javax.swing.JLabel();
    totalRuntimeTxt = new javax.swing.JTextField();
    analysisDataPanel = new javax.swing.JPanel();
    analysisVehiclesPanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    analysisVehiclesTable = new javax.swing.JTable();
    analysisCoursePanel = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    analysisCourseTable = new javax.swing.JTable();
    analysisOrdersPanel = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    analysisOrdersTable = new javax.swing.JTable();
    jPanel2 = new javax.swing.JPanel();
    ordersSuccRateLbl = new javax.swing.JLabel();
    ordersSuccRateTxt = new javax.swing.JTextField();
    ordersDeadlineRateLbl = new javax.swing.JLabel();
    ordersDeadlineRateTxt = new javax.swing.JTextField();

    setLayout(new java.awt.BorderLayout());

    jPanel1.setLayout(new java.awt.GridBagLayout());

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/org/opentcs/plantoverview/statisticsPanel/Bundle"); // NOI18N
    analysisInputFileLbl.setText(bundle.getString("statisticsPanel.label_inputFile.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
    jPanel1.add(analysisInputFileLbl, gridBagConstraints);

    analysisInputFileTxt.setEditable(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    jPanel1.add(analysisInputFileTxt, gridBagConstraints);

    parsedRecordsLbl.setText(bundle.getString("statisticsPanel.label_parsedRecords.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
    jPanel1.add(parsedRecordsLbl, gridBagConstraints);

    parsedRecordsTxt.setEditable(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
    jPanel1.add(parsedRecordsTxt, gridBagConstraints);

    startInputParsingBtn.setText(bundle.getString("statisticsPanel.button_readInputFile.text")); // NOI18N
    startInputParsingBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        startInputParsingBtnActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
    jPanel1.add(startInputParsingBtn, gridBagConstraints);

    totalRuntimeLbl.setText(bundle.getString("statisticsPanel.label_totalRuntime.text")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
    jPanel1.add(totalRuntimeLbl, gridBagConstraints);

    totalRuntimeTxt.setEditable(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
    jPanel1.add(totalRuntimeTxt, gridBagConstraints);

    add(jPanel1, java.awt.BorderLayout.NORTH);

    analysisDataPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("statisticsPanel.panel_analysisData.border.title"))); // NOI18N
    analysisDataPanel.setLayout(new javax.swing.BoxLayout(analysisDataPanel, javax.swing.BoxLayout.Y_AXIS));

    analysisVehiclesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("statisticsPanel.panel_analysisVehicles.border.title"))); // NOI18N
    analysisVehiclesPanel.setLayout(new java.awt.BorderLayout());

    jScrollPane1.setPreferredSize(new java.awt.Dimension(550, 200));

    analysisVehiclesTable.setModel(new VehiclesTableModel());
    jScrollPane1.setViewportView(analysisVehiclesTable);

    analysisVehiclesPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    analysisDataPanel.add(analysisVehiclesPanel);

    analysisCoursePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("statisticsPanel.panel_analysisCourse.border.title"))); // NOI18N
    analysisCoursePanel.setLayout(new java.awt.BorderLayout());

    jScrollPane2.setPreferredSize(new java.awt.Dimension(550, 200));

    analysisCourseTable.setModel(new PointsTableModel());
    jScrollPane2.setViewportView(analysisCourseTable);

    analysisCoursePanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

    analysisDataPanel.add(analysisCoursePanel);

    analysisOrdersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("statisticsPanel.panel_analysisOrders.border.title"))); // NOI18N
    analysisOrdersPanel.setLayout(new java.awt.BorderLayout());

    jScrollPane3.setPreferredSize(new java.awt.Dimension(550, 200));

    analysisOrdersTable.setModel(new OrdersTableModel());
    jScrollPane3.setViewportView(analysisOrdersTable);

    analysisOrdersPanel.add(jScrollPane3, java.awt.BorderLayout.CENTER);

    ordersSuccRateLbl.setText(bundle.getString("statisticsPanel.label_ordersSuccessRate.text")); // NOI18N
    jPanel2.add(ordersSuccRateLbl);

    ordersSuccRateTxt.setEditable(false);
    ordersSuccRateTxt.setColumns(12);
    ordersSuccRateTxt.setText("0");
    jPanel2.add(ordersSuccRateTxt);

    ordersDeadlineRateLbl.setText(bundle.getString("statisticsPanel.label_ordersDeadlineCrossedRate.text")); // NOI18N
    jPanel2.add(ordersDeadlineRateLbl);

    ordersDeadlineRateTxt.setEditable(false);
    ordersDeadlineRateTxt.setColumns(12);
    ordersDeadlineRateTxt.setText("0");
    jPanel2.add(ordersDeadlineRateTxt);

    analysisOrdersPanel.add(jPanel2, java.awt.BorderLayout.SOUTH);

    analysisDataPanel.add(analysisOrdersPanel);

    add(analysisDataPanel, java.awt.BorderLayout.CENTER);

    getAccessibleContext().setAccessibleName(bundle.getString("statisticsPanel.accessibleName")); // NOI18N
  }// </editor-fold>//GEN-END:initComponents

  private void startInputParsingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startInputParsingBtnActionPerformed
    selectInputFile();
  }//GEN-LAST:event_startInputParsingBtnActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel analysisCoursePanel;
  private javax.swing.JTable analysisCourseTable;
  private javax.swing.JPanel analysisDataPanel;
  private javax.swing.JLabel analysisInputFileLbl;
  private javax.swing.JTextField analysisInputFileTxt;
  private javax.swing.JPanel analysisOrdersPanel;
  private javax.swing.JTable analysisOrdersTable;
  private javax.swing.JPanel analysisVehiclesPanel;
  private javax.swing.JTable analysisVehiclesTable;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JLabel ordersDeadlineRateLbl;
  private javax.swing.JTextField ordersDeadlineRateTxt;
  private javax.swing.JLabel ordersSuccRateLbl;
  private javax.swing.JTextField ordersSuccRateTxt;
  private javax.swing.JLabel parsedRecordsLbl;
  private javax.swing.JTextField parsedRecordsTxt;
  private javax.swing.JButton startInputParsingBtn;
  private javax.swing.JLabel totalRuntimeLbl;
  private javax.swing.JTextField totalRuntimeTxt;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
}
