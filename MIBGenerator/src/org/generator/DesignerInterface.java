package org.generator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class DesignerInterface extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 3281433828955488913L;
    private JPanel contentPane;
    private JTextField txtOID;
    private JTextField txtTranslateName;
    private JTextField txtCurrentOID;
    private JTextField txtParentTranslateName;
    private JTextField txtParentCurrentOID;
    private JTextField txtValue;
    private static JTextPane txpSQL;
    private static JTextPane txpJSON;
    private JTextField txtReadCommunity;
    private JTextField txtWriteCommunity;
    private JComboBox<String> cmbValueType;
    
    private static JLabel lblStatus;
    
    private static Backend mBackend;
    private JTextField txtNextOID;
    
    private static DesignerInterface frame;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    frame = new DesignerInterface();
                    frame.setVisible(true);
                    
                    mBackend = new Backend(txpJSON, txpSQL, lblStatus);
                    mBackend.start();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public DesignerInterface()
    {
        setTitle("MIB SQL JSON Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 676, 537);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblOid = new JLabel("OID");
        lblOid.setBounds(10, 62, 46, 14);
        contentPane.add(lblOid);
        
        JLabel lblTranslateName = new JLabel("Translate Name");
        lblTranslateName.setBounds(10, 87, 174, 14);
        contentPane.add(lblTranslateName);
        
        JLabel lblCurrentOID = new JLabel("Current OID");
        lblCurrentOID.setBounds(10, 112, 89, 14);
        contentPane.add(lblCurrentOID);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 221, 637, 8);
        contentPane.add(separator);
        
        txtOID = new JTextField();
        txtOID.setBounds(194, 56, 86, 20);
        contentPane.add(txtOID);
        txtOID.setColumns(10);
        
        txtTranslateName = new JTextField();
        txtTranslateName.setBounds(194, 81, 86, 20);
        contentPane.add(txtTranslateName);
        txtTranslateName.setColumns(10);
        
        txtCurrentOID = new JTextField();
        txtCurrentOID.setBounds(194, 106, 86, 20);
        contentPane.add(txtCurrentOID);
        txtCurrentOID.setColumns(10);
        
        JLabel lblMIB = new JLabel("Current MIB");
        lblMIB.setBounds(10, 31, 89, 14);
        contentPane.add(lblMIB);
        
        JLabel lblParentTranslateName = new JLabel("Parent Translate Name");
        lblParentTranslateName.setBounds(10, 137, 174, 14);
        contentPane.add(lblParentTranslateName);
        
        JLabel lblParentCurrentOID = new JLabel("Parent Current OID");
        lblParentCurrentOID.setBounds(10, 162, 174, 14);
        contentPane.add(lblParentCurrentOID);
        
        txtParentTranslateName = new JTextField();
        txtParentTranslateName.setColumns(10);
        txtParentTranslateName.setBounds(194, 134, 86, 20);
        contentPane.add(txtParentTranslateName);
        
        JLabel lblCurrentSQL = new JLabel("SQL File");
        lblCurrentSQL.setBounds(10, 240, 57, 14);
        contentPane.add(lblCurrentSQL);
        
        txtParentCurrentOID = new JTextField();
        txtParentCurrentOID.setColumns(10);
        txtParentCurrentOID.setBounds(194, 159, 86, 20);
        contentPane.add(txtParentCurrentOID);
        
        txtValue = new JTextField();
        txtValue.setColumns(10);
        txtValue.setBounds(472, 84, 175, 20);
        contentPane.add(txtValue);
        
        JLabel lblValueType = new JLabel("Value Type");
        lblValueType.setBounds(337, 62, 121, 14);
        contentPane.add(lblValueType);
        
        JLabel lblNewLabel = new JLabel("Current JSON");
        lblNewLabel.setBounds(337, 240, 98, 14);
        contentPane.add(lblNewLabel);
        
        cmbValueType = new JComboBox<String>();
        cmbValueType.setModel(new DefaultComboBoxModel<String>(new String[] {"String", "Time", "Integer", "Long", "Folder"}));
        cmbValueType.setBounds(472, 59, 175, 20);
        contentPane.add(cmbValueType);
        
        JLabel lblValue = new JLabel("Value");
        lblValue.setBounds(337, 87, 121, 14);
        contentPane.add(lblValue);
        
        JScrollPane lSQLScrollPane = new JScrollPane();
        lSQLScrollPane.setBounds(10, 265, 310, 181);
        contentPane.add(lSQLScrollPane);
        
        txpSQL = new JTextPane();
        lSQLScrollPane.setViewportView(txpSQL);
        
        JScrollPane lJSONScrollPane = new JScrollPane();
        lJSONScrollPane.setBounds(337, 265, 310, 181);
        contentPane.add(lJSONScrollPane);
        
        txpJSON = new JTextPane();
        lJSONScrollPane.setViewportView(txpJSON);
        
        JMenuBar mbMenuBar = new JMenuBar();
        mbMenuBar.setBounds(0, 0, 660, 20);
        contentPane.add(mbMenuBar);
        
        JMenu mnFile = new JMenu("File");
        mbMenuBar.add(mnFile);
        
        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pArugment) {
                mBackend.setOpen();
            }
        });
        mnFile.add(mntmOpen);
        
        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pArugment) {
                mBackend.setSave();
            }
        });
        mnFile.add(mntmSave);
        
        JSeparator mnSeparator = new JSeparator();
        mnFile.add(mnSeparator);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pArugment) {
                mBackend.setRunning(false);
                frame.dispose();
            }
        });
        mnFile.add(mntmExit);
        
        JButton btnUpdateInsert = new JButton("Update/Insert");
        btnUpdateInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pArugment) {
                mBackend.setUpdateInsert(
                        txtOID.getText(), 
                        txtCurrentOID.getText(),
                        txtTranslateName.getText(), 
                        txtParentTranslateName.getText(), 
                        txtParentCurrentOID.getText(), 
                        txtNextOID.getText(), 
                        cmbValueType.getSelectedItem().toString(), 
                        txtValue.getText(), 
                        txtReadCommunity.getText(), 
                        txtWriteCommunity.getText());
            }
        });
        btnUpdateInsert.setBounds(409, 187, 114, 23);
        contentPane.add(btnUpdateInsert);
        
        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pArugment) {
                mBackend.setRemove(
                        txtOID.getText());
            }
        });
        btnRemove.setBounds(533, 187, 114, 23);
        contentPane.add(btnRemove);
        
        JLabel lblNextOid = new JLabel("Next OID");
        lblNextOid.setBounds(10, 187, 109, 14);
        contentPane.add(lblNextOid);
        
        JLabel lblReadCommunity = new JLabel("Read Community");
        lblReadCommunity.setBounds(337, 112, 121, 14);
        contentPane.add(lblReadCommunity);
        
        JLabel lblWriteCommunity = new JLabel("Write Community");
        lblWriteCommunity.setBounds(337, 137, 121, 14);
        contentPane.add(lblWriteCommunity);
        
        txtReadCommunity = new JTextField();
        txtReadCommunity.setColumns(10);
        txtReadCommunity.setBounds(472, 109, 175, 20);
        contentPane.add(txtReadCommunity);
        
        txtWriteCommunity = new JTextField();
        txtWriteCommunity.setColumns(10);
        txtWriteCommunity.setBounds(472, 134, 175, 20);
        contentPane.add(txtWriteCommunity);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 457, 637, 8);
        contentPane.add(separator_1);
        
        lblStatus = new JLabel("Complete");
        lblStatus.setBounds(10, 473, 310, 14);
        contentPane.add(lblStatus);
        
        txtNextOID = new JTextField();
        txtNextOID.setBounds(194, 184, 86, 20);
        contentPane.add(txtNextOID);
        txtNextOID.setColumns(10);
    }
}
