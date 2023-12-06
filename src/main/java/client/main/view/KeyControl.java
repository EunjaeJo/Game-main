package client.main.view;

import client.main.GameUser;

import java.awt.event.KeyAdapter;

public class KeyControl extends KeyAdapter implements Runnable {

    GameUser user;
    MainMapView mainMapView;

    public KeyControl(GameUser user, MainMapView mainMapView) {
        this.user = user;
        this.mainMapView = mainMapView;
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
