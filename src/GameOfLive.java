import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;


public class GameOfLive extends javax.swing.JFrame {
    final int width = 200, height = 100;
    boolean[][] currentMove = new boolean[height][width],
            nextMove = new boolean[height][width];
    boolean play;
    Image offScrImg;
    Graphics offScrGraph;

    public GameOfLive() {
        initComponents();
        offScrImg = createImage(jPanel1.getWidth(), jPanel1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        Timer time = new Timer();

        TimerTask task = new TimerTask() {
            public void run() {
                if (play) {
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            nextMove[i][j] = shouldCellLive(i, j);
                        }
                    }
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            currentMove[i][j] = nextMove[i][j];
                        }
                    }
                    repain();
                }
            }
        };
        time.scheduleAtFixedRate(task, 0, 100);
        repain();
    }

    private boolean shouldCellLive(int row, int col) {
        int liveNeighbors = countLiveNeighbors(row, col);

        if (liveNeighbors == 3) {
            return true; // Any dead cell with exactly 3 live neighbors comes to life.
        } else if (currentMove[row][col] && liveNeighbors == 2) {
            return true; // Any live cell with 2 live neighbors survives.
        } else {
            return false; // Otherwise, the cell dies.
        }
    }

    private int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        // Check neighboring cells in all 8 directions.
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue; // Skip the current cell.
                }

                int neighborRow = row + dr;
                int neighborCol = col + dc;

                // Check if the neighbor is within bounds and alive.
                if (isValidCell(neighborRow, neighborCol) && currentMove[neighborRow][neighborCol]) {
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonPlay = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new Dimension(1024, 768));
        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 293, Short.MAX_VALUE)
        );

        jButtonPlay.setText("Play");
        jButtonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayActionPerformed(evt);
            }
        });

        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButtonPlay, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        323, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonPlay)
                                        .addComponent(jButton2))
                                .addContainerGap())
        );
        pack();
    }


    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {
        offScrImg = createImage(jPanel1.getWidth(), jPanel1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        repain();
    }

    private void jButtonPlayActionPerformed(java.awt.event.ActionEvent evt) {
        play = !play;
        if (play) jButtonPlay.setText("Pause");
        else jButtonPlay.setText("Play");
        repain();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        currentMove = new boolean[height][width];
        repain();
    }

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {
        int j = width * evt.getX() / jPanel1.getWidth();
        int i = height * evt.getY() / jPanel1.getHeight();
        if (SwingUtilities.isLeftMouseButton(evt)) {
            currentMove[i][j] = true;
        } else currentMove[i][j] = false;
        repain();
    }


    private void repain() {
        offScrGraph.setColor(jPanel1.getBackground());
        offScrGraph.fillRect(0, 0,
                jPanel1.getWidth(),
                jPanel1.getHeight());

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (currentMove[i][j]) {
                    offScrGraph.setColor(Color.YELLOW);
                    int x = j * jPanel1.getWidth() / width;
                    int y = i * jPanel1.getHeight() / height;
                    offScrGraph.fillRect(x, y,
                            jPanel1.getWidth() / width,
                            jPanel1.getHeight() / height);
                }
            }
        }
        offScrGraph.setColor(Color.BLACK);
        for (int i = 1; i < height; i++) {
            int y = i * jPanel1.getHeight() / height;
            offScrGraph.drawLine(0, y, jPanel1.getWidth(), y);
        }
        for (int j = 1; j < width; j++) {
            int x = j * jPanel1.getWidth() / width;
            offScrGraph.drawLine(x, 0, x, jPanel1.getHeight());
        }
        jPanel1.getGraphics().drawImage(offScrImg, 0, 0, jPanel1);
    }

    private javax.swing.JButton jButtonPlay;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;

}
