import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SearchCameraGUI extends javax.swing.JFrame {

    public final String title = "Team Kevin IP Camera Tool";
    public static ArrayList<Device> DeviceList;
    public static Integer index;
    public static Boolean disableInteraction;

    /**
     * Creates new form SearchCameraGUI
     */
    public SearchCameraGUI() {
        disableInteraction = false;
        this.setUndecorated(true);
        this.setTitle(title);
        this.setResizable(false);
        initComponents();
        getContentPane().setBackground(new java.awt.Color(38,40,51));
        UIManager.put("activeCaption", new javax.swing.plaf.ColorUIResource(new java.awt.Color(38,40,51)));
        JDialog.setDefaultLookAndFeelDecorated(true);

        FrameDragListener frameDragListener = new FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);

//            frame.pack();
        this.setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        toolName = new javax.swing.JLabel();
        titleSelectCamera = new javax.swing.JLabel();
        closeButton = new javax.swing.JLabel();
        macBox = new javax.swing.JLabel();
        ipBox = new javax.swing.JLabel();
        ArrowRight = new javax.swing.JLabel();
        ArrowLeft = new javax.swing.JLabel();
        buttonHack = new javax.swing.JLabel();
        buttonRefresh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(38, 40, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(711, 400));
        setSize(new java.awt.Dimension(261, 400));

        toolName.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        toolName.setForeground(new java.awt.Color(255, 255, 255));
        toolName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        toolName.setText(title);
        toolName.setInheritsPopupMenu(false);

        titleSelectCamera.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        titleSelectCamera.setForeground(new java.awt.Color(255, 255, 255));
        titleSelectCamera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleSelectCamera.setText("Select IP Camera");

        closeButton.setBackground(new java.awt.Color(207, 0, 15));
        closeButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        closeButton.setForeground(new java.awt.Color(255, 255, 255));
        closeButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeButton.setText("X");
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        closeButton.setOpaque(true);
//        closeButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                closeButtonMouseDragged(evt);
//            }
//        });
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButtonMouseExited(evt);
            }
        });

        macBox.setBackground(new java.awt.Color(217, 215, 204));
        macBox.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        macBox.setForeground(new java.awt.Color(38, 40, 51));
        macBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        macBox.setText("");
        macBox.setOpaque(true);

        ipBox.setBackground(new java.awt.Color(217, 215, 204));
        ipBox.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        ipBox.setForeground(new java.awt.Color(38, 40, 51));
        ipBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ipBox.setText("");
        ipBox.setOpaque(true);

        ArrowRight.setBackground(new java.awt.Color(153, 153, 0));
        ArrowRight.setFont(new java.awt.Font("Montserrat", 1, 36)); // NOI18N
        ArrowRight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ArrowRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-right.PNG"))); // NOI18N
//        ArrowRight.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                ArrowRightMouseDragged(evt);
//            }
//        });
        ArrowRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArrowRightMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ArrowRightMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ArrowRightMouseExited(evt);
            }
        });

        ArrowLeft.setBackground(new java.awt.Color(153, 153, 0));
        ArrowLeft.setFont(new java.awt.Font("Montserrat", 1, 36)); // NOI18N
        ArrowLeft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ArrowLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-left.PNG"))); // NOI18N
//        ArrowLeft.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                ArrowLeftMouseDragged(evt);
//            }
//        });
        ArrowLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArrowLeftMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ArrowLeftMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ArrowLeftMouseExited(evt);
            }
        });

        buttonHack.setBackground(new java.awt.Color(63, 114, 155));
        buttonHack.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        buttonHack.setForeground(new java.awt.Color(255, 255, 255));
        buttonHack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        buttonHack.setText("Hack");
        buttonHack.setOpaque(true);
//        buttonHack.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                buttonHackMouseDragged(evt);
//            }
//        });
        buttonHack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonHackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonHackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonHackMouseExited(evt);
            }
        });

        buttonRefresh.setBackground(new java.awt.Color(63, 114, 155));
        buttonRefresh.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        buttonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        buttonRefresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        buttonRefresh.setText("Refresh");
        buttonRefresh.setOpaque(true);
