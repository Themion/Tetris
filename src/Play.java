public class Play {
	public static void main(String[] args) {
		//게임을 플레이할 보드
		GameBoard board = new GameBoard();
		//매 초마다 블럭을 움직이고 그에 따른 상태를 출력
		board.start();
		//게임 시작
		board.go();
	}
}