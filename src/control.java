import java.util.Random;

public class control {
	Random rd = new Random();
	
	int[] center = {5, 1};
	int[][] add = {{0, 0}, {0, 0}, {0, 0}}; //[�� ����]�� �߽� �������κ��� [������/�Ʒ�]�� ��ĭ�� ������ �ִ°�
	
	int type = 7; 
	/*
	 * 0 : ����, 1 : ����, 2 : ����
	 * 3 : L��, 4 : L�� �¿��Ī
	 * 5 : ������, 6 : ������ �¿��Ī
	 */

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
	public control(control ctrl) {
		this.type = ctrl.type;
		for(int i = 0; i < 2; i++) {
			this.center[i] = ctrl.center[i];
			for(int j = 0; j < 3; j++) {
				this.add[j][i] = ctrl.add[j][i];
			}
		}
	}
	
	int state(int block, int dir) { return this.center[dir] + this.add[block][dir]; }

	boolean isValid()
	{
		for(int i = 0; i < 3; i++) {
			if( (this.state(i, 0) < 0) || (this.state(i, 0) > 9) || (this.state(i, 1) < 0) || (this.state(i, 1) > 19) ) return false;
		}
		
		return true;
	}

	void CW() {
		int temp;
		
		for(int i = 0; i < 3; i++) {
			temp = add[i][0];
			add[i][0] = -add[i][1];
			add[i][1] = temp;
		}
	}
	void ACW() {
		int temp;
		
		for(int i = 0; i < 3; i++) {
			temp = -add[i][0];
			add[i][0] = add[i][1];
			add[i][1] = temp;
		}
	}
}