import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class BroadcastDiscover {
	
	/** Class variables **/
	public Thread listenThread, announceThread;
	public DatagramSocket recvSocket = null, sendSocket = null;
	InetAddress bcastaddr;
	boolean run=true;
	boolean DEBUG=true;
	private ArrayList<String> discovered=null;
	private long waitTime=60000;
	
	/** Class Functions **/
	public void sendBroadcast(String message) throws IOException {
		sendSocket = new DatagramSocket();
		sendSocket.setBroadcast(true);
		DatagramPacket packet = new DatagramPacket(message.getBytes(),message.length(),bcastaddr, 11111);
		sendSocket.send(packet);
		sendSocket.close();
	}
	
	public ArrayList<Object> recvBroadcast() throws IOException {
		if(recvSocket==null) {
			recvSocket = new DatagramSocket(11111,bcastaddr);
			recvSocket.setBroadcast(true);
		}
		byte receiveData[] = new byte[100];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		recvSocket.receive(receivePacket);
		ArrayList<Object> returnData = new ArrayList<Object>();
		returnData.add((Object)receivePacket.getSocketAddress());
		returnData.add((Object)(new String(receivePacket.getData())));
		return returnData;
	}
	
	public ArrayList<String> getDiscovered() {
		return discovered;
	}
	
	private String process(String data) {
		int from=data.indexOf("/")+1;
		int to=data.indexOf(":");
		return data.substring(from,to);
	}
	
	public BroadcastDiscover() {
		try {
			bcastaddr = InetAddress.getByName("255.255.255.255");
			discovered = new ArrayList<String>();
			/** Here thread gets created **/
			listenThread = new Thread() {
				public void run() {
					while(run) {
						try {
							// TODO Add a secret message or something.
							ArrayList<Object> in = recvBroadcast();
							SocketAddress addr = (SocketAddress) in.get(0);
							boolean insertFlag = true;
							for(int i=0;i<discovered.size();i++) {
								if(process(addr.toString()).contains(discovered.get(i).toString())){
									insertFlag = false;
									break;
								}
							}
							if(insertFlag) {
								discovered.add(process(addr.toString()));
								if(DEBUG) {
									System.out.println("BroadcastDiscover: Adding "+addr);
								}
							}
							else {
								if(DEBUG) {
									System.out.println("BroadcastDiscover: Already exists "+addr);
								}
							}
							String data = (String) in.get(1);
							//System.out.println(data);
							if(data.startsWith("Who is there?")) {
								// TODO Check if message originated from self, if yes there is no need to reply
								// TODO Add a cooldown timer
								if(DEBUG) {
									System.out.println("BroadcastDiscover: Got ping message from ip:"+addr+" message:"+data);
									System.out.println("BroadcastDiscover: Send Notification");
								}
								ack();
							}
							else if(data.startsWith("I am here!")) {
								if(DEBUG) {
									System.out.println("BroadcastDiscover: Got reply message from ip:"+addr+" message:"+data);
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			announceThread = new Thread() {
				public void run() {
					while(run) {
						ask();
						try {
							Thread.sleep(waitTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	public void ask() {
		try {
			sendBroadcast("Who is there?");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ack() {
		try {
			sendBroadcast("I am here!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BroadcastDiscover(int period) {
		this();
		waitTime = period;
	}
	
	public void disableDebug() {
		DEBUG=false;
	}
	
	public void start() {
		run=true;
		announceThread.start();
		listenThread.start();
	}
	
	public void join() {
		try {
			announceThread.join();
			listenThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop() {
		run=false;
	}
	
}

/** Sample Usage **/
/*
public class Main {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Statring");
		BroadcastDiscover bd = new BroadcastDiscover();
		bd.start();
		bd.join();
		System.out.println("Exiting!");
//		try {
//			//recvBroadcast();
//			//System.out.println("Recv message");
////			sendBroadcast("hello");
////			System.out.println("Send");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
*/
