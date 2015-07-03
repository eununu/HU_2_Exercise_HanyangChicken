import java.util.Scanner;
import java.util.ArrayList;

class Order
{
	int no, money= 0;
	String name;
	int menuCnt[]= new int[4];
	int menuMoney[]= {16000,18000,21000,22000};
	
	Order(int no, String n, int cnt[])
	{
		this.no= no;
		this.name= n;
		this.menuCnt= cnt;
		for(int i=0;i<4;i++)
		{
			money+= menuMoney[i]*cnt[i];
		}
	}
	
	void print() //toString?
	{
		System.out.printf("%-5d%-10s%-3d%-3d%-3d%-3d%-5d\n",no,name,menuCnt[0],menuCnt[1],menuCnt[2],menuCnt[3],money);
	}
	
	int getMoney()
	{
		return money;
	}
}

public class OrderManager {

	Scanner s= new Scanner(System.in);
	ArrayList<Order> u= new ArrayList<Order>();
	int count= 1;
	int totalOrderChic[]= new int[4];
	int totalMoney= 0;
	int menuMoney[]= {16000,18000,21000,22000};
	
	void test() //시험데이터
	{
		String testname[]={"폴리","엠버","로이","헬리","둘리","또치","희동이","도우너","고길동","홍길동"};
		int testorder[][]={{1,0,0,0},
				{0,1,0,0},
				{0,0,1,0},
				{0,0,0,1},
				{1,1,0,0},
				{0,0,2,0},
				{1,0,0,1},
				{0,0,0,5},
				{2,2,2,2},
				{3,0,3,0}};
		
		for(int i=0;i<10;i++)
		{
			u.add(new Order(count++,testname[i],testorder[i]));
			
			for(int j=0;j<4;j++)
			{
				totalMoney+= testorder[i][j]*menuMoney[j];
				totalOrderChic[j]+= testorder[i][j];
			}
		}
	}
	
	void start() //관리작업진행
	{
		int n,findsth,search;
		String findsths;
		
		while(true)
		{
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.print("1)주문추가 2)주문취소 3)주문서 보기 4)주문서 찾기 5)판매현황보기 6)베스트고객 7)종료 = > ");
			n= s.nextInt();
			System.out.println("-------------------------------------------------------------------------------------------");
			if(n== 7)
			{
				System.out.println("종료되었습니다.");
				break;
			}
			else if(n== 1) addOrder();
			else if(n== 2) cancelOrder();
			else if(n== 3) printOrder();
			else if(n== 4) 
			{
				System.out.print("메뉴번호로 검색하시려면 1번 고객이름으로 검색하시려면 2번을 입력해 주세요 : ");
				search= s.nextInt();
				if(search== 1)
				{
					System.out.print("검색할 메뉴번호를 입력해 주세요(1:순살 2:양념 3:파닭 4:오븐 100이상:일정금액 이상) : ");
					findsth= s.nextInt();
					findOrder(findsth);
				}
				else
				{
					System.out.print("검색할 고객이름을 입력해 주세요 : ");
					findsths= s.next();
					findOrder(findsths);
				}
			}
			else if(n== 5) viewStatus();
			else findBestCustomer();
			System.out.println();
		}
	}
	
	void addOrder()
	{
		String name;
		int orderChic[]= new int[4];
		System.out.print("주문자의 성함을 입력해 주세요 : ");
		name= s.next();
		System.out.print("순살, 양념, 파닭, 오븐 통닭의 개수를 입력해주세요 : ");
		
		for(int i=0;i<4;i++)
		{
			orderChic[i]= s.nextInt();
			totalOrderChic[i]+= orderChic[i];
		}
		u.add(new Order(count,name,orderChic));
		totalMoney+= u.get(u.size()-1).getMoney();
		count++;
	}
	
	void cancelOrder()
	{
		int no,t= 0;
		System.out.print("취소할 주문번호를 입력해 주세요 : ");
		no= s.nextInt();
		for(int i=0;i<u.size();i++)
		{
			if(u.get(i).no== no) 
			{
				t=i;
				break;
			}
		}
		for(int i=0;i<4;i++)
		{
			totalOrderChic[i]-=u.get(t).menuCnt[i];
		}
		totalMoney-= u.get(t).getMoney();
		u.remove(t);
		
	}
	
	void printOrder()
	{
		print();
		for(int i=0;i<u.size();i++)
		{
			u.get(i).print();
		}
	}

	void findOrder(int n) //주문서찾기 메뉴번호로
	{
		if(n>=1 && n<=4)		
		{
			print();
			for(int i=0;i<u.size();i++)
			{
				if(u.get(i).menuCnt[n-1]> 0) u.get(i).print();
			}
		}
		else
		{
			if(n>= 100)
			{
				System.out.print("금액을 입력하세요. (현재금액이상으로 주문한 주문서만 출력됩니다.) : ");
				int standard= s.nextInt();
				print();
				for(int i=0;i<u.size();i++)
				{
					if(u.get(i).getMoney() >= standard) u.get(i).print();
				}
			}
			else System.out.println("입력하신 메뉴번호는 존재하지 않습니다.");
		}
	}
	
	void findOrder(String n) //주문서찾기 이름으로
	{
		print();
		for(int i=0;i<u.size();i++)
		{
			if(u.get(i).name.matches(".*"+n+".*")==true) u.get(i).print();
		}
	}
	
	void viewStatus() //판매현황
	{
		System.out.printf("총 주문서 개수 : %d\n",u.size());
		System.out.printf("%-3s%-3s%-3s%-3s\n","순살","양념","파닭","오븐");
		for(int i=0;i<4;i++)
		{
			System.out.printf("%-3s",totalOrderChic[i]);
		}
		System.out.printf("\n총 주문 금액 : %d\n",totalMoney);
	}
	
	void print()
	{
		System.out.printf("%-5s%-10s%-3s%-3s%-3s%-3s%-5s\n","주문번호","이름","순살","양념","파닭","오븐","주문금액");
	}
	
	void findBestCustomer() //베스트고객찾기
	{
		int max= 0;
		int bestCu= 0;
		for(int i=0;i<u.size();i++)
		{
			if(max< u.get(i).getMoney())
			{
				max= u.get(i).getMoney();
				bestCu= i;
			}
		}
		System.out.printf("베스트 고객님은 %s입니다.\n",u.get(bestCu).name);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderManager om= new OrderManager();
//		om.test(); //시험데이터 입력
		om.start();
	}
}
