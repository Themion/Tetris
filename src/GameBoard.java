import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class GameBoard extends Thread {
	//���� ��Ʈ���� �������� ���¸� ������ ��
	static boolean[][] board = new boolean[10][20];
	//��Ʈ���� �ǿ��� �����ϴ� ��
	static control ctrl;
	//��Ʈ���� ���� ����
	static int score = 0;
	//������ ���� ��� true�� �Ǵ� ����. run���� ���
	boolean ifSet = false;
	
	//�̹� ���� ���� �ڸ��� ��, ���� ���� �ִ� �ڸ��� +, �� �ڸ��� ��� ǥ��
	static void show() {
		System.out.println("\n\n\n\n");
		
		//���� ���� �ڸ��� ������
		control shadow = new control(ctrl);
		//���� �Ʒ��� ����ش�
		while(check(shadow) > 0) shadow.center[1] += 1;
		
		//��� ĭ�� ����
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				//�ش� ĭ�� �̹� ä���� �ִٸ� ��� ǥ��
				if(board[j][i] == true) System.out.print("��");
				
				//�ش� ĭ�� ����� �����Ѵٸ� +�� ǥ��
				else if((ctrl.center[0] == j) && (ctrl.center[1] == i)) System.out.print("+");
				else if((ctrl.state(0, 0) == j) && (ctrl.state(0, 1) == i)) System.out.print("+");
				else if((ctrl.state(1, 0) == j) && (ctrl.state(1, 1) == i)) System.out.print("+");																		
				else if((ctrl.state(2, 0) == j) && (ctrl.state(2, 1) == i)) System.out.print("+");

				//�ش� ĭ�� ����� ���� ���̶�� -�� ǥ��
				else if((shadow.center[0] == j) && (shadow.center[1] == i)) System.out.print("-");
				else if((shadow.state(0, 0) == j) && (shadow.state(0, 1) == i)) System.out.print("-");
				else if((shadow.state(1, 0) == j) && (shadow.state(1, 1) == i)) System.out.print("-");																		
				else if((shadow.state(2, 0) == j) && (shadow.state(2, 1) == i)) System.out.print("-");
				
				//�ش� ĭ�� ��������� ��� ǥ��
				else System.out.print("��");
			}
			
			System.out.println("");
		}
	}
	//�־��� ���� �ִ� �ڸ��� ���� ������ -1. 0. 1�� ��ȯ
	static int check(control ctrl) {
		/* ���ϰ��� -1, 0, 1 �� �ϳ�
		 * ���ϰ��� -1�� ��� ���� ���� board�� ��ħ or ���� �߻�.
		 * ���ϰ���  0�� ��� ���� �ٷ� �Ʒ��� ���� board�� ������. �ϰ� �Ұ���
		 * �� �̿��� ���� ���� ���ϰ��� 1
		 * */
		
		if(!ctrl.isValid()) return -1;
		
		for(int i = 0; i < 3; i++) {
			if(board[ctrl.state(i, 0)][ctrl.state(i, 1)]) return -1;
		}

		for(int i = 0; i < 3; i++) {
			if(ctrl.state(i, 1) > 18) return 0;
			if(board[ctrl.state(i, 0)][ctrl.state(i, 1) + 1]) return 0;
		}
		
		if((ctrl.center[1] <= 18) && (board[ctrl.center[0]][ctrl.center[1] + 1])) return 0;
		
		return 1;
	}
	//��� �� �ִ� ���� ���� �Լ�
	static void lineBreak() {
		//������ �μ��� ������ ���� ���ϴ� ����
		boolean noBreakLine;
		
		//�� �Ʒ� ���κ��� ���ʷ� Ȯ��
		for(int i = 19; i >= 0; i--){
			//noBreakLine �ʱ�ȭ
			noBreakLine = false;
			
			//�� ĭ�� �ϳ��� �ִٸ� �ش� ������ �μ��� ����
			for(int j = 0; j < 10; j++) if(!board[j][i]) noBreakLine = true; 
			
			//nobreakLine�� true�� continue
			if(noBreakLine) continue;
			//nobreakLine�� false��
			else {
				//�ش� ���� ���� ��� ���� �� ĭ �Ʒ��� ������
				for(int j = i; j > 0; j--) {
					for(int k = 0; k < 10; k++) {
						board[k][j] = board[k][j - 1];
					}
				}
				
				//�� �� ������ ���� false�� ä���
				for(int k = 0; k < 10; k++) {
					board[k][0] = false;
				}
				
				//������ 1�� ����
				score += 1;
				//�ش� ������ ��Ž���ϱ� ���� i�� 1 �߰�
				i += 1;
			}
		}
	}
	//����� 1�ʿ� �� ���� �� ĭ �Ʒ���
	@Override
	public void run() {
		//���� ���� �ð��� ����
		long curTime = System.currentTimeMillis();
		//score�� ���� ���̵��� ���̱� ���� ����
		int timeLimit;
		
		//������ ������ ���� ���
		while(!ifSet) {
			//���̵��� ������ ����
			timeLimit = 1000 - score;
			//�� Ŭ������
			if(System.currentTimeMillis() - curTime >= timeLimit) {
				//���� �� ĭ �Ʒ��� ���� ��
				ctrl.center[1] += 1;
				//���� ���¸� ���
				show();
				//curTime�� ����
				curTime = System.currentTimeMillis();
			}
		}
	}
	
	//������ main �Լ�
	void go() {
		System.out.println("W : �� �� �Ʒ���");
		System.out.println("A : �� �� ĭ ��������");
		System.out.println("S : �� �� ĭ ����������");
		System.out.println("D : �� �� ĭ �Ʒ���");
		System.out.println("Q : �� �ݽð���� ȸ��");
		System.out.println("E : �� �ð���� ȸ��");

		//��� ������ ���� �� ����� ���� �յ��ϰ� �������� ��
		int round[] = {0, 1, 2, 3, 4, 5, 6};
		//round�� �ε����� �� ����
		int target = 0;
		
		//round�� �����Ѵ�
		Collections.shuffle(Arrays.asList(round));
		//ctrl�� �� ���� ����
		ctrl = new control(round[target]);
		
		//ctrl�� �� ���¸� ��Ÿ���� ����
		int res;
		//ctrl�� ���� ����� ����
		char order = 'S';
		//order ������ ���� �Ѱ��ֱ� ���� ���� ����
		String input;
		//W Ŀ�ǵ带 �Է����� �� ����� boolean ����
		boolean ifW = false;
		
		Scanner scan = new Scanner(System.in);
		
		do {
			//���� ctrl�� ���¿� ���� �� �ൿ�� ���Ѵ�
			res = check(ctrl);
			
			//�ٴڿ� ������ board�� ���� ���� �߰��� �� �� ������ ���� �÷���
			if(res == 0) {
				//���� center�� board�� �߰��� ��
				board[ctrl.center[0]][ctrl.center[1]] = true;
				//���� ������ ������ ���� board�� �߰��Ѵ�
				for(int i = 0; i < 3; i++) board[ctrl.state(i, 0)][ctrl.state(i, 1)] = true;
				
				///target�� 6�̶�� round�� �ٽ� �����ϰ� target�� 0���� ����
				if(target == 6) {
					Collections.shuffle(Arrays.asList(round));
					target = 0;
				}
				
				//target�� 0���� 5 ���̶�� target�� 1�� ����
				else target += 1;
				
				//ifW �ʱ�ȭ
				ifW = false;
				//�� �ִ� ��� ����
				lineBreak();
				//���� board ���� ���
				show();
				
				ctrl = new control(target);
			}
			
			else if(res == 1) {
				//���� WŰ�� ������ �ʾҴٸ� Ű�� ���� �Է¹޴´�
				if(!ifW) {
					show();
					//String�� ���ڿ��� �Է¹ް�
					input = scan.next();
					//input�� �� ���ڸ��� Ŀ�ǵ�� ���
					order = input.charAt(0);
					
					//����� ��������
					if(input == "Quit") break;

					//�ҹ����� ��� �빮�ڷ� ����
					if((order >= 'a') && (order <= 'z')) order -= 32;
					//���� �� ���ڸ� �Է¹޾Ҵٸ� ����
					else if((order < 'A') || (order > 'Z')) break;
				}

				//���� WŰ�� �Է��ߴٸ� Ŀ�ǵ带 S�� ����
				else order = 'S';
				
				//order���� ���� ��� ����
				switch(order) {
					case('W'):
						while(check(ctrl) > 0) ctrl.center[1] += 1;
						break;
					
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
		
		ifSet = true;
		
		//������ ����Ǿ��� ��� ���� ���� �޼����� ���� ���
		System.out.println("Game Over!");
		System.out.println("Your score: " + score);
		
		scan.close();
	}
}