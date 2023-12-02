package client.main.mainmap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dice extends JLabel {
    private Timer rollTimer;
    private ImageIcon[] diceIcons;
    private int currentDiceResult;

    public Dice() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // 주사위 이미지 경로 배열 (1부터 6까지)
        String[] imagePaths = {
                "SOURCE/Dice/dice1.png",
                "SOURCE/Dice/dice2.png",
                "SOURCE/Dice/dice3.png",
                "SOURCE/Dice/dice4.png",
                "SOURCE/Dice/dice5.png",
                "SOURCE/Dice/dice6.png"
        };

        diceIcons = new ImageIcon[6];

        for (int i = 0; i < 6; i++) {
            ImageIcon imageIcon = new ImageIcon(imagePaths[i]);
            diceIcons[i] = imageIcon;
        }

        // 타이머 초기화
        rollTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    public void rollDice() {
        int diceResult = (int) (Math.random() * 6) + 1;
        setIcon(diceIcons[diceResult - 1]);
        currentDiceResult = diceResult;
        repaint(); // 이미지 변경을 화면에 즉시 반영하기 위해 repaint() 호출
    }

    public Image getImage() {
        // 현재 보여지고 있는 아이콘의 이미지를 가져와서 반환
        return diceIcons[currentDiceResult].getImage();
    }


    public void startRolling() {
        rollTimer.start();
    }

    public void stopRolling() {
        rollTimer.stop();
        currentDiceResult = getCurrentDiceResult();
        setIcon(diceIcons[currentDiceResult - 1]); // 멈추면 현재 결과로 이미지 업데이트
        System.out.println("주사위 멈춤. 결과: " + currentDiceResult);
    }

    // 현재 보여지고 있는 주사위 결과를 가져오는 메서드
    private void getCurrentVisibleDiceResult() {
        for (int i = 0; i < diceIcons.length; i++) {
            // 배열에서 현재 보여지고 있는 이미지를 찾아 인덱스 반환
            if (((ImageIcon) diceIcons[i]).getImage() == ((ImageIcon) getIcon()).getImage()) {
                currentDiceResult = i + 1;
            }
        }
    }

    public int getCurrentDiceResult() {
        getCurrentVisibleDiceResult();
        return currentDiceResult;
    }
}