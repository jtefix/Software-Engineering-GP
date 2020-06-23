package View;

import Control.Swing.SwingMainPageController;
import Model.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMainPage extends JFrame{

    private JPanel rootPanel;

    private JTextPane resultsPane;
    private JButton calculateButton;
    private JRadioButton numberOfPagesViewedRadioButton;
    private JRadioButton timeOnPageRadioButton;
    private JButton clearButton;

    private SwingMainPageController swingMainPageController;

    public SwingMainPage(Database database){

        add(rootPanel);
        setTitle("Campaign: " + database.getCampaignName());
        setSize(1000,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.swingMainPageController = new SwingMainPageController(database,this);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingMainPageController.clickCalculateButton();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingMainPageController.clickClearButton();
            }
        });

        setVisible(true);

    }

    public JTextPane getResultsPane() {
        return resultsPane;
    }

    public void setResultsPane(JTextPane resultsPane) {
        this.resultsPane = resultsPane;
    }

    public JButton getCalculateButton() {
        return calculateButton;
    }

    public void setCalculateButton(JButton calculateButton) {
        this.calculateButton = calculateButton;
    }

    public JRadioButton getNumberOfPagesViewedRadioButton() {
        return numberOfPagesViewedRadioButton;
    }

    public void setNumberOfPagesViewedRadioButton(JRadioButton numberOfPagesViewedRadioButton) {
        this.numberOfPagesViewedRadioButton = numberOfPagesViewedRadioButton;
    }

    public JRadioButton getTimeOnPageRadioButton() {
        return timeOnPageRadioButton;
    }

    public void setTimeOnPageRadioButton(JRadioButton timeOnPageRadioButton) {
        this.timeOnPageRadioButton = timeOnPageRadioButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public void setClearButton(JButton clearButton) {
        this.clearButton = clearButton;
    }
}
