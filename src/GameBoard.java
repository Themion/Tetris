import java.util.Scanner;
import java.util.Random;

public class GameBoard {
	static boolean[][] board = new boolean[10][20];
	static control ctrl = new control();
	static control temp_ctrl;
	static int score = 0;
	
	//�̹� ���� ���� �ڸ��� ��, ���� ���� �ִ� �ڸ��� +, �� �ڸ��� ��� ǥ��
	static void show(){
		System.out.println("\n\n\n\n");
		
		temp_ctrl = new control(ctrl);
		while(check(temp_ctrl) > 0) temp_ctrl.center[1] += 1;
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[j][i] == true) System.out.print("��");
				
				else if((ctrl.center[0] == j) && (ctrl.center[1] == i)) System.out.print("+");
				else if((ctrl.state(0, 0) == j) && (ctrl.state(0, 1) == i)) System.out.print("+");
				else if((ctrl.state(1, 0) == j) && (ctrl.state(1, 1) == i)) System.out.print("+");																		
				else if((ctrl.state(2, 0) == j) && (ctrl.state(2, 1) == i)) System.out.print("+");
				
				else if((temp_ctrl.center[0] == j) && (temp_ctrl.center[1] == i)) System.out.print("-");
				else if((temp_ctrl.state(0, 0) == j) && (temp_ctrl.state(0, 1) == i)) System.out.print("-");
				else if((temp_ctrl.state(1, 0) == j) && (temp_ctrl.state(1, 1) == i)) System.out.print("-");																		
				else if((temp_ctrl.state(2, 0) == j) && (temp_ctrl.state(2, 1) == i)) System.out.print("-");
				
				else System.out.print("��");
			}
			
			System.out.println("");
		}
	}
	//�־��� ���� �ִ� �ڸ��� ���� ������ -1. 0. 1�� ��ȯ
	static int check(control cntrl) {
		/* ���ϰ��� -1, 0, 1 �� �ϳ�
		 * ���ϰ��� -1�� ��� ���� ���� board�� ��ħ or ���� �߻�.
		 * ���ϰ���  0�� ��� ���� �ٷ� �Ʒ��� ���� board�� ������. �ϰ� �Ұ���
		 * �� �̿��� ���� ���� ���ϰ��� 1
		 * */
		
		if(!cntrl.isValid()) return -1;
		
		for(int i = 0; i < 3; i++) {
			if(board[cntrl.state(i, 0)][cntrl.state(i, 1)]) return -1;
		}

		for(int i = 0; i < 3; i++) {
			if(cntrl.state(i, 1) > 18) return 0;
			if(board[cntrl.state(i, 0)][cntrl.state(i, 1) + 1]) return 0;
		}
		
		if((cntrl.center[1] <= 18) && (board[cntrl.center[0]][cntrl.center[1] + 1])) return 0;
		
		return 1;
	}
	//��� �� �ִ� ���� ���� �Լ�
	static void lineBreak() {
		boolean noBreakLine;
		
		//�� �Ʒ� ���κ��� ���ʷ� Ȯ��
		for(int i = 19; i >= 0; i--){
			noBreakLine = false;
			
			for(int j = 0; j < 10; j++){
				if(!board[j][i]) noBreakLine = true;
			}
			
			if(noBreakLine) continue;
			else {
				for(int j = i; j > 0; j--)
				{
					for(int k = 0; k < 10; k++) {
						board[k][j] = board[k][j - 1];
					}
				}
				
				for(int k = 0; k < 10; k++) {
					board[k][0] = false;
				}
				
				score += 1;
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("W : �� �� �Ʒ���");
		System.out.println("A : �� �� ĭ ��������");
		System.out.println("S : �� �� ĭ ����������");
		System.out.println("D : �� �� ĭ �Ʒ���");
		System.out.println("Q : �� �ݽð���� ȸ��");
		System.out.println("E : �� �ð���� ȸ��");
		
		Random rd = new Random();
		ctrl = new control(rd.nextInt(5));
		
		int res = check(ctrl);
		char order = 'S';
		String input;
		boolean ifW = false;
		Scanner scan = new Scanner(System.in);

		do {
			res = check(ctrl);
			
			//�ٴڿ� ������ board�� ���� ���� �߰��� �� �� ������ ���� �÷���
			if(res == 0) {
				//���� center�� board�� �߰��� ��
				board[ctrl.center[0]][ctrl.center[1]] = true;
				//���� ������ ������ ���� board�� �߰��Ѵ�
				for(int i = 0; i < 3; i++) board[ctrl.state(i, 0)][ctrl.state(i, 1)] = true;
				ifW = false;
				show();
				lineBreak();
				
				ctrl = new control();
			}
			
			else if(res == 1) {
				//���� WŰ�� ������ �ʾҴٸ� Ű�� ���� �Է¹޴´�
				if(!ifW) {
					show();
					//String�� ���ڿ��� �Է¹ް�
					input = scan.next();
					//input�� �� ���ڸ��� Ŀ�ǵ�� ���
					order = input.charAt(0);
					
					//������ ��������
					if(input == "Quit") break;

					//�ҹ����� ��� �빮�ڷ� ����
					if((order >= 'a') && (order <= 'z')) order -= 32;
					//���� �� ���ڸ� �Է¹޾Ҵٸ� ����
					else if((order < 'A') || (order > 'Z')) break;
				}

				//���� WŰ�� �Է��ߴٸ� Ŀ�ǵ带 S�� ����
				else order = 'S';
				
				switch(order) {
					case('W'):
						ifW = true;
					
					case('S'):
						//���� �߽��� �� ĭ �Ʒ��� �̵�
						ctrl.center[1] += 1;
						break;
					
					case('A'):
						//���� �߽��� �� ĭ �������� �̵�
						ctrl.center[0] -= 1;
						//�Ұ����� ��� �ٽ� ���������� �̵�
						if(check(ctrl) == -1) ctrl.center[0] += 1;
						break;
					
					case('D'):
						//���� �߽��� �� ĭ ���������� �̵�
						ctrl.center[0] += 1;
						//�Ұ����� ��� �ٽ� �������� �̵�
						if(check(ctrl) == -1) ctrl.center[0] -= 1;
						break;
						
					case('Q'):
						//���� �ݽð�������� 90�� ȸ��
						ctrl.ACW();
						//�Ұ����� ��� ���� �ð�������� 90�� ȸ��
						if(check(ctrl) == -1) ctrl.CW();
						break;
						
					case('E'):
						//���� �ð�������� 90�� ȸ��
						ctrl.CW();
						//�Ұ����� ��� ���� �ݽð�������� 90�� ȸ��
						if(check(ctrl) == -1) ctrl.ACW();
						break;
				}
			}
		} while(res >= 0);
		
		System.out.println("Game Over!");
		System.out.println("Your score: " + score);
		
		scan.close();
	}
}