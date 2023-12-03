package client.main.mainmap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dice extends JPanel {
    private Timer rollTimer;
    private ImageIcon[] diceIcons;
    private int currentDiceResult;
    private int width = 64, height = 64;

    public Dice() {
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

    public void setPosition(int x, int y) {
        setLocation(x, y);
    }

    public Image getImage() {
        // 현재 currentDiceResult를 유효한 범위 내로 조정하기 위해 모듈로 연산자 사용
        int index = (currentDiceResult - 1 + diceIcons.length) % diceIcons.length;
        return diceIcons[index].getImage();
    }


    public void rollDice() {
         int diceResult = (int) (Math.random() * 6) + 1;
//        int diceResult = 1; // 테스트용 1만 나옴
        currentDiceResult = diceResult;
        repaint(); // 이미지 변경을 화면에 즉시 반영하기 위해 repaint() 호출
    }

    public void startRolling() {
        rollTimer.start();
    }

    public void stopRolling() {
        rollTimer.stop();
        repaint(); // 이미지 변경을 화면에 즉시 반영하기 위해 repaint() 호출
        System.out.println("주사위 멈춤. 결과: " + currentDiceResult);
    }

    public int getDiceResult() {
        return currentDiceResult;
    }
}
