import java.util.Random;

public class control {
	Random rd = new Random();
	
	//블록의 중심
	int[] center = {5, 1};
	//[각 블록]의 중심 블록과의 좌표 차
	int[][] add = {{0, 0}, {0, 0}, {0, 0}}; 
	
	/* 블록의 종류
	 * 0 : ㅡ자, 1 : ㅗ자, 2 : ㅁ자
	 * 3 : L자, 4 : L자 좌우대칭
	 * 5 : ㄱㄴ자, 6 : ㄱㄴ자 좌우대칭
	 */
	int type = 7; 

	//랜덤으로 블록 생성
	public control() {
		this.type = rd.nextInt(7);
		
		switch(this.type) {
			case(0):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 2;
				break;
				
			case(1):
				this.add[0][0] = -1;
				this.add[1][1] = -1;
				this.add[2][0] = 1;
				break;
				
			case(2):
				this.add[0][1] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 1;
				this.add[2][1] = -1;
				break;
				
			case(3):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = -1;
				this.add[2][1] = 1;
				break;
				
			case(4):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 1;
				this.add[2][1] = 1;
				break;
				
			case(5):
				this.add[0][0] = -1;
				this.add[1][1] = -1;
				this.add[2][0] = 1;
				this.add[2][1] = -1;
				break;
				
			case(6):
				this.add[0][0] = 1;
				this.add[1][1] = -1;
				this.add[2][0] = -1;
				this.add[2][1] = -1;
				break;
				
			default:;
		}
	}
	//set 번호의 블록 생성
	public control(int set) {
		this.type = set;
		
		switch(this.type) {
			case(0):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 2;
				break;
				
			case(1):
				this.add[0][0] = -1;
				this.add[1][1] = -1;
				this.add[2][0] = 1;
				break;
				
			case(2):
				this.add[0][1] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 1;
				this.add[2][1] = -1;
				break;
				
			case(3):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = -1;
				this.add[2][1] = 1;
				break;
				
			case(4):
				this.add[0][0] = -1;
				this.add[1][0] = 1;
				this.add[2][0] = 1;
				this.add[2][1] = 1;
				break;
				
			case(5):
				this.add[0][0] = -1;
				this.add[1][1] = -1;
				this.add[2][0] = 1;
				this.add[2][1] = -1;
				break;
				
			case(6):
				this.add[0][0] = 1;
				this.add[1][1] = -1;
				this.add[2][0] = -1;
				this.add[2][1] = -1;
				break;
				
			default:;
		}
	}
	//입력받은 블록의 복사본 생성
	public control(control ctrl) {
		this.type = ctrl.type;
		for(int i = 0; i < 2; i++) {
			this.center[i] = ctrl.center[i];
			for(int j = 0; j < 3; j++) {
				this.add[j][i] = ctrl.add[j][i];
			}
		}
	}
	
	//block 번의 dir 좌표 값을 출력
	int state(int block, int dir) { return this.center[dir] + this.add[block][dir]; }

	//블록이 테트리스 보드 안에 존재하는지를 boolean으로 출력
	boolean isValid()
	{
		for(int i = 0; i < 3; i++) {
			if( (this.state(i, 0) < 0) || (this.state(i, 0) > 9) || (this.state(i, 1) < 0) || (this.state(i, 1) > 19) ) return false;
		}
		
		return true;
	}

	//블록을 시계 방향으로 회전
	void CW() {
		int temp;
		
		for(int i = 0; i < 3; i++) {
			temp = add[i][0];
			add[i][0] = -add[i][1];
			add[i][1] = temp;
		}
	}
	//블록을 반시계방향으로 회전
	void ACW() {
		int temp;
		
		for(int i = 0; i < 3; i++) {
			temp = -add[i][0];
			add[i][0] = add[i][1];
			add[i][1] = temp;
		}
	}
}