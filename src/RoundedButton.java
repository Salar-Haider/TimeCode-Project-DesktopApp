
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

 class RoundedButton extends JButton {

        private Color defaultColor;
        private Color hoverColor;
        private boolean hovered = false;
        private boolean rounded;

        public RoundedButton(String text, Color color ,int defaultFontSize, float hoveredFontSize, boolean round) {
            
          
            super(text);
   
            rounded = round;
            
            float dFSize = defaultFontSize;
            float hFSize = hoveredFontSize;
            
            
            
            this.defaultColor = color;
            this.hoverColor = color.darker();
            setForeground(Color.WHITE);
            setBackground(defaultColor);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFont(new Font("Arial", Font.BOLD, defaultFontSize));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effect with color change and resizing
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hovered = true;
                    setBackground(hoverColor);
                    setFont(getFont().deriveFont(hFSize));  // Slightly increase font size on hover
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    hovered = false;
                    setBackground(defaultColor);
                    setFont(getFont().deriveFont(dFSize));  // Reset font size
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (hovered) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(defaultColor);
            }

            // Draw rounded rectangle button
            
            if(rounded == true){
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
            
            super.paintComponent(g);
        }
 
        @Override
        protected void paintBorder(Graphics g) {
            // No border needed as the button is rounded
        }
    }