import java.util.Scanner;
import java.util.Random;

public class GameBoard {
	static boolean[][] board = new boolean[10][20];
	static control ctrl = new control();
	static control temp_ctrl;
	static int score = 0;
	
	//이미 블럭이 쌓인 자리는 ■, 현재 블럭이 있는 자리는 +, 빈 자리는 □로 표시
	static void show(){
		System.out.println("\n\n\n\n");
		
		temp_ctrl = new control(ctrl);
		while(check(temp_ctrl) > 0) temp_ctrl.center[1] += 1;
		
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[j][i] == true) System.out.print("■");
				
				else if((ctrl.center[0] == j) && (ctrl.center[1] == i)) System.out.print("+");
				else if((ctrl.state(0, 0) == j) && (ctrl.state(0, 1) == i)) System.out.print("+");
				else if((ctrl.state(1, 0) == j) && (ctrl.state(1, 1) == i)) System.out.print("+");																		
				else if((ctrl.state(2, 0) == j) && (ctrl.state(2, 1) == i)) System.out.print("+");
				
				else if((temp_ctrl.center[0] == j) && (temp_ctrl.center[1] == i)) System.out.print("-");
				else if((temp_ctrl.state(0, 0) == j) && (temp_ctrl.state(0, 1) == i)) System.out.print("-");
				else if((temp_ctrl.state(1, 0) == j) && (temp_ctrl.state(1, 1) == i)) System.out.print("-");																		
				else if((temp_ctrl.state(2, 0) == j) && (temp_ctrl.state(2, 1) == i)) System.out.print("-");
				
				else System.out.print("□");
			}
			
			System.out.println("");
		}
	}
	//주어진 블럭이 있는 자리에 대한 정보를 -1. 0. 1로 반환
	static int check(control cntrl) {
		/* 리턴값은 -1, 0, 1 중 하나
		 * 리턴값이 -1일 경우 블럭과 기존 board가 겹침 or 에러 발생.
		 * 리턴값이  0일 경우 블럭의 바로 아래에 기존 board가 존재함. 하강 불가능
		 * 그 이외의 경우는 전부 리턴값이 1
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
	//모두 차 있는 행을 비우는 함수
	static void lineBreak() {
		boolean noBreakLine;
		
		//맨 아래 라인부터 차례로 확인
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
		System.out.println("W : 블럭 맨 아래로");
		System.out.println("A : 블럭 한 칸 왼쪽으로");
		System.out.println("S : 블럭 한 칸 오른쪽으로");
		System.out.println("D : 블럭 한 칸 아래로");
		System.out.println("Q : 블럭 반시계방향 회전");
		System.out.println("E : 블럭 시계방향 회전");
		
		Random rd = new Random();
		ctrl = new control(rd.nextInt(5));
		
		int res = check(ctrl);
		char order = 'S';
		String input;
		boolean ifW = false;
		Scanner scan = new Scanner(System.in);

		do {
			res = check(ctrl);
			
			//바닥에 닿으면 board에 기존 블럭을 추가한 뒤 새 블럭으로 게임 플레이
			if(res == 0) {
				//블럭의 center를 board에 추가한 뒤
				board[ctrl.center[0]][ctrl.center[1]] = true;
				//블럭의 나머지 조각들 또한 board에 추가한다
				for(int i = 0; i < 3; i++) board[ctrl.state(i, 0)][ctrl.state(i, 1)] = true;
				ifW = false;
				show();
				lineBreak();
				
				ctrl = new control();
			}
			
			else if(res == 1) {
				//전에 W키를 누르지 않았다면 키를 새로 입력받는다
				if(!ifW) {
					show();
					//String에 문자열을 입력받고
					input = scan.next();
					//input의 맨 앞자리를 커맨드로 사용
					order = input.charAt(0);
					
					//디버깅용 강제종료
					if(input == "Quit") break;

					//소문자일 경우 대문자로 변경
					if((order >= 'a') && (order <= 'z')) order -= 32;
					//범위 외 문자를 입력받았다면 종료
					else if((order < 'A') || (order > 'Z')) break;
				}

				//전에 W키를 입력했다면 커맨드를 S로 고정
				else order = 'S';
				
				switch(order) {
					case('W'):
						ifW = true;
					
					case('S'):
						//블럭의 중심을 한 칸 아래로 이동
						ctrl.center[1] += 1;
						break;
					
					case('A'):
						//블럭의 중심을 한 칸 왼쪽으로 이동
						ctrl.center[0] -= 1;
						//불가능할 경우 다시 오른쪽으로 이동
						if(check(ctrl) == -1) ctrl.center[0] += 1;
						break;
					
					case('D'):
						//블럭의 중심을 한 칸 오른쪽으로 이동
						ctrl.center[0] += 1;
						//불가능할 경우 다시 왼쪽으로 이동
						if(check(ctrl) == -1) ctrl.center[0] -= 1;
						break;
						
					case('Q'):
						//블럭을 반시계방향으로 90도 회전
						ctrl.ACW();
						//불가능할 경우 블럭을 시계방향으로 90도 회전
						if(check(ctrl) == -1) ctrl.CW();
						break;
						
					case('E'):
						//블럭을 시계방향으로 90도 회전
						ctrl.CW();
						//불가능할 경우 블럭을 반시계방향으로 90도 회전
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