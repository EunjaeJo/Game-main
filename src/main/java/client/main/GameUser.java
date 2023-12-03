package client.main;

import client.main.member.Member;
import client.main.object.Item;
import client.main.object.PlanetNode;

import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameUser {
    private int id;
    private Member member;
    private String nickName;
    private Socket sock;
    private GameRoom room;
    private int enteredCode;
    Image img;
    Toolkit tk = Toolkit.getDefaultToolkit();
    private int posX, posY;
    private int initialX = 125;
    private int initialY = 125;
    private int width, height = 64;
    private int coin = 0; // 보유 코인 수
    private int sun = 0; // 보유 태양 수

    private PlanetNode currentNode; // 현재 노드 위치
    private int miniGameScore; // 미니게임 점수
    private ArrayList<Item> userItems; // 보유 아이템

    public GameUser(Member member) {
        this.member = member;
        this.nickName = member.getNickName();
        posX = initialX;
        posY = initialY;
    }

    /**
     * 방에 입장시킴
     *
     * @param room 입장할 방
     */
    public void enterRoom(GameRoom room) {
        this.room = room;
    }

    /**
     * 방에서 퇴장
     */
    public void exitRoom() {
        this.room = null;
    }

    public GameRoom getRoom() {
        return room;
    }

//    // 노드 이동 멤버 함수
//    public void moveNode(PlanetNode targetNode) {
//        posX = targetNode.getX();
//        posY = targetNode.getY();
//    }

    /**
     * 노드 이동 처리
     */
    // get 현재 노드 위치
    public PlanetNode getCurrentNode() {
        return currentNode;
    }

    // set 현재 노드 위치
    public void setCurrentNode(PlanetNode node) {
        this.currentNode = node;
    }

    // set 캐릭터 현재 좌표
    public void setCurrentPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // 노드 이동: 타겟 노드로 직접(한번에) 이동
    public void moveToNode(PlanetNode targetNode) {
        setCurrentNode(targetNode);
        setCurrentPosition(targetNode.getPosX(), targetNode.getPosY());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public int getEnteredCode() {
        return enteredCode;
    }

    public void setEnteredCode(int enteredCode) {
        this.enteredCode = enteredCode;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    } // 코인 설정

    // 코인 변경
    public void addCoin(int coin) {
        this.coin += coin;
        if (this.coin == 0)
            this.coin = 0;
    }

    public int getSun() {
        return sun;
    }

    public void addSun() {
        this.sun += 1;
    } // 태양 구매 시 업데이트

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    // get 미니게임 점수
    public int getMiniGameScore(){
        return miniGameScore;
    }

    // set 미니게임 점수
    public void setMiniGameScore(int miniGameScore){
        this.miniGameScore = miniGameScore;
    }

    public void addUserItem(Item item){
        this.userItems.add(item);
    }

    public void removeUserItem(Item item){
        this.userItems.remove(item);
    }

    public ArrayList<Item> getUserItems(){
        return userItems;
    }

    /*
                        equals와 hashCode를 override 해줘야, 동일유저를 비교할 수 있다
                        비교할 때 -> gameUser 간 equals 비교, list에서 find 등
                     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameUser gameUser = (GameUser) o;

        return id == gameUser.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
