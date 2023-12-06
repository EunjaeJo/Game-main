package client.main.view;

import client.main.GameUser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControl extends MouseAdapter implements Runnable {

    GameUser user;
    MainMapView mainMapView;

    public MouseControl(GameUser user, MainMapView mainMapView) {
        this.user = user;
        this.mainMapView = mainMapView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 마우스 클릭 시 주사위 굴리기 시작 또는 멈춤
//        dice.startRolling();
//        repaint();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(20);
//                if(this.player.hp <= 0) {
//                    break;
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
