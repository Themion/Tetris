import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class GameBoard extends Thread {
	//현재 테트리스 게임판의 상태를 저장할 판
	static boolean[][] board = new boolean[10][20];
	//테트리스 판에서 조종하는 블럭
	static control ctrl;
	//테트리스 게임 점수
	static int score = 0;
	//게임이 끝난 경우 true가 되는 변수. run에서 사용
	boolean ifSet = false;
	
	//이미 블럭이 쌓인 자리는 ■, 현재 블럭이 있는 자리는 +, 빈 자리는 □로 표시
	static void show() {
		System.out.println("\n\n\n\n");
		
		//블럭이 놓일 자리를 보여줌
		control shadow = new control(ctrl);
		//블럭을 아래로 당겨준다
		while(check(shadow) > 0) shadow.center[1] += 1;
		
		//모든 칸에 대해
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 10; j++) {
				//해당 칸이 이미 채워져 있다면 ■로 표시
				if(board[j][i] == true) System.out.print("■");
				
				//해당 칸에 블록이 존재한다면 +로 표시
				else if((ctrl.center[0] == j) && (ctrl.center[1] == i)) System.out.print("+");
				else if((ctrl.state(0, 0) == j) && (ctrl.state(0, 1) == i)) System.out.print("+");
				else if((ctrl.state(1, 0) == j) && (ctrl.state(1, 1) == i)) System.out.print("+");																		
				else if((ctrl.state(2, 0) == j) && (ctrl.state(2, 1) == i)) System.out.print("+");

				//해당 칸에 블록이 놓일 것이라면 -로 표시
				else if((shadow.center[0] == j) && (shadow.center[1] == i)) System.out.print("-");
				else if((shadow.state(0, 0) == j) && (shadow.state(0, 1) == i)) System.out.print("-");
				else if((shadow.state(1, 0) == j) && (shadow.state(1, 1) == i)) System.out.print("-");																		
				else if((shadow.state(2, 0) == j) && (shadow.state(2, 1) == i)) System.out.print("-");
				
				//해당 칸이 비어있으면 □로 표시
				else System.out.print("□");
			}
			
			System.out.println("");
		}
	}
	//주어진 블럭이 있는 자리에 대한 정보를 -1. 0. 1로 반환
	static int check(control ctrl) {
		/* 리턴값은 -1, 0, 1 중 하나
		 * 리턴값이 -1일 경우 블럭과 기존 board가 겹침 or 에러 발생.
		 * 리턴값이  0일 경우 블럭의 바로 아래에 기존 board가 존재함. 하강 불가능
		 * 그 이외의 경우는 전부 리턴값이 1
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
	//모두 차 있는 행을 비우는 함수
	static void lineBreak() {
		//라인을 부술지 내버려 둘지 정하는 변수
		boolean noBreakLine;
		
		//맨 아래 라인부터 차례로 확인
		for(int i = 19; i >= 0; i--){
			//noBreakLine 초기화
			noBreakLine = false;
			
			//빈 칸이 하나라도 있다면 해당 라인을 부수지 않음
			for(int j = 0; j < 10; j++) if(!board[j][i]) noBreakLine = true; 
			
			//nobreakLine이 true면 continue
			if(noBreakLine) continue;
			//nobreakLine이 false면
			else {
				//해당 라인 위의 모든 블럭을 한 칸 아래로 내린다
				for(int j = i; j > 0; j--) {
					for(int k = 0; k < 10; k++) {
						board[k][j] = board[k][j - 1];
					}
				}
				
				//맨 위 라인을 전부 false로 채운다
				for(int k = 0; k < 10; k++) {
					board[k][0] = false;
				}
				
				//점수에 1점 가점
				score += 1;
				//해당 라인을 재탐색하기 위해 i에 1 추가
				i += 1;
			}
		}
	}
	//블록을 1초에 한 번씩 한 칸 아래로
	@Override
	public void run() {
		//게임 시작 시간을 저장
		long curTime = System.currentTimeMillis();
		//score에 따라 난이도를 높이기 위한 변수
		int timeLimit;
		
		//게임이 끝나지 않은 경우
		while(!ifSet) {
			//난이도를 설정한 다음
			timeLimit = 1000 - score;
			//매 클럭마다
			if(System.currentTimeMillis() - curTime >= timeLimit) {
				//블럭을 한 칸 아래로 내린 뒤
				ctrl.center[1] += 1;
				//현재 상태를 출력
				show();
				//curTime을 갱신
				curTime = System.currentTimeMillis();
			}
		}
	}
	
	//실질적 main 함수
	void go() {
		System.out.println("W : 블럭 맨 아래로");
		System.out.println("A : 블럭 한 칸 왼쪽으로");
		System.out.println("S : 블럭 한 칸 오른쪽으로");
		System.out.println("D : 블럭 한 칸 아래로");
		System.out.println("Q : 블럭 반시계방향 회전");
		System.out.println("E : 블럭 시계방향 회전");

		//모든 종류의 블럭을 한 덩어리로 묶어 균등하게 나오도록 함
		int round[] = {0, 1, 2, 3, 4, 5, 6};
		//round의 인덱스로 쓸 변수
		int target = 0;
		
		//round를 셔플한다
		Collections.shuffle(Arrays.asList(round));
		//ctrl에 새 블럭을 지정
		ctrl = new control(round[target]);
		
		//ctrl의 현 상태를 나타내는 변수
		int res;
		//ctrl에 내릴 명령을 저장
		char order = 'S';
		//order 변수에 값을 넘겨주기 위한 더미 변수
		String input;
		//W 커맨드를 입력했을 때 사용할 boolean 변수
		boolean ifW = false;
		
		Scanner scan = new Scanner(System.in);
		
		do {
			//현재 ctrl의 상태에 따라 할 행동을 정한다
			res = check(ctrl);
			
			//바닥에 닿으면 board에 기존 블럭을 추가한 뒤 새 블럭으로 게임 플레이
			if(res == 0) {
				//블럭의 center를 board에 추가한 뒤
				board[ctrl.center[0]][ctrl.center[1]] = true;
				//블럭의 나머지 조각들 또한 board에 추가한다
				for(int i = 0; i < 3; i++) board[ctrl.state(i, 0)][ctrl.state(i, 1)] = true;
				
				///target이 6이라면 round를 다시 셔플하고 target을 0으로 지정
				if(target == 6) {
					Collections.shuffle(Arrays.asList(round));
					target = 0;
				}
				
				//target이 0부터 5 사이라면 target에 1을 더함
				else target += 1;
				
				//ifW 초기화
				ifW = false;
				//차 있는 블록 제거
				lineBreak();
				//현재 board 상태 출력
				show();
				
				ctrl = new control(target);
			}
			
			else if(res == 1) {
				//전에 W키를 누르지 않았다면 키를 새로 입력받는다
				if(!ifW) {
					show();
					//String에 문자열을 입력받고
					input = scan.next();
					//input의 맨 앞자리를 커맨드로 사용
					order = input.charAt(0);
					
					//디버깅 강제종료
					if(input == "Quit") break;

					//소문자일 경우 대문자로 변경
					if((order >= 'a') && (order <= 'z')) order -= 32;
					//범위 외 문자를 입력받았다면 종료
					else if((order < 'A') || (order > 'Z')) break;
				}

				//전에 W키를 입력했다면 커맨드를 S로 고정
				else order = 'S';
				
				//order값에 따라 블록 조작
				switch(order) {
					case('W'):
						while(check(ctrl) > 0) ctrl.center[1] += 1;
						break;
					
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
		
		ifSet = true;
		
		//게임이 종료되었을 경우 게임 오버 메세지와 점수 출력
		System.out.println("Game Over!");
		System.out.println("Your score: " + score);
		
		scan.close();
	}
}