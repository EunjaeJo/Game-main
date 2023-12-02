package client.main.view;

import client.main.GameRoom;
import client.main.GameUser;
import client.main.mainmap.Dice;
import client.main.member.Member;
import client.main.object.PlanetNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class MainMapView extends JPanel implements Runnable {

    int frameWidth = 800; // Panel 폭
    int frameHeight = 800; // Panel 넓이
    int gridSize = 70; // 각 이미지의 크기
    int gridCount = 5;  // 격자의 행과 열 개수
    int gap = 50;       // 이미지 간격

    Dice dice; // 주사위 객체 생성
    Member member; // 멤버 정보 받아오기
    GameUser user; // 플레이어 정보 받아오기
    Thread th; // KeyAdapter 쓰레드
    boolean checkExit; // JFrame 종료 여부

    // 이미지를 불러오는 역할 , 더블 버퍼.
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image buffImg;
    Graphics buffG;

    Image background_ = tk.getImage("SOURCE/bg0.png"); // 배경화면;
    Image planetImages[] = new Image[16];
    String playerImgPath = "SOURCE/Players/playerImg"; // 플레이어 이미지들 경로
    Image playerImages[] = new Image[3];
    int scaledWidth = 800;
    int scaledHeight = 800;
    Image background = background_.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

    ArrayList<PlanetNode> nodes = new ArrayList<>();
    // 각 노드별 코인 정보 저장 배열
    int[] coinInfo = {3, -3, 3, 3, -3, 3, 3, 3, -3, 3, 3, 3, -3, 3, -3, 3};

    GameRoom room;
    ArrayList<GameUser> users = new ArrayList<>();

    GameUser turnPlayer; //현재 자신의 차례인 플레이어
    HashMap<Integer, Integer> turnInfo = new HashMap<>(); // key : 현재 턴 값 value : 해당 턴 주사위 던진 플레이어 수

    // 주사위 결과에 따라 이동할 타겟 노드 계산
    private PlanetNode calculateTargetNode(GameUser player, int diceResult) {
        int targetNodeId = (player.getCurrentNode().getId() + diceResult - 1) % 16 + 1;
        return findNodeById(targetNodeId);
    }

    // 노드 ID로 노드를 찾는 메서드
    private PlanetNode findNodeById(int nodeId) {
        for (PlanetNode node : nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }

    // 주사위 결과에 따라 플레이어 이동 처리
    public void handleMoveToNode(GameUser player) {
        int diceResult = dice.getDiceResult();
        PlanetNode targetNode = calculateTargetNode(player, diceResult);
        player.moveToNode(targetNode); // 타겟노드로 직접 이동
        repaint();
    }

    // 플레이어가 밟아갈 중간 노드들을 가져오는 메서드
    private List<PlanetNode> getNonTargetNodes(PlanetNode startNode, PlanetNode targetNode) {
        List<PlanetNode> nonTargetNodes = new ArrayList<>();

        // 시작 노드부터 타겟 노드까지의 모든 중간 노드를 가져옴
        int startNodeId = startNode.getId();
        int targetNodeId = targetNode.getId();

        // 시계 방향으로 노드를 가져오는 예시
        if (startNodeId <= targetNodeId) {
            for (int nodeId = startNodeId; nodeId < targetNodeId; nodeId++) {
                nonTargetNodes.add(findNodeById(nodeId));
            }
        } else {
            // 반시계 방향으로 노드를 가져오는 예시
            for (int nodeId = startNodeId; nodeId <= 16; nodeId++) {
                nonTargetNodes.add(findNodeById(nodeId));
            }
            for (int nodeId = 1; nodeId < targetNodeId; nodeId++) {
                nonTargetNodes.add(findNodeById(nodeId));
            }
        }

        return nonTargetNodes;
    }


    /**
     * 생성자 함수
     */
    public MainMapView(ArrayList<GameUser> users, GameRoom room) {
        setSize(frameWidth, frameHeight);
        setVisible(true);

        this.users = users;

        this.room = room;
        this.checkExit = false;
//        turnPlayer = room.getGameOwner(); // 게임 시작시 방장부터 시작
        // (테스트) 임의의 현재 플레이어: users의 첫 번째 플레이어
        turnPlayer = users.get(0);
        turnInfo.put(0, 0);


        //플레이어 키 입력 스레드
//        KeyControl key = new KeyControl(turnPlayer, this);
//        th = new Thread(key);
//        th.start();

        //플레이어 마우스 입력 스레드
        MouseControl mouse = new MouseControl(turnPlayer, this);
        th = new Thread(mouse);
        th.start();


        // 테두리 이미지 배치
        int totalSize = gridSize * gridCount + gap * (gridCount - 1);
        int startX = (800 - totalSize) / 2;
        int startY = (800 - totalSize) / 2;
        int distY = 0;

        // 행성 노드 생성 및 추가
        for (int i = 0; i < 16; i++) {
            String path = "SOURCE/Planets/planet" + ((i % 9) + 1) + ".png";
            Image img = tk.getImage(path);
            planetImages[i] = resizeImage(img, 70, 70);
        }

        // 노드 index 0~4
        for (int row = 0; row < gridCount; row++) {
            for (int col = 0; col < gridCount; col++) {
                int index = row * gridCount + col;
                if (index < 5) {
                    int x = startX + col * (gridSize + gap);
                    int y = startY + row * (gridSize + gap);
                    //g.drawImage(planetImages[index], x, y, gridSize, gridSize, null);
                    nodes.add(new PlanetNode(index + 1, x, y, planetImages[index], coinInfo[index]));
                }
            }
        }

        // 노드 index 5 ~ 10
        int index = 5;
        for (int row = 1; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                if (index % 2 == 1) {
                    int x = startX;
                    int y = startY + row * (gridSize + gap);
                    //g.drawImage(planetImages[index], x, y, gridSize, gridSize, null);
                    nodes.add(new PlanetNode(index + 1, x, y, planetImages[index], coinInfo[index]));
                } else {
                    int x = startX + 4 * (gridSize + gap);
                    int y = startY + row * (gridSize + gap);
                    //g.drawImage(planetImages[index], x, y, gridSize, gridSize, null);
                    nodes.add(new PlanetNode(index + 1, x, y, planetImages[index], coinInfo[index]));
                }
                index++;
            }
        }

        // 노드 index 11~15
        int dist = 0;
        for (int col = 11; col < 16; col++) {
            int x = startX + dist++ * (gridSize + gap);
            int y = startY + 4 * (gridSize + gap);
            //g.drawImage(planetImages[col], x, y, gridSize, gridSize, null);
            nodes.add(new PlanetNode(col + 1, x, y, planetImages[0 + (col - 10)], coinInfo[index]));
        }

        // 노드 넘버링 수정
        nodes.get(5).setId(16);
        nodes.get(7).setId(15);
        nodes.get(9).setId(14);
        nodes.get(11).setId(13);
        nodes.get(13).setId(11);
        nodes.get(14).setId(10);
        nodes.get(15).setId(9);
        nodes.get(10).setId(8);
        nodes.get(8).setId(7);


        // 플레이어별 이미지 설정 및 현재 노드 초기화 (플레이어 수: 3)
        for (int i = 0; i < 3; i++) {
            GameUser u = users.get(i);
            PlanetNode node = nodes.get(i);
            String imagePath = playerImgPath + (i + 1) + ".png";
            Image image = tk.getImage(imagePath);
            playerImages[i] = resizeImage(image, 64, 64);
            u.setImg(playerImages[i]);
            u.setCurrentNode(node); // 현재 노드 첫 번째 노드로 초기화
        }


//         // 마우스 이벤트 리스너
//        addMouseListener(mouse);

// 주사위 패널 생성 및 설정
        dice = new Dice();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 클릭 시 주사위를 굴린다.
                dice.startRolling();
                dice.rollDice();

                // 1초 후 주사위 멈춤 (1000ms = 1초)
                Timer stopTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        dice.stopRolling();

                        // 주사위 멈춘 후 주사위 결과에 따라 플레이어 이동 처리
                        handleMoveToNode(turnPlayer);
                    }
                });
                stopTimer.setRepeats(false); // 타이머 한 번만 실행
                stopTimer.start();
            }
        });
        dice.setBounds(370, 370, 64, 64);
        add(dice);

    }

    // 이미지 크기 조절 메서드
    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        buffImg = createImage(getWidth(), getHeight());
        buffG = buffImg.getGraphics();
        update(buffG);
        g.drawImage(buffImg, 0, 0, this);
        if (turnInfo.size() >= 17 && turnInfo.get(16) >= 4) {
            return;
        }
        repaint();
        g.drawImage(dice.getImage(), 370, 370, 64, 64, this); // 주사위 그림

    }

    // 요소들 그림
    public void update(Graphics g) {
        drawBackground(g);
        drawNodes(g);
        drawGameUsers(g);
    }

    private void drawGameUsers(Graphics g) {
        for (int i = 0; i < users.size(); i++) {
            GameUser u = users.get(i);
            buffG.drawImage(u.getImg(), u.getPosX(), u.getPosY(), this);
        }
    }

    private void drawNodes(Graphics g) {
        for (int i = 0; i < nodes.size(); i++) {
            PlanetNode node = nodes.get(i);
            buffG.drawImage(node.getImg(), node.getPosX(), node.getPosY(), this);
        }
    }

    private void drawBackground(Graphics g) {
        buffG.clearRect(0, 0, frameWidth, frameHeight);
        buffG.drawImage(background, 0, 0, this);
    }

    @Override
    public void run() {
        // 게임 진행시 main 스레드를 join으로 묶어둔다.
        while (true) {
            if (this.checkExit == true)
                break;
            else {
                System.out.println("");

                try {
                    Thread.sleep(10); // 적절한 sleep 시간을 설정
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
