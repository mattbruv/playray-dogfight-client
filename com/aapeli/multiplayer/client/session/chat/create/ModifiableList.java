package com.aapeli.multiplayer.client.session.chat.create;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;

public class ModifiableList
  extends JPanel
  implements ListSelectionListener, Option
{
  private JList list;
  private DefaultListModel listModel;
  private String hireString = "Hire";
  private String fireString = "Fire";
  private JButton fireButton;
  private JTextField employeeName;
  private String key;
  
  public ModifiableList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    super(new BorderLayout());
    setOpaque(false);
    setMaximumSize(new Dimension(paramInt1, 100));
    setMinimumSize(new Dimension(0, paramInt2));
    setPreferredSize(new Dimension(getPreferredSize().width, paramInt2));
    this.fireString = paramString2;
    this.hireString = paramString3;
    this.key = paramString1;
    this.listModel = new DefaultListModel();
    this.list = new JList(this.listModel);
    this.list.setSelectionMode(0);
    this.list.setSelectedIndex(0);
    this.list.addListSelectionListener(this);
    this.list.setVisibleRowCount(5);
    JScrollPane localJScrollPane = new JScrollPane(this.list);
    JButton localJButton = new JButton(paramString3);
    HireListener localHireListener = new HireListener(localJButton);
    localJButton.setActionCommand(paramString3);
    localJButton.addActionListener(localHireListener);
    localJButton.setEnabled(false);
    this.fireButton = new JButton(paramString2);
    this.fireButton.setActionCommand(paramString2);
    this.fireButton.addActionListener(new FireListener());
    this.employeeName = new JTextField(10);
    this.employeeName.addActionListener(localHireListener);
    this.employeeName.getDocument().addDocumentListener(localHireListener);
    JPanel localJPanel = new JPanel();
    localJPanel.setOpaque(false);
    localJPanel.setLayout(new BoxLayout(localJPanel, 2));
    localJPanel.add(this.fireButton);
    localJPanel.add(Box.createHorizontalStrut(5));
    localJPanel.add(new JSeparator(1));
    localJPanel.add(Box.createHorizontalStrut(5));
    localJPanel.add(this.employeeName);
    localJPanel.add(localJButton);
    localJPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(localJScrollPane, "Center");
    add(localJPanel, "Last");
  }
  
  public void valueChanged(ListSelectionEvent paramListSelectionEvent)
  {
    if (!paramListSelectionEvent.getValueIsAdjusting()) {
      if (this.list.getSelectedIndex() == -1) {
        this.fireButton.setEnabled(false);
      } else {
        this.fireButton.setEnabled(true);
      }
    }
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getValue()
  {
    String str1 = "";
    Enumeration localEnumeration = this.listModel.elements();
    while (localEnumeration.hasMoreElements())
    {
      String str2 = (String)localEnumeration.nextElement();
      str1 = str1 + str2.replaceAll(",", "");
      if (localEnumeration.hasMoreElements()) {
        str1 = str1 + ",";
      }
    }
    return str1;
  }
  
  public void reset()
  {
    this.listModel.removeAllElements();
  }
  
  public void setValue(String paramString)
  {
    this.listModel.removeAllElements();
    String[] arrayOfString = paramString.split(",");
    for (int i = 0; i < arrayOfString.length; i++) {
      if (arrayOfString[i].length() > 0) {
        this.listModel.addElement(arrayOfString[i]);
      }
    }
  }
  
  class HireListener
    implements ActionListener, DocumentListener
  {
    private boolean alreadyEnabled = false;
    private JButton button;
    
    public HireListener(JButton paramJButton)
    {
      this.button = paramJButton;
    }
    
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      String str = ModifiableList.this.employeeName.getText();
      if ((str.equals("")) || (alreadyInList(str)))
      {
        Toolkit.getDefaultToolkit().beep();
        ModifiableList.this.employeeName.requestFocusInWindow();
        ModifiableList.this.employeeName.selectAll();
        return;
      }
      int i = ModifiableList.this.list.getSelectedIndex();
      if (i == -1) {
        i = 0;
      } else {
        i++;
      }
      ModifiableList.this.listModel.insertElementAt(ModifiableList.this.employeeName.getText(), i);
      ModifiableList.this.employeeName.requestFocusInWindow();
      ModifiableList.this.employeeName.setText("");
      ModifiableList.this.list.setSelectedIndex(i);
      ModifiableList.this.list.ensureIndexIsVisible(i);
    }
    
    protected boolean alreadyInList(String paramString)
    {
      return ModifiableList.this.listModel.contains(paramString);
    }
    
    public void insertUpdate(DocumentEvent paramDocumentEvent)
    {
      enableButton();
    }
    
    public void removeUpdate(DocumentEvent paramDocumentEvent)
    {
      handleEmptyTextField(paramDocumentEvent);
    }
    
    public void changedUpdate(DocumentEvent paramDocumentEvent)
    {
      if (!handleEmptyTextField(paramDocumentEvent)) {
        enableButton();
      }
    }
    
    private void enableButton()
    {
      if (!this.alreadyEnabled) {
        this.button.setEnabled(true);
      }
    }
    
    private boolean handleEmptyTextField(DocumentEvent paramDocumentEvent)
    {
      if (paramDocumentEvent.getDocument().getLength() <= 0)
      {
        this.button.setEnabled(false);
        this.alreadyEnabled = false;
        return true;
      }
      return false;
    }
  }
  
  class FireListener
    implements ActionListener
  {
    FireListener() {}
    
    public void actionPerformed(ActionEvent paramActionEvent)
    {
      int i = ModifiableList.this.list.getSelectedIndex();
      ModifiableList.this.listModel.remove(i);
      int j = ModifiableList.this.listModel.getSize();
      if (j == 0)
      {
        ModifiableList.this.fireButton.setEnabled(false);
      }
      else
      {
        if (i == ModifiableList.this.listModel.getSize()) {
          i--;
        }
        ModifiableList.this.list.setSelectedIndex(i);
        ModifiableList.this.list.ensureIndexIsVisible(i);
      }
    }
  }
}
