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
	
	void test() //���赥����
	{
		String testname[]={"����","����","����","�︮","�Ѹ�","��ġ","����","�����","��浿","ȫ�浿"};
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
	
	void start() //�����۾�����
	{
		int n,findsth,search;
		String findsths;
		
		while(true)
		{
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.print("1)�ֹ��߰� 2)�ֹ���� 3)�ֹ��� ���� 4)�ֹ��� ã�� 5)�Ǹ���Ȳ���� 6)����Ʈ�� 7)���� = > ");
			n= s.nextInt();
			System.out.println("-------------------------------------------------------------------------------------------");
			if(n== 7)
			{
				System.out.println("����Ǿ����ϴ�.");
				break;
			}
			else if(n== 1) addOrder();
			else if(n== 2) cancelOrder();
			else if(n== 3) printOrder();
			else if(n== 4) 
			{
				System.out.print("�޴���ȣ�� �˻��Ͻ÷��� 1�� ���̸����� �˻��Ͻ÷��� 2���� �Է��� �ּ��� : ");
				search= s.nextInt();
				if(search== 1)
				{
					System.out.print("�˻��� �޴���ȣ�� �Է��� �ּ���(1:���� 2:��� 3:�Ĵ� 4:���� 100�̻�:�����ݾ� �̻�) : ");
					findsth= s.nextInt();
					findOrder(findsth);
				}
				else
				{
					System.out.print("�˻��� ���̸��� �Է��� �ּ��� : ");
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
		System.out.print("�ֹ����� ������ �Է��� �ּ��� : ");
		name= s.next();
		System.out.print("����, ���, �Ĵ�, ���� ����� ������ �Է����ּ��� : ");
		
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
		System.out.print("����� �ֹ���ȣ�� �Է��� �ּ��� : ");
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

	void findOrder(int n) //�ֹ���ã�� �޴���ȣ��
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
				System.out.print("�ݾ��� �Է��ϼ���. (����ݾ��̻����� �ֹ��� �ֹ����� ��µ˴ϴ�.) : ");
				int standard= s.nextInt();
				print();
				for(int i=0;i<u.size();i++)
				{
					if(u.get(i).getMoney() >= standard) u.get(i).print();
				}
			}
			else System.out.println("�Է��Ͻ� �޴���ȣ�� �������� �ʽ��ϴ�.");
		}
	}
	
	void findOrder(String n) //�ֹ���ã�� �̸�����
	{
		print();
		for(int i=0;i<u.size();i++)
		{
			if(u.get(i).name.matches(".*"+n+".*")==true) u.get(i).print();
		}
	}
	
	void viewStatus() //�Ǹ���Ȳ
	{
		System.out.printf("�� �ֹ��� ���� : %d\n",u.size());
		System.out.printf("%-3s%-3s%-3s%-3s\n","����","���","�Ĵ�","����");
		for(int i=0;i<4;i++)
		{
			System.out.printf("%-3s",totalOrderChic[i]);
		}
		System.out.printf("\n�� �ֹ� �ݾ� : %d\n",totalMoney);
	}
	
	void print()
	{
		System.out.printf("%-5s%-10s%-3s%-3s%-3s%-3s%-5s\n","�ֹ���ȣ","�̸�","����","���","�Ĵ�","����","�ֹ��ݾ�");
	}
	
	void findBestCustomer() //����Ʈ��ã��
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
		System.out.printf("����Ʈ ������ %s�Դϴ�.\n",u.get(bestCu).name);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderManager om= new OrderManager();
//		om.test(); //���赥���� �Է�
		om.start();
	}
}