//        buttonRefresh.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                buttonRefreshMouseDragged(evt);
//            }
//        });
        buttonRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRefreshMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonRefreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonRefreshMouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleSelectCamera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(ArrowLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(ipBox, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(macBox, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(buttonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonHack, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ArrowRight, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 140, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(toolName)
                                                .addContainerGap())
                                        .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(titleSelectCamera)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ArrowLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(ipBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(macBox, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(ArrowRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonHack, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(91, 91, 91)
                                .addComponent(toolName)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void closeButtonMouseEntered(java.awt.event.MouseEvent evt) {
        closeButton.setBackground(new java.awt.Color(214,69,65));
    }

    private void closeButtonMouseExited(java.awt.event.MouseEvent evt) {
        closeButton.setBackground(new java.awt.Color(207,0,15));
    }

    private void closeButtonMouseDragged(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    private void ArrowRightMouseClicked(java.awt.event.MouseEvent evt) {
        if (showArrows() && !disableInteraction) {
            System.out.println("CLICK RIGHT");
            updateFields(true);
        }
    }

    private void ArrowRightMouseDragged(java.awt.event.MouseEvent evt) {
        if (showArrows() && !disableInteraction) {
            System.out.println("CLICK RIGHT");
            updateFields(true);
        }
    }

    private void ArrowRightMouseEntered(java.awt.event.MouseEvent evt) {
        if (showArrows()) {
            ArrowRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-right-white.PNG")));
        }
    }

    private void ArrowRightMouseExited(java.awt.event.MouseEvent evt) {
        if (showArrows()) {
            ArrowRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-right.PNG")));
        }
    }

    private void ArrowLeftMouseClicked(java.awt.event.MouseEvent evt) {
        if (showArrows() && !disableInteraction) {
            System.out.println("CLICK LEFT");
            updateFields(false);
        }
    }

    private void ArrowLeftMouseDragged(java.awt.event.MouseEvent evt) {
        if (showArrows() && !disableInteraction) {
            System.out.println("CLICK LEFT");
            updateFields(false);
        }
    }

    private void ArrowLeftMouseEntered(java.awt.event.MouseEvent evt) {
        if (showArrows()) {
            ArrowLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-left-white.PNG")));
        }
    }

    private void ArrowLeftMouseExited(java.awt.event.MouseEvent evt) {
        if (showArrows()) {
            ArrowLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-left.PNG")));
        }
    }

    private void buttonHackMouseClicked(java.awt.event.MouseEvent evt) {
        if (!disableInteraction && DeviceList.size() > 0 ) {
            this.dispose();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new GUI(DeviceList.get(index)).setVisible(true);
                }
            });
        }
    }

    private void buttonHackMouseDragged(java.awt.event.MouseEvent evt) {
        if (!disableInteraction && DeviceList.size() > 0) {
            System.out.println("CLICK HACK");
        }
    }

    private void buttonHackMouseEntered(java.awt.event.MouseEvent evt) {
        buttonHack.setBackground(new java.awt.Color(84,140,185));
    }

    private void buttonHackMouseExited(java.awt.event.MouseEvent evt) {
        buttonHack.setBackground(new java.awt.Color(63,114,155));
    }

    private void buttonRefreshMouseDragged(java.awt.event.MouseEvent evt) {
        if (!disableInteraction) {
            refresh();
            System.out.println("CLICK REFRESH");
        }
    }

    private void buttonRefreshMouseClicked(java.awt.event.MouseEvent evt) {
        buttonRefreshMouseDragged(evt);
    }

    private void buttonRefreshMouseEntered(java.awt.event.MouseEvent evt) {
        buttonRefresh.setBackground(new java.awt.Color(84,140,185));
    }

    private void buttonRefreshMouseExited(java.awt.event.MouseEvent evt) {
        buttonRefresh.setBackground(new java.awt.Color(63,114,155));
    }


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
            java.util.logging.Logger.getLogger(SearchCameraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchCameraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchCameraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchCameraGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchCameraGUI().setVisible(true);
            }
        });
    }

    public void updateDevices(ArrayList<Device> newList) {
        DeviceList = newList;

        if (newList.size() > 1) {
            ArrowLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-left.PNG")));
            ArrowRight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow-right.PNG")));
        } else {
            ArrowLeft.setIcon(null);
            ArrowRight.setIcon(null);
        }

        if (newList.size() > 0) {
            ipBox.setText(newList.get(0).getIp());
            macBox.setText(newList.get(0).getMac());
        }

        index = 0;
    }

    public void updateFields(boolean add) {
        if (add) {
            index = (index + 1) % DeviceList.size();
        } else {
            index = ((index - 1) + DeviceList.size()) % DeviceList.size();
        }
        ipBox.setText(DeviceList.get(index).getIp());
        macBox.setText(DeviceList.get(index).getMac());
    }

    public Boolean showArrows() {
        if (DeviceList.size() > 1) {
            return true;
        }
        return false;
    }

    public void refresh() {
        disableInteraction = true;

        try {
            ArrayList<Device> tempDevices = Scan.getDevices();
            if (tempDevices == null) {
                tempDevices = new ArrayList<>();
            }
            updateDevices(tempDevices);
        } catch (Exception e) {
            e.printStackTrace();
        }

        disableInteraction = false;
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel ArrowLeft;
    private javax.swing.JLabel ArrowRight;
    private javax.swing.JLabel buttonHack;
    private javax.swing.JLabel buttonRefresh;
    private javax.swing.JLabel closeButton;
    private javax.swing.JLabel ipBox;
    private javax.swing.JLabel macBox;
    private javax.swing.JLabel titleSelectCamera;
    private javax.swing.JLabel toolName;
    // End of variables declaration                   
}
