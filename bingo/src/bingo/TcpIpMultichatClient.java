package bingo;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

//import bingoo.TcpIpMultichatClient.ClientReceiver;

public class TcpIpMultichatClient {
	static DataOutputStream out;
	static Label stlb = new Label();
	static Button b = new Button("�� ��");
	static Button exit = new Button("������");
	static Socket socket;
	static Bingo win = null;

	public static void main(String args[]) {

		//�÷��̾�� �Է��ϴ� ������
		Frame nameFrame = new Frame("input name");
		nameFrame.setSize(400, 100);
		nameFrame.setLayout(new FlowLayout()); // . ���̾ƿ� �Ŵ����� ������ �����Ѵ�
		//�÷��̾���ؽ�Ʈ�ʵ�, ��
		TextField nametxt = new TextField(10);
		Label nameLabel = new Label();
		Button okBtn = new Button("����");
		nameLabel.setText("�÷��̾��̸��Է�");
		//�����ӿ� add
		nameFrame.add(nameLabel);
		nameFrame.add(nametxt);
		nameFrame.add(okBtn);
		nameFrame.setVisible(true);
		
		//���ӹ�ư �������� action
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				//�÷��̾� �̸� �ؽ�Ʈ �ʵ尡 ��������� ����
				if(nametxt.getText().isEmpty()) {
					System.out.println("USAGE: java TcpIpMultichatClient ��ȭ��");
					System.exit(0); 
				//�÷��̾� �̸� �Է� ������ ���� ��ư ȭ�� ������ �ϱ�		
				}else {
					
					nameFrame.dispose();
					nameFrame.setVisible(false);
					
					String name = nametxt.getText();

					
					Frame f = new Frame("BinGo Game~");
					f.setSize(400, 400);
					f.setLayout(null); // . ���̾ƿ� �Ŵ����� ������ �����Ѵ�
					
					Font font = new Font("Serif", Font.ITALIC, 40) ;


						
					Label title = new Label();
					title.setBounds(120, 70, 200, 150); 
					title.setText("��������");
					title.setFont(font);
					f.add(title);

					b.setSize(100, 50); // Button . �� ũ�⸦ �����Ѵ�
					b.setLocation(70, 280); // Frame Button . �������� �� ��ġ�� �����Ѵ�
					f.add(b);
					exit.setSize(100, 50); // Button . �� ũ�⸦ �����Ѵ�
					exit.setLocation(230, 280); // Frame Button . �������� �� ��ġ�� �����Ѵ�
					f.add(exit);	


					stlb.setBounds(75, 50, 100, 430); 
					stlb.setText("0");

					f.add(stlb);
					
					f.setVisible(true);

				
					try {
			            // ������ �����Ͽ� ������ ��û�Ѵ�.
//						socket = new Socket("10.10.10.141", 7777);
						socket = new Socket("127.0.0.1", 7777);
						System.out.println("������ ����Ǿ����ϴ�.");
						//textfield name�ް� ����
						Thread sender   = new Thread(new ClientSender(socket, name));
						//@180526
//						Thread receiver = new Thread(new ClientReceiver(socket));
						Thread receiver = new Thread(new ClientReceiver(socket,name));


						sender.start();
						receiver.start();
					} catch(ConnectException ce) {
						//@180526
						//ce.printStackTrace();
						System.out.println("�������ӽ���");
						System.exit(0);
					} catch(Exception e) {}
					
					
					
					TcpIpMultichatClient awtControlDemo = new TcpIpMultichatClient();
					awtControlDemo.showButton();
					
					
					
					
				}
				
			}
		});
	} // main

	private void showButton() {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					out.writeUTF("1|���ӽ���");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//@180526
				try {
					out.writeUTF("999|������");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.exit(0);
			}
		});
	}

	static class ClientSender extends Thread {
		Socket socket;

		String name;

		ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
			} catch (Exception e) {
			}
		}

		public void run() {
			Scanner scanner = new Scanner(System.in);
			try {
				if (out != null) {
					out.writeUTF(name);
				}

				while (out != null) {
					out.writeUTF("[" + name + "]" + scanner.nextLine());
				}
			} catch (IOException e) {}
		} // run()
	} // ClientSender

	static class ClientReceiver extends Thread {
		Socket socket;

		DataInputStream in;

		//@180526
		String name;
		ClientReceiver(Socket socket, String name) {
			this.socket = socket;
			this.name = name;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}
//		ClientReceiver(Socket socket) {
//			this.socket = socket;
//			try {
//				in = new DataInputStream(socket.getInputStream());
//			} catch (IOException e) {
//			}
//		}

		public void run() {
			// System.out.println("Ŭ��"+in);
			while (in != null) {
				try {
					// String msg = in.readLine();// msg: ������ ���� �޽���
					String msg = in.readUTF();// msg: ������ ���� �޽���

					String msgs[] = msg.split("\\|");

					String protocol = msgs[0];

					System.out.println(msg + "|Ŭ����");
					switch (protocol) {

					case "100":
						stlb.setText("����� = " + msgs[1]);
						break;
					case "1":
						if (win == null)
							win = new Bingo("Bingo Game Ver1.0", socket);
						b.setEnabled(false);
						//@180526
						if(name.equals(msgs[2])) {//���� ���� ��ư ��������� 1��
							win.turnCheck(true);
						}else {
							win.turnCheck(false);
						}
						break;
					case "300":
						//@180526
						//win.bingoCheck(msgs[1]);
						//@180526
						if(name.equals(msgs[2])) {//������ ��ư ��������� �̸��� ������ �� false ->������ disable
							win.bingoCheck(msgs[1], true);//�÷� ���β� true(red)
							win.turnCheck(false);
						}else {
							win.bingoCheck(msgs[1], false);//�÷� ���ǲ� true(green)
							win.turnCheck(true);
						}
						break;
					case "400":
						if(msgs[1].equals("��������")) {
							win.winCheck(msgs[1]);
						}if(msgs[1].equals("���������")) {
							b.setEnabled(true);
							win = null;
							
						}if(msgs[1].equals("���Ӳ���")) {
							System.exit(0);
						}
					case "#":
						System.out.println(msgs[1]);
						break;
					}
				} catch (IOException e) {
				}
			}
		} // run
	} // ClientReceiver
} // class