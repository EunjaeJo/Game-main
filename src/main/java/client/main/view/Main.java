package client.main.view;

import client.main.GameUser;
import client.main.RoomManager;
import client.main.member.Member;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {
    // 이미지 버퍼
    Image buffImg;
    Graphics buffG;

    public Main() {
        // 타이틀 설정
        setTitle("Solar System");
        // 창을 닫을 때 프로그램 종료 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 테스트용 객체 생성
        Member m1 = new Member("a", "a", "팽도리");
        Member m2 = new Member("b", "a", "이상해씨");
        Member m3 = new Member("c", "a", "파이리");
//        Member m4 = new Member("d", "a", "d");
        GameUser g1 = new GameUser(m1);
        GameUser g2 = new GameUser(m2);
        GameUser g3 = new GameUser(m3);
//        GameUser g4 = new GameUser(m4);
        ArrayList<GameUser> users = new ArrayList<>();
        users.add(g1);
        users.add(g2);
        users.add(g3);
//        users.add(g4);

        // MainMapView 객체 생성
        MainMapView mainMapView = new MainMapView(users, RoomManager.createRoom());

        // 프레임 크기 조절
        setSize(800, 800);
        // MainMapView를 프레임에 추가
        getContentPane().add(mainMapView);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        // 스레드 시작
        new Thread(mainMapView).start();
    }

    public static void main(String[] args) {
        // Main 객체 생성
//        SwingUtilities.invokeLater(() -> new Main());
        new Main();
    }
}
