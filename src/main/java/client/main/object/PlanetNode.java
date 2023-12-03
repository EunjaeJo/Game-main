package client.main.object;

import java.awt.*;

public class PlanetNode extends Unit {
    private int id;
    private int coin; // 해당 행성에 할당되는 코인 수
    private boolean sun = false; // 해당 노드 태양 생성 여부
    private String name;

    public PlanetNode(int id, int x, int y, Image img, int coin) {
        this.id = id;
        this.posX = x;
        this.posY = y;
        this.img = img;
        this.coin = coin;
    }

    public PlanetNode(int x, int y, Image img) {
        this.posX = x;
        this.posY = y;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoin() {
        return coin;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setSun(boolean change) { // 메인에서 랜덤 돌려서 나온 노드에 sun 위치시킴
        sun = true;
    }

    public boolean isSun() {
        return sun;
    }

//    // 여러 노드를 지나가는 애니메이션을 처리하는 메서드
//    public void moveToNodeAnimation(GameUser player, List<PlanetNode> nodes, int animationDuration) {
//        for (PlanetNode targetNode : nodes) {
//            int targetX = targetNode.getPosX();
//            int targetY = targetNode.getPosY();
//
//            // 이동 애니메이션 처리 코드 (예시)
//            for (int i = 0; i < animationDuration; i++) {
//                int deltaX = (targetX - player.getPosX()) / animationDuration;
//                int deltaY = (targetY - player.getPosY()) / animationDuration;
//
//                player.setCurrentPosition(player.getPosX() + deltaX, player.getPosY() + deltaY);
//
//                try {
//                    Thread.sleep(80); // 적절한 sleep 시간을 설정
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // 화면 갱신을 위한 repaint() 호출
//                // repaint();
//                // 수정된 부분: 노드 클래스에서 repaint() 호출이 아니라 MainMapView에서 호출하도록 변경
//            }
//
//            // 이동이 완료된 후 플레이어의 현재 노드를 업데이트
//            player.setCurrentNode(targetNode);
//        }
//    }

}

